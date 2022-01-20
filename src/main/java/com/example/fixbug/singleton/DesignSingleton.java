package com.example.fixbug.singleton;

import java.util.HashMap;
import java.util.Map;

public class DesignSingleton {

    private final Map<Integer, String> str;

    public static DesignSingleton getInstance(){
        return DesignSingletonHolder.SINGLETON;
    }

    private DesignSingleton(){
        str = new HashMap<>();
    }

    private String getString(int number){
        String s = str.get(number);
        if (s == null){
            s = number + " object";
            str.put(number, s);
        }else {
            s = s + " đã có";
        }
        return s;
    }

    public String callStr(int number){
        String s = getString(number);
        return s;
    }

    private static final class DesignSingletonHolder{
        private static final DesignSingleton SINGLETON = new DesignSingleton();
    }
}
