package com.xandersu.game2048.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lenovo on 2016/11/26.
 */

public class SpUtil {

    private static SharedPreferences sp;

    public static void putInt(Context context,String key, int value){
       if(sp == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
       }
        sp.edit().putInt(key,value).commit();
    }

    public static int getInt(Context context,String key, int defValue){
        if(sp == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getInt(key,defValue);
    }
}
