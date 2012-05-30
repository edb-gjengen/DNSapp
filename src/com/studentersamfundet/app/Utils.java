package com.studentersamfundet.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class Utils {
	public static String intToMonthStr(int monthInt) {
		String monthString;

		switch (monthInt) {
		case 1:  monthString = "Januar";      break;
		case 2:  monthString = "Februar";     break;
		case 3:  monthString = "Mars";        break;
		case 4:  monthString = "April";       break;
		case 5:  monthString = "Mai";         break;
		case 6:  monthString = "Juni";        break;
		case 7:  monthString = "Juli";		  break;
		case 8:  monthString = "August";	  break;
		case 9:  monthString = "September";   break;
		case 10: monthString = "Oktober";     break;
		case 11: monthString = "November";    break;
		case 12: monthString = "Desember";    break;
		default: monthString = "NA"; 		  break;
		}
		return monthString;
	}
	
	public static String stripNullFromDay(String day) {
		if (day.charAt(0) == '0')
			day = day.substring(1);
		return day;
	}

	public static String convertStreamToString(InputStream is)
            throws IOException {
        /*
         * To convert the InputStream to String we use the
         * Reader.read(char[] buffer) method. We iterate until the
         * Reader return -1 which means there's no more data to
         * read. We use the StringWriter class to produce the string.
         */
        if (is != null) {
            Writer writer = new StringWriter();
 
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {        
            return "";
        }
    }
	
}

