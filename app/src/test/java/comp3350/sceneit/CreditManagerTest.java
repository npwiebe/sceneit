package comp3350.sceneit;

import org.junit.Test;

import java.util.Calendar;

import comp3350.sceneit.logic.CreditManager;

import static org.junit.Assert.*;

public class CreditManagerTest {
    @Test
    public void testReverse(){
        assertEquals(CreditManager.reverseNumber(1962), 2691);
        assertEquals(CreditManager.reverseNumber(162), 261);
        assertEquals(CreditManager.reverseNumber(12), 21);
        assertEquals(CreditManager.reverseNumber(0), 0);
    }

    @Test
    public void testCheckDate(){
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        //This test currently only works when the month is less then 10;
        assertFalse(CreditManager.checkDate(""));//No date
        assertFalse(CreditManager.checkDate("1"));//not enough info
        assertFalse(CreditManager.checkDate("11111"));//to much info
        assertTrue(CreditManager.checkDate("0" + month + year%100));
        assertTrue(CreditManager.checkDate("0" + month + (year%100+1)));
        assertTrue(CreditManager.checkDate("0" + (month+1) + (year%100)));
        assertTrue(CreditManager.checkDate("0" + (month+1) + (year%100+1)));
        assertFalse(CreditManager.checkDate("0" + (month-1) + (year%100)));
        assertFalse(CreditManager.checkDate("0" + (month) + (year%100-1)));
        assertFalse(CreditManager.checkDate("0" + (month-1) + (year%100-1)));
    }

    @Test
    public void testIsNumeric(){
        assertTrue(CreditManager.isNumeric("12"));
        assertFalse(CreditManager.isNumeric(""));
        assertFalse(CreditManager.isNumeric("F"));
        assertFalse(CreditManager.isNumeric("1F"));
        assertFalse(CreditManager.isNumeric("F1"));
    }

    @Test
    public void testValidateCredit(){
        assertFalse(CreditManager.validateCredit(""));
        assertFalse(CreditManager.validateCredit("111122223333444"));//15 numbers
        assertFalse(CreditManager.validateCredit("11112222333344445"));//17 numbers
        assertFalse(CreditManager.validateCredit("WORDS"));
        //Cards that work
        assertTrue(CreditManager.validateCredit("4024007197504847"));
        assertTrue(CreditManager.validateCredit("4556472298918518"));
        assertTrue(CreditManager.validateCredit("6011589937907554"));
        //cards that dont work
        assertFalse(CreditManager.validateCredit("4024007197504846"));
    }

    @Test
    public void testFieldFilled(){
        assertTrue(CreditManager.fieldFilled("Yo lots of text"));
        assertTrue(CreditManager.fieldFilled("D"));
        assertFalse(CreditManager.fieldFilled(""));
        assertFalse(CreditManager.fieldFilled(null));
    }
}
