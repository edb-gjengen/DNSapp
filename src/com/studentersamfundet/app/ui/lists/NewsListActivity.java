package com.studentersamfundet.app.ui.lists;

import com.studentersamfundet.app.News;
import com.studentersamfundet.app.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class NewsListActivity extends BaseListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        
		ListCreator<?> lc = new ListCreator<News>(this, R.id.event_list, R.id.event_list_progress_bar) {

			@Override
			protected News[] getObjects() {
				return getDataHandler().getNews();
			}
		};
        lc.execute();
    }
    
	
	@Override
	protected <T> ListAdapter createAdapter(T[] objects) {
		final News[] news = (News[])objects;
		ListAdapter adapter = new ArrayAdapter<News>(this, 0, 0, news) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
    			ViewGroup row;
    			
    			if (null == convertView) {
    				LayoutInflater inflater = getLayoutInflater();
    				row = (ViewGroup)inflater.inflate(0, null);
    			} else {
    				row = (ViewGroup)convertView;
    			}
    			
    			News article = news[position];
    			
    			TextView title = (TextView)row.findViewById(R.id.news_list_row_title);
    			TextView description = (TextView)row.findViewById(R.id.news_list_row_desc);
    			
    			title.setText(article.title);
    			description.setText(article.description);
    			
				return row;
			}
		};
		
		return adapter;
	}
}