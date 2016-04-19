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





}
