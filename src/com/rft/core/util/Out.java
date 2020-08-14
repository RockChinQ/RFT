package com.rft.core.util;

import java.util.Date;

public class Out {
    public static void say(String msg){
        System.out.println(msg);
    }
    public static void say(String sub,String msg){
        say(TimeUtil.millsToMMDDHHmmSS(new Date().getTime())+"["+sub+"]"+msg);
    }
}
