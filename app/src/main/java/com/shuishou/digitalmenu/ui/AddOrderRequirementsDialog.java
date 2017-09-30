package com.shuishou.digitalmenu.ui;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.shuishou.digitalmenu.R;
import com.shuishou.digitalmenu.uibean.ChoosedDish;

/**
 * Created by Administrator on 2017/7/21.
 */

class AddOrderRequirementsDialog {

    private EditText txtCode;

    private final MainActivity mainActivity;

    private AlertDialog dlg;

    private ChoosedDish choosedFood;

    public AddOrderRequirementsDialog(@NonNull MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        initUI();
    }

    private void initUI(){
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.addordercontent_layout, null);
        txtCode = (EditText) view.findViewById(R.id.txt_confirmcode);

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Content");
        builder.setMessage("Please add additional requirements for this dish");
        builder.setIcon(R.drawable.info);
        builder.setPositiveButton("Yes", null);
        builder.setNegativeButton("No", null);
        builder.setView(view);
        dlg = builder.create();
        dlg.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                //add listener for YES button
                ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choosedFood.setAdditionalRequirements(txtCode.getText().toString());
                        dismiss();
                    }
                });
            }
        });
    }

    public void showDialog(ChoosedDish cf){
        choosedFood = cf;
        txtCode.setText(cf.getAdditionalRequirements());
        dlg.show();
    }

    private void dismiss(){
        dlg.dismiss();
    }
}
