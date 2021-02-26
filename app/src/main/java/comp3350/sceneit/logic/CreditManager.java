package comp3350.sceneit.logic;

import java.util.Calendar;

public class CreditManager {
    //Checks if a string has any data in it.
    static public boolean fieldFilled(String data) {
        return data != null && data.length() != 0;
    }

    //checks if a the date given in mmyy format (it assumes its that format) is after the currect date
    //This is to check the expiry date on credit cards.
    static public boolean checkDate(String date) {
        boolean validated = false;
        int inputMonth;
        int inputYear;
        int dateNum;
        Calendar c = Calendar.getInstance();
        if (isNumeric(date) && date.length() == 4) {
            dateNum = Integer.parseInt(date);
            inputMonth = dateNum / 100; //removes the year digits
            inputYear = dateNum % 100; //Removes everything that isnt the last two digits
            //the date is correct if the input month and year are less or equal to current month and year.
            validated = (inputMonth >= c.get(Calendar.MONTH) && inputYear >= c.get(Calendar.YEAR) % 100);
        }
        return validated;
    }

    //Uses Luhns algorithm to validate credit cards.
    static public boolean validateCredit(String creditString) {
        boolean validated = false;
        long workingOn;
        long creditNum;
        //Make sure the string passed in is numeric
        if (isNumeric(creditString)) {
            //Get a number from the string
            creditNum = Long.parseLong(creditString);
            //Check if credit card has 16 digits
            if (String.valueOf(creditNum).length() == 16) {
                //the next 4 lines basically all do Luhns algorithm for validating credit cards
                workingOn = creditNum / 10;//remove last digit from number
                workingOn = reverseNumber(workingOn);
                workingOn = Luhn(workingOn);

                //
                validated = ((workingOn + creditNum % 10) % 10 == 0);
            }
        }
        return validated;
    }

    //Is this string able to be converted to a number.
    static public boolean isNumeric(String number) {
        boolean isNumber;
        try {
            Long.parseLong(number);
            isNumber = true;
        } catch (NumberFormatException e) {
            isNumber = false;
        }
        return isNumber;
    }

    //Reverse what ever number is passed to it
    static public long reverseNumber(long number) {

        long reversed = 0;
        long storage = 0;

        while (number != 0) {
            storage = number % 10;
            reversed = reversed * 10 + storage;
            number /= 10;
        }

        return reversed;
    }

    //Luhn is all the super weird math parts of Luhns algorithm, look it up on wikipedia
    static private long Luhn(long number) {
        //I need to convert my number to a string so i can look at specific digits on the number
        String numberString = Long.toString(number);
        long[] trackNumbers = new long[numberString.length()];
        long total = 0;
        //Go through number by number and do the doubling to every odd number and subtract 9 if that
        //doubled number is greater then 9. Then add all them to total
        for (int i = 0; i < numberString.length(); i++) {
            trackNumbers[i] = Character.getNumericValue(numberString.charAt(i));
            if (i % 2 == 0) {
                trackNumbers[i] *= 2;
                if (trackNumbers[i] > 9)
                    trackNumbers[i] -= 9;
            }
            total += trackNumbers[i];
        }

        return total;
    }
}
