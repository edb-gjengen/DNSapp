package com.studentersamfundet.app.ui;

import java.text.SimpleDateFormat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.widget.TextView;
import com.studentersamfundet.app.News;
import com.studentersamfundet.app.R;

public class NewsViewActivity extends BaseDnsActivity {
	News article;
	
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.news_view);
	        
	        Intent intent = getIntent();
	        this.article = (News)intent.getSerializableExtra("news");
	        final News a = this.article;
	        
	        if (a == null) {
	        	throw new Error("This should NEVER happen");
	        }
	        
	        TextView title = (TextView) findViewById(R.id.news_view_title);
	        TextView description = (TextView) findViewById(R.id.news_view_description);
	        TextView datetime = (TextView) findViewById(R.id.news_view_datetime);
	        
	        title.setText(Html.fromHtml(a.getTitle()));
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        datetime.setText(a.getPubDate() != null ? dateFormat.format(a.getPubDate()) : "");
	        
	        StringBuilder sb = new StringBuilder();
	        
	        if (a.getDescription().length() > 0) {
	        	sb.append("<b>");
	        	sb.append(a.getDescription());
	        	sb.append("</b><br/><br/>");
	        }
	        
	        if (a.getContent().length() > 0) {
	        	sb.append(a.getContent());
	        }
	        
	        description.setText(Html.fromHtml(sb.toString()));
	        Linkify.addLinks(description, Linkify.ALL);
	    }
}
