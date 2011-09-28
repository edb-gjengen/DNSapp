package com.studentersamfundet.app;

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
}
