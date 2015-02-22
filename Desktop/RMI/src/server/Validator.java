package server;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

public class Validator {
    //The isLuhnFormula method is get from http://de.wikipedia.org/wiki/Luhn-Algorithmus#Java
    public static boolean isLuhnFormula(String creditCardNumber) {
        int[] creditCardArr = tokenize(creditCardNumber);
        if(creditCardArr.length == 0)
            return false;
        int sum = 0;
        for (int i = 0; i < creditCardArr.length; i++) {
            // get digits in reverse order
            int digit = creditCardArr[creditCardArr.length - i - 1];
            // every 2nd number multiply with 2
            if (i % 2 == 1) {
                digit *= 2;
            }
            sum += digit > 9 ? digit - 9 : digit;
        }
        return sum % 10 == 0;
    }
    //Perform tokenize over input
    public static int[] tokenize(String input) {
        String[] tokenizeString = input.split("\\D*");

        LinkedList<String> charList = new LinkedList<>();

        for (String tokenizeString1 : tokenizeString) {
            charList.add(tokenizeString1);
            charList.remove("");
        }

        int[] intArr = new int[charList.size()];

        Iterator itr = charList.iterator();
        int index = -1;

        while (itr.hasNext()) {
            intArr[++index] = Integer.parseInt((String) itr.next());
        }

        return intArr;
    }
    //Determine whether or not the specific file exist
    public static boolean fileExist(String username) {
        File file = new File(username);
        return file.exists();
    }
    
}
