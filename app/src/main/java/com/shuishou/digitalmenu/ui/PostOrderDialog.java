package com.shuishou.digitalmenu.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.shuishou.digitalmenu.InstantValue;
import com.shuishou.digitalmenu.R;
import com.shuishou.digitalmenu.bean.Desk;
import com.shuishou.digitalmenu.bean.HttpResult;
import com.shuishou.digitalmenu.http.HttpOperator;
import com.shuishou.digitalmenu.uibean.ChoosedDish;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.theme;

/**
 * Created by Administrator on 2017/7/21.
 */

public class PostOrderDialog {
    private EditText txtCode;
    private EditText txtCustomerAmount;
    private TableLayout deskAreaLayout;
    private ArrayList<ChoosedDish> choosedFoodList;
    private HttpOperator httpOperator;

    private AlertDialog dlg;

    private ArrayList<DeskIcon> deskIconList = new ArrayList<>();

    private MainActivity mainActivity;

    private DeskClickListener deskClickListener = new DeskClickListener();
    private final static int MESSAGEWHAT_CHECKCONFIRMCODE=1;
    private final static int MESSAGEWHAT_CHECKDESKAVAILABLE=2;
    private final static int MESSAGEWHAT_MAKEORDERSUCCESS=3;
    private final static int MESSAGEWHAT_ADDDISHSUCCESS=4;
    private final static int MESSAGEWHAT_ASKTOADDDISHINORDER=5;
    private final static int MESSAGEWHAT_ERRORTOAST=8;
    private final static int MESSAGEWHAT_ERRORDIALOG=9;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            dealHandlerMessage(msg);
            super.handleMessage(msg);
        }
    };

    public PostOrderDialog(@NonNull MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        initUI();
    }

    private void initUI(){
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.postorderdialog_layout, null);
        txtCode = (EditText) view.findViewById(R.id.txt_confirmcode);
        deskAreaLayout = (TableLayout)view.findViewById(R.id.postorder_deskarea);
        txtCustomerAmount = (EditText) view.findViewById(R.id.txt_customeramount);
        initDeskData(mainActivity.getDesks());
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
//        builder.setTitle("Confirm");
//        builder.setMessage("Please input the CONFIRMATION CODE before post this order!");
//        builder.setIcon(R.drawable.info);
        //here cannot use listener on the positive button because the dialog will dismiss no matter
        //the input value is valiable or not. I wish the dialog keep while input info is wrong.
        builder.setPositiveButton("Confirm", null);
        builder.setNegativeButton("Cancel", null);
        builder.setView(view);
        dlg = builder.create();

        dlg.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                //add listener for YES button
                ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeOrder();
                    }
                });
            }
        });
        dlg.setCancelable(false);
        dlg.setCanceledOnTouchOutside(false);
        Window window = dlg.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        param.y = 50;
        window.setAttributes(param);
    }

    public void showDialog(HttpOperator httpOperator, ArrayList<ChoosedDish> choosedFoodList){
        this.choosedFoodList = choosedFoodList;
        this.httpOperator = httpOperator;
        dlg.show();
    }

    private void dealHandlerMessage(Message msg){
        switch (msg.what){
            case MESSAGEWHAT_ERRORTOAST :
                Toast.makeText(mainActivity, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                break;
            case MESSAGEWHAT_ERRORDIALOG:
                mainActivity.popupWarnDialog(R.drawable.error, "WRONG", msg.obj.toString());
                break;
            case MESSAGEWHAT_MAKEORDERSUCCESS:
                mainActivity.onFinishMakeOrder("SUCCESS", "Finish make order! Order Sequence : " + msg.arg1);
                break;
            case MESSAGEWHAT_ASKTOADDDISHINORDER:
                addDishToOrderWithAsk(msg.arg1);
                break;
            case MESSAGEWHAT_ADDDISHSUCCESS:
                mainActivity.onFinishMakeOrder("SUCCESS", "Add dish successfully");
                break;
        }
    }

    private void makeOrder(){
        if (txtCode.getText() == null || txtCode.getText().length() == 0) {
            Toast.makeText(mainActivity, "Please input the Confirmation Code to post this order!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (txtCustomerAmount.getText() == null || txtCustomerAmount.getText().length() == 0){
            Toast.makeText(mainActivity, "Please input the amount of customer!", Toast.LENGTH_SHORT).show();
            return;
        }
        DeskIcon choosedDeskIcon = null;
        for (DeskIcon di: deskIconList) {
            if (di.isChoosed()){
                choosedDeskIcon = di;
                break;
            }
        }
        if (choosedDeskIcon == null){
            Toast.makeText(mainActivity, "Please select the desk before post this order!", Toast.LENGTH_SHORT).show();
            return;
        }
        final Desk choosedDesk = choosedDeskIcon.getDesk();
        //check confirm code and desk status
        new Thread(){
            @Override
            public void run() {
                String result = httpOperator.checkConfirmCodeSync(txtCode.getText().toString());
                if (InstantValue.RESULT_SUCCESS.equals(result)){
                    String deskstatus = httpOperator.checkDeskStatus(choosedDesk.getName());
                    if (InstantValue.CHECKDESK4MAKEORDER_OCCUPIED.equals(deskstatus)){
                        Message msg = new Message();
                        msg.what = MESSAGEWHAT_ASKTOADDDISHINORDER;
                        msg.arg1 = choosedDesk.getId();
                        handler.sendMessage(msg);
                    } else if (InstantValue.CHECKDESK4MAKEORDER_AVAILABLE.equals(deskstatus)){
                        makeNewOrder(choosedDesk.getId());
                    } else {
                        Message msg = new Message();
                        msg.what = MESSAGEWHAT_ERRORDIALOG;
                        msg.obj = deskstatus;
                        handler.sendMessage(msg);
                    }
                } else {
                    Message msg = new Message();
                    msg.what = MESSAGEWHAT_ERRORDIALOG;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //this function must be call in a non-UI thread
    private void makeNewOrder(int deskid){
        JSONArray os = null;
        try {
            os = generateOrderJson();
        } catch (JSONException e) {
            Message msg = new Message();
            msg.what = MESSAGEWHAT_ERRORDIALOG;
            msg.obj = "There are error to build JSON Object, please restart APP!";
            handler.sendMessage(msg);
            return;
        }

        if (os != null){
            HttpResult<Integer> result = httpOperator.makeOrder(txtCode.getText().toString(), os.toString(), deskid,
                    Integer.parseInt(txtCustomerAmount.getText().toString()));
            if (result.success){
                Message msg = new Message();
                msg.what = MESSAGEWHAT_MAKEORDERSUCCESS;
                msg.arg1 = result.data;
                handler.sendMessage(msg);
            } else {
                Message msg = new Message();
                msg.what = MESSAGEWHAT_ERRORDIALOG;
                msg.obj = "Something wrong happened while making order! \n\nError message : " + result.result;
                handler.sendMessage(msg);
            }
        }
    }

    private void addDishToOrderWithAsk(final int deskid){
        AlertDialog askDialog = new AlertDialog.Builder(dlg.getContext())
                .setTitle("Add Order")
                .setMessage("There is an order on this table already. \n\nWill you add these dishes in the order?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONArray os = null;
                        try {
                            os = generateOrderJson();
                        } catch (JSONException e) {
                            Toast.makeText(mainActivity, "There are error to build JSON Object, please !", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (os != null){
                            final String oss = os.toString();
                            new Thread(){
                                @Override
                                public void run() {
                                    HttpResult<Integer> result = httpOperator.addDishToOrder(deskid,oss);
                                    if (result.success){
                                        Message msg = new Message();
                                        msg.what = MESSAGEWHAT_ADDDISHSUCCESS;
                                        handler.sendMessage(msg);
                                    } else {
                                        Message msg = new Message();
                                        msg.what = MESSAGEWHAT_ERRORDIALOG;
                                        msg.obj = "Something wrong happened while add dishes! \n\nError message : " + result.result;
                                        handler.sendMessage(msg);
                                    }
                                }
                            }.start();
                        }
                    }
                }).create();
        askDialog.show();
    }

    private JSONArray generateOrderJson() throws JSONException {
        JSONArray ja = new JSONArray();
        for(ChoosedDish cf: choosedFoodList){
            JSONObject jo = new JSONObject();
            jo.put("id", cf.getDish().getId());
            jo.put("amount", cf.getAmount());
            jo.put("addtionalRequirements", cf.getAdditionalRequirements());
            ja.put(jo);
        }
        return ja;
    }
    public void initDeskData(ArrayList<Desk> desks){
        deskAreaLayout.removeAllViews();
        TableRow.LayoutParams trlp = new TableRow.LayoutParams();
        trlp.setMargins(5, 5 ,0 ,0);
        TableRow tr = null;
        for (int i = 0; i < desks.size(); i++) {
            if (i % 10 == 0){
                tr = new TableRow(mainActivity);
                deskAreaLayout.addView(tr);
            }
            DeskIcon di = new DeskIcon(mainActivity, desks.get(i));
            deskIconList.add(di);
            tr.addView(di, trlp);
        }

    }

    //clear up old data in the components
    public void clearup(){
        txtCode.setText(InstantValue.NULLSTRING);
        for(DeskIcon di : deskIconList){
            di.setChoosed(false);
        }
    }

    public void dismiss(){
        dlg.dismiss();
    }

    class DeskIcon extends android.support.v7.widget.AppCompatTextView{
        private Desk desk;
        private boolean choosed;
        public DeskIcon(Context context, Desk desk){
            super(context);
            this.desk = desk;
            initDeskUI();
        }

        private void initDeskUI(){
            setTextSize(18);
            setTextColor(Color.BLACK);
            setBackgroundColor(Color.LTGRAY);
            setText(desk.getName());
            setHeight(InstantValue.DESKHEIGHT_IN_POSTORDERDIALOG);
            setWidth(InstantValue.DESKWIDTH_IN_POSTORDERDIALOG);
            setOnClickListener(deskClickListener);
            setEllipsize(TextUtils.TruncateAt.END);
        }

        public void setChoosed(boolean b){
            choosed = b;
            if (b){
                this.setBackgroundColor(Color.GREEN);
            } else {
                this.setBackgroundColor(Color.LTGRAY);
            }
        }

        public boolean isChoosed(){
            return choosed;
        }

        public Desk getDesk() {
            return desk;
        }
    }

    class DeskClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getClass().getName().equals(DeskIcon.class.getName())){
                for(DeskIcon di : deskIconList){
                    di.setChoosed(false);
                }
                ((DeskIcon)v).setChoosed(true);
            }
        }
    }
}
