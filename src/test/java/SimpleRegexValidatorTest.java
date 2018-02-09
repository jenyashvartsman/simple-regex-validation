import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class SimpleRegexValidatorTest {

    SimpleRegexValidator validator;

    @Before
    public void setValidator() {
        validator = SimpleRegexValidator.getInstance();
    }

    @Test
    public void testValidateUsername() {
        assertEquals(validator.validate(RegexType.USERNAME, "JonSnow12"), true);
        assertEquals(validator.validate(RegexType.USERNAME, "Jon_Snow"), true);
        assertEquals(validator.validate(RegexType.USERNAME, "Jo"), false);
        assertEquals(validator.validate(RegexType.USERNAME, "JonSnow12_The-Lanistars"), false);
    }

    @Test
    public void testPassword() {
        assertEquals(validator.validate(RegexType.PASSWORD, "jonsnow1A@"), true);
        assertEquals(validator.validate(RegexType.PASSWORD, "jonSnow12$"), true);
        assertEquals(validator.validate(RegexType.PASSWORD, "jonS@"), false);
        assertEquals(validator.validate(RegexType.PASSWORD, "jonsnow12@"), false);
    }

    @Test
    public void testHex() {
        String[] valid = {"#1f1f1F", "#AFAFAF", "#1AFFa1", "#222fff", "#F00"};
        String[] invalid = {"123456", "#afafah", "#123abce", "aFaE3f", "F00", "#afaf", "#F0h"};

        for (String str : valid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.HEXADECIMAL, str), true);
        }
        for (String str : invalid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.HEXADECIMAL, str), false);
        }
    }

    @Test
    public void testEmail() {
        String[] valid = {"mkyong@yahoo.com",
                "mkyong-100@yahoo.com", "mkyong.100@yahoo.com",
                "mkyong111@mkyong.com", "mkyong-100@mkyong.net",
                "mkyong.100@mkyong.com.au", "mkyong@1.com",
                "mkyong@gmail.com.com", "mkyong+100@gmail.com",
                "mkyong-100@yahoo-test.com"};
        String[] invalid = {"mkyong", "mkyong@.com.my",
                "mkyong123@gmail.a", "mkyong123@.com", "mkyong123@.com.com",
                ".mkyong@mkyong.com", "mkyong()*@gmail.com", "mkyong@%*.com",
                "mkyong..2002@gmail.com", "mkyong.@gmail.com",
                "mkyong@mkyong@gmail.com", "mkyong@gmail.com.1a"};

        for (String str : valid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.EMAIL, str), true);
        }
        for (String str : invalid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.EMAIL, str), false);
        }
    }

    @Test
    public void testImage() {
        String[] valid = {"a.jpg", "a.gif", "a.png", "a.bmp",
                "..jpg", "..gif", "..png", "..bmp",
                "a.JPG", "a.GIF", "a.PNG", "a.BMP",
                "a.JpG", "a.GiF", "a.PnG", "a.BmP",
                "jpg.jpg", "gif.gif", "png.png", "bmp.bmp"};
        String[] invalid = {".jpg", ".gif", ".png", ".bmp",
                " .jpg", " .gif", " .png", " .bmp",
                "a.txt", "a.exe", "a.", "a.mp3",
                "jpg", "gif", "png", "bmp"};

        for (String str : valid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.IMAGE_FILE_EXTENSION, str), true);
        }
        for (String str : invalid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.IMAGE_FILE_EXTENSION, str), false);
        }
    }

    @Test
    public void testIpAddress() {
        String[] valid = {"1.1.1.1", "255.255.255.255", "192.168.1.1", "10.10.1.1", "132.254.111.10",
                "26.10.2.10", "127.0.0.1"};
        String[] invalid = {"10.10.10", "10.10", "10", "a.a.a.a", "10.0.0.a", "10.10.10.256", "222.222.2.999",
                "999.10.10.20", "2222.22.22.22", "22.2222.22.2", "10.10.10", "10.10.10"};

        for (String str : valid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.IP_ADDRESS, str), true);
        }
        for (String str : invalid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.IP_ADDRESS, str), false);
        }
    }

    @Test
    public void test12Hours() {
        String[] valid = {"1:00am", "1:00 am", "1:00 AM", "1:00pm", "1:00 pm", "1:00 PM", "12:50 pm"};
        String[] invalid = {"0:00 am", "10:00  am", "1:00", "23:00 am", "1:61 pm", "13:00 pm", "001:50 pm",
                "10:99 am", "01:00 pm", "1:00 bm"};

        for (String str : valid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.TIME_12_HOUR_FORMAT, str), true);
        }
        for (String str : invalid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.TIME_12_HOUR_FORMAT, str), false);
        }
    }

    @Test
    public void test24Hours() {
        String[] valid = {"01:00", "02:00", "13:00", "1:00", "2:00", "13:01", "23:59", "15:00", "00:00", "0:00"};
        String[] invalid = {"24:00", "12:60", "0:0", "13:1", "101:00"};

        for (String str : valid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.TIME_24_HOUR_FORMAT, str), true);
        }
        for (String str : invalid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.TIME_24_HOUR_FORMAT, str), false);
        }
    }

    @Test
    public void testDateFormat() {
        String[] valid = {
                "1/1/2010", "01/01/2020",
                "31/1/2010", "31/01/2020",
                "29/2/2008", "29/02/2008",
                "28/2/2009", "28/02/2009",
                "31/3/2010", "31/03/2010",
                "30/4/2010", "30/04/2010",
                "31/5/2010", "31/05/2010",
                "30/6/2010", "30/06/2010",
                "31/7/2010", "31/07/2010",
                "31/8/2010", "31/08/2010",
                "30/9/2010", "30/09/2010",
                "31/10/2010", "31/10/2010",
                "30/11/2010", "30/11/2010",
                "31/12/2010", "31/12/2010"
        };
        String[] invalid = {
                "32/1/2010", "32/01/2020", "1/13/2010", "29/a/2008", "a/02/2008", "333/2/2008", "29/02/200a"
        };

        for (String str : valid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.DATE_FORMAT_DDMMYYYY, str), true);
        }
        for (String str : invalid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.DATE_FORMAT_DDMMYYYY, str), false);
        }
    }

    @Test
    public void testHTML() {
        String[] valid = {"<b>", "<input value='>'>", "<input value='<'>", "<b/>", "<a href='http://www.google.com'>",
                "<br>", "<br/>", "<input value=\"\" id='test'>", "<input value='' id='test'>"};
        String[] invalid = {"<input value=\" id='test'>", "<input value=' id='test'>", "<input value=> >"};

        for (String str : valid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.HTML_TAG, str), true);
        }
        for (String str : invalid) {
            assertEquals(String.format("%s Faild", str), validator.validate(RegexType.HTML_TAG, str), false);
        }
    }
}
