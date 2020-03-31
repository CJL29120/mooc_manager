package com.chen.mooc_manager.util;

import java.util.Random;

public class OtherUtils {

    public static String getRandomCode( int length ){
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for ( int i = 0; i < length; i++ )
        {
            int number = random.nextInt( base.length() );
            sb.append( base.charAt( number ) );
        }
        return sb.toString();

    }
}
