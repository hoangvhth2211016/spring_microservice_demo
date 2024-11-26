package com.microservice.mail.utils;

import java.util.Map;

public class StringUtil {
    
    public static void replace(StringBuilder sb, Map<String, String> replacements){
        
        for(Map.Entry<String, String> replacement : replacements.entrySet()){
            int start = sb.indexOf(replacement.getKey());
            while (start != -1) {
                int end = start + replacement.getKey().length();
                sb.replace(start, end, replacement.getValue());
                start = sb.indexOf(replacement.getKey(), start + replacement.getValue().length());
            }
        }
    }
    
}
