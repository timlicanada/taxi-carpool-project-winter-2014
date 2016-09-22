package com.example.jeremy.carpoolcontroller;
import java.lang.System;

public class SecurityClass {

    static String keyword = "ALK3R893afjaidnf9qnqdfnq09a093JAF0094aldfemalkdmfDFNNN203";

    public static String encrypt(String clearText){
        String cipherText1 = "";
        String cipherText2 = "";
        int count = 0;

        for(int i = 0; i<clearText.length(); i++) {
            char c = clearText.charAt(i);
            int index = (int) c;
            index += (int) keyword.charAt(count);
            if (index >= 65535) {
                index = index - 65535;
            }
            cipherText1 += (char) index;
            count++;
            if (count == keyword.length()) {
                count = 0;
            }
        }

        for(int j = 0; j<cipherText1.length(); j++) {
            char ch = cipherText1.charAt(j);
            int index2 = (int)ch;
            index2 += 399;
            if(index2 >= 65535){index2 = index2 - 65535;}
            cipherText2 += (char)index2;
        }

        return cipherText2;
    }

    public static String decrypt(String cipherText){
        String clearText1 = "";
        String clearText2 = "";
        int count = 0;

        for(int j = 0; j<cipherText.length(); j++) {
            char ch = cipherText.charAt(j);
            int index2 = (int)ch;
            index2 -= 399;
            if(index2 < 0){index2 = index2 + 65535;}
            clearText1 += (char)index2;
        }

        for(int i = 0; i<clearText1.length(); i++) {
            char c = clearText1.charAt(i);
            int index = (int)c;
            index -= (int)keyword.charAt(count);
            if (index < 0) {
                index = index + 65535;
            }
            clearText2 += (char) index;
            count++;
            if (count == keyword.length()) {
                count = 0;
            }
        }

        return clearText2;
    }
}