package com.shuishou.digitalmenu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/12/22.
 */

public final class InstantValue {
    public static final DateFormat DFYMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String DOLLAR = "$";
    public static final String DOLLARSPACE = "$ ";
    public static final String NULLSTRING = "";
    public static final String SPACESTRING = " ";
    public static final String SLLASHSTRING = " ";
    public static final String ENTERSTRING = "\n";

    public static final String FORMAT_DOUBLE = "%.2f";

    public static final String RESULT_SUCCESS = "SUCCESS";
    public static final String RESULT_FAIL = "FAIL";
    public static final String CHECKDESK4MAKEORDER_AVAILABLE = "AVAILABLE";
    public static final String CHECKDESK4MAKEORDER_OCCUPIED = "OCCUPIED";
    public static final byte DISH_STATUS_NORMAL = 0;
    public static final byte DISH_STATUS_SOLDOUT = 1; //缺货
    public static final byte DISH_STATUS_ONSALE = 2;//促销

    public static final int DISPLAY_DISH_WIDTH = 250; //dish控件的宽度
    public static final int DISPLAY_DISH_HEIGHT = 300; //dish控件高度, 含图片
    public static final int DISPLAY_LEFTCATEGORY_WIDTH = 180; //左侧目录控件宽度
    public static final int DISPLAY_RIGHTORDER_WIDTH = 260;//右侧的选单控件宽度

    public static final int DESKWIDTH_IN_POSTORDERDIALOG = 110;
    public static final int DESKHEIGHT_IN_POSTORDERDIALOG = 110;

    public static final byte DISH_CHOOSEMODE_DEFAULT = 1;
//    public static final byte DISH_CHOOSEMODE_SUBITEM = 2;
    public static final byte DISH_CHOOSEMODE_POPINFOCHOOSE = 3;
    public static final byte DISH_CHOOSEMODE_POPINFOQUIT = 4;

    public static final byte DISH_PURCHASETYPE_UNIT = 1;//按份购买
    public static final byte DISH_PURCHASETYPE_WEIGHT = 2;//按重量购买

    public static final String FORMAT_DOUBLE_2DECIMAL = "%.2f";

    public static String URL_TOMCAT = null;
    public static boolean SETTING_SHOWDISHPICTURE = true;
    public static boolean SETTING_NEEDPWDPOSTINGORDER = true;

    public static final String SERVER_CATEGORY_UPGRADEAPK = "upgradeApk";
    public static final String SERVER_CATALOG_DISH_PICTURE_BIG = "dishimage_big";
    public static final String SERVER_CATALOG_DISH_PICTURE_SMALL = "dishimage_small";
    public static final String SERVER_CATALOG_DISH_PICTURE_ORIGIN = "dishimage_origin";
    public static final String LOCAL_CATALOG_DISH_PICTURE_ORIGIN = "/data/data/com.shuishou.digitalmenu/dishimage_origin/";
    public static final String LOCAL_CATALOG_DISH_PICTURE_BIG = "/data/data/com.shuishou.digitalmenu/dishimage_big/";
    public static final String LOCAL_CATALOG_DISH_PICTURE_SMALL = "/data/data/com.shuishou.digitalmenu/dishimage_small/";
    public static final String LOCAL_CATALOG_ERRORLOG = "/data/data/com.shuishou.digitalmenu/errorlog/";

    public static final String LOCAL_CATEGORY_UPGRADEAPK = "/data/user/0/com.shuishou.digitalmenu/files/";
    public static final String FILE_SERVERURL = "/data/data/com.shuishou.digitalmenu/serverconfig";
    public static final String FILE_CONFIGINFO = "/data/data/com.shuishou.digitalmenu/configinfo";
    public static final String ERRORLOGPATH = "/data/data/com.shuishou.digitalmenu/errorlog/";
    public static final String ERRORLOGFILENAME = "digitalmenu_log";
    public static final String LOGO_PATH = "/data/data/com.shuishou.digitalmenu/logo_path/";

    public static final String CONFIGINFO_SHOWDISHPIC = "SHOWDISHPIC";
    public static final String CONFIGINFO_NEEDPWDPOSTINGORDER = "NEEDPWDPOSTINGORDER";

    public static final String CONFIGS_CONFIRMCODE = "CONFIRMCODE";
    public static final String CONFIGS_OPENCASHDRAWERCODE = "OPENCASHDRAWERCODE";
    public static final String CONFIGS_LANGUAGEAMOUNT = "LANGUAGEAMOUNT";
    public static final String CONFIGS_FIRSTLANGUAGENAME= "FIRSTLANGUAGENAME";
    public static final String CONFIGS_FIRSTLANGUAGEABBR = "FIRSTLANGUAGEABBR";
    public static final String CONFIGS_SECONDLANGUAGENAME= "SECONDLANGUAGENAME";
    public static final String CONFIGS_SECONDLANGUAGEABBR = "SECONDLANGUAGEABBR";

    public static final byte MENUCHANGE_TYPE_DISHSOLDOUT = 0;//设置soldout或取消soldout
    public static final byte MENUCHANGE_TYPE_CHANGEPROMOTION = 1;//设置promotion或取消promotion
    public static final byte MENUCHANGE_TYPE_DISHCONFIGSOLDOUT = 2;//设置soldout或取消soldout
    public static final byte MENUCHANGE_TYPE_DISHADD = 3; //增加菜品
    public static final byte MENUCHANGE_TYPE_DISHUPDATE = 4;//修改菜品属性
    public static final byte MENUCHANGE_TYPE_DISHPICTURE = 5;//修改菜品图片
    public static final byte MENUCHANGE_TYPE_DISHDELETE = 6;//delete菜品
    public static final byte MENUCHANGE_TYPE_CATEGORY1ADD = 7;
    public static final byte MENUCHANGE_TYPE_CATEGORY1UPDATE = 8;
    public static final byte MENUCHANGE_TYPE_CATEGORY1DELETE = 9;
    public static final byte MENUCHANGE_TYPE_CATEGORY2ADD = 10;
    public static final byte MENUCHANGE_TYPE_CATEGORY2UPDATE = 11;
    public static final byte MENUCHANGE_TYPE_CATEGORY2DELETE = 12;
    public static final byte MENUCHANGE_TYPE_DISHCONFIGGROUPADD = 13;
    public static final byte MENUCHANGE_TYPE_DISHCONFIGGROUPUPDATE = 14;
    public static final byte MENUCHANGE_TYPE_DISHCONFIGGROUPDELETE = 15;
    public static final byte MENUCHANGE_TYPE_DISHCONFIGADD = 16;
    public static final byte MENUCHANGE_TYPE_DISHCONFIGUPDATE = 17;
    public static final byte MENUCHANGE_TYPE_DISHCONFIGDELETE = 18;
    public static final byte MENUCHANGE_TYPE_DISHMOVEINCONFIGGROUP = 19;
    public static final byte MENUCHANGE_TYPE_DISHMOVEOUTCONFIGGROUP = 20;

    public static final int REFRESHMENUINTERVAL= 60; //the interval for refresh the menu data
}
