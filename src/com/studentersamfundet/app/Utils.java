package com.studentersamfundet.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {
	public static String intToMonthStr(int monthInt) {
		String monthString;

		switch (monthInt) {
		case 1:  monthString = "Jan";      break;
		case 2:  monthString = "Feb";      break;
		case 3:  monthString = "Mar";      break;
		case 4:  monthString = "Apr";      break;
		case 5:  monthString = "Mai";      break;
		case 6:  monthString = "Jun";      break;
		case 7:  monthString = "Jul";      break;
		case 8:  monthString = "Aug";      break;
		case 9:  monthString = "Sep";      break;
		case 10: monthString = "Okt";      break;
		case 11: monthString = "Nov";      break;
		case 12: monthString = "Des";      break;
		default: monthString = "NA"; 	   break;
		}
		return monthString;
	}

	/* Check for Internet connection. */
	public static boolean checkConnection(Context context) {
		ConnectivityManager conMan
		= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMan.getActiveNetworkInfo();
		return netInfo != null;
	}
}
