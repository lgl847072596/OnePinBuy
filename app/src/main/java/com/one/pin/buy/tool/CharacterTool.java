package com.one.pin.buy.tool;

import java.io.Serializable;

/**
 * Created by liuguilin on 16/4/19.
 */
public class CharacterTool implements Serializable {

    private CharacterTool(){}

    /**
     * @description 去除空指针
     * @param target
     * @return
     */
    public static String decuteNull(String target){
        return target==null?"":target;
    }

    /**
     * @description 校验用户名
     * @param target
     * @return
     */
    public static boolean isRightAccount(String target){
        if(target==null)return false;
        return target.length()==11;
    }

    /**
     * @descritpion 校验密码
     * @param target
     * @return
     */
    public static boolean isRightPassword(String target){
        return target.length()>0;
    }


}
