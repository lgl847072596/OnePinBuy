package com.one.pin.buy.tool;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created by liuguilin on 16/4/19.
 * ShardPreferenceTool 操作xml文件
 */
public class ShardPreferenceTool {

    private  SharedPreferences sp=null;
    private  SharedPreferences.Editor editor=null;
    private final static  String name = "android-xml";
    private final static int mode = Context.MODE_PRIVATE;

    private static ShardPreferenceTool instance;


    private ShardPreferenceTool(Context context){
        sp=context.getSharedPreferences(name,mode);
        editor=sp.edit();
    }


    public synchronized  static ShardPreferenceTool getInstance(Context context){
        if(instance==null){
            instance=new ShardPreferenceTool(context);
        }
        return instance;
    }

    /**
     * @description 保存一个键值对
     * @param key
     * @param value
     * @return
     */
    public boolean save(String key,String value){
        boolean result=false;
        if(editor!=null){
            editor.putString(key, value);
            editor.commit();
            result=true;
        }
        return result;

    }

    /**
     * 保存一个map
     * @param map
     * @return
     */
    public boolean save(Map<String,String> map){
        boolean result=false;
        if(editor!=null){
            Set<String> set = map.keySet();
            for (String key : set) {
                editor.putString(key, map.get(key));
            }
            editor.commit();
            result=true;
        }
        return result;
    }

    /**
     * 通过可以取值,无则返回null
     * @param key
     * @return
     */
    public String get(String key){
        String result=null;
        if(sp!=null){
            result=sp.getString(key,null);
        }
        return result;
    }

    /**
     * 通过key删除值
     * @param key
     * @return
     */
    public boolean delete(String key){
        boolean result=false;
        if(editor!=null){
            editor.remove(key);
            editor.commit();
            result=true;
        }
        return result;
    }

    /**
     * 删除全部的键值对
     * @return
     */
    public boolean deleteAll(){
        boolean result=false;
        if(editor!=null){
            editor.clear();
            editor.commit();
            result=true;
        }
        return result;
    }

}
