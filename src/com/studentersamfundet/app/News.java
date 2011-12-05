package com.studentersamfundet.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;

public class News {
	public String title;
	public String description;
	public String content;
	public Uri link;
	public Date pubDate;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public News(String title, String description, String content, String link, String pubDate) {
		this.title = title.trim();
		this.description = description.trim();
		this.content = content.trim();
		this.link = Uri.parse(link.trim());
		
		try {
			this.pubDate = sdf.parse(pubDate.trim());
		} catch (ParseException e) {
			this.pubDate = null;
		}
	}
}
