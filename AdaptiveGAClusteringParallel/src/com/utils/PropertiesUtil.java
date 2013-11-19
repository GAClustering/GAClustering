package com.utils;

import java.util.ResourceBundle;

/**
 *
 * @author xp
 */
public class PropertiesUtil {

    public static void loadDBProperties(){
        ResourceBundle res = ResourceBundle.getBundle(ConstantDB._FILE_PATH_PROPERTIES);

        ConstantDB.DRIVER_CLASS = res.getString(ConstantDB._DRIVER_CLASS_PROPERTIES);
        ConstantDB.CONNECTION_STR = res.getString(ConstantDB._CONNECTION_STR_PROPERTIES);
        ConstantDB.USER_CONNECTION = res.getString(ConstantDB._USER_CONNECTION_PROPERTIES);
        ConstantDB.PASSWORD_CONNECTION = res.getString(ConstantDB._PASSWORD_CONNECTION_PROPERTIES);
    }
}

