package com.studentersamfundet.app;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class News implements Serializable {
	private static final long serialVersionUID = 4015441794784305793L;
	
	public String title;
	public String description;
	public String content;
	public String link;
	public Date pubDate;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public News(String title, String description, String content, String link, String pubDate) {
		this.title = title.trim();
		this.description = description.trim();
		this.content = content.trim();
		this.link = link.trim();
		
		try {
			this.pubDate = sdf.parse(pubDate.trim());
		} catch (ParseException e) {
			this.pubDate = null;
		}
	}
}
