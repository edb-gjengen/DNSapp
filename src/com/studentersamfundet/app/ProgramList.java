package com.studentersamfundet.app;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ProgramList extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.event_list);
        
        // View v = this.findViewById(R.id.program_button);
        // v.setClickable(false);
        
        View header = getLayoutInflater().inflate(R.layout.header, null);
        View footer = getLayoutInflater().inflate(R.layout.footer, null);
        
        ListView listView = getListView();
        listView.addHeaderView(header);
        listView.addFooterView(footer);
        
        ListAdapter adapter = createAdapter();
        setListAdapter(adapter);
    }
    
    protected ListAdapter createAdapter() {
    	String[] vals = populateList();
    	ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vals);
    	 
    	return adapter;
    }
    
    public String[] populateList() {
    	String[] val = new String[10];
    	val[0] = "test!";
    	val[1] = "test2";
    	val[2] = "test3";
    	val[3] = "test3";
    	val[4] = "test3";
    	val[5] = "test3";
    	val[6] = "test3";
    	val[7] = "test3";
    	val[8] = "test3";
    	val[9] = "test3";
    	return val;
    }
}
