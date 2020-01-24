package com.komarkova.voteSystem.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Param {
    public static String getParamUTF8(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.ISO_8859_1);

        input = new String(bytes, StandardCharsets.UTF_8);
        return input;
    }
    public static String[] getParamUTF8Arr(String[] input){
        String[] res=new String[input.length];
        for (int i = 0; i < res.length; i++){
            res[i]= getParamUTF8(input[i]);
        }
        return res;
    }

    public static boolean isUnique(String[] input){
        for (int i = 0; i < input.length; i++){
            for (int j = i+1; j < input.length; j++){
                if (input[i].equals(input[j])){
                    return false;
                }
            }
        }
        return true;
    }

    public static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;

        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }

        return os.toByteArray();
    }
}
