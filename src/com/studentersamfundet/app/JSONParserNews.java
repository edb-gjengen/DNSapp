package com.studentersamfundet.app;

import java.io.InputStream;

public class JSONParserNews implements IParser {
	private static final long serialVersionUID = -2654591623794449527L;
	private final DataHandler dh;
	
	public JSONParserNews() {
		 this(null);
	}
	
	public JSONParserNews(DataHandler dh) {
		if (dh == null) {
			this.dh = new DataHandler();
		} else {
			this.dh = dh;
		}
	}

	public DataHandler parse(InputStream in) {
        String title = "";
        String description = "";
        String link = "";
        String content = "";
        String pubDate = "";
        
        for (int i = 0;i < 0;i++) { 
        	dh.insertNews(title, description, content, link, pubDate);           
        }
        return dh;
	}
}
