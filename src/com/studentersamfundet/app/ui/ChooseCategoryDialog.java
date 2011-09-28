package com.studentersamfundet.app.ui;

import java.security.InvalidParameterException;

import com.studentersamfundet.app.Event;
import com.studentersamfundet.app.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ChooseCategoryDialog extends Dialog {
	private final Callback callback;
	private String chosenCategory = Event.ALL;
	
	public interface Callback {
		public void run(String ... message);
	}
	
	private class OnDismiss implements OnDismissListener {
		public void onDismiss(DialogInterface dialog) {
			callback.run(chosenCategory);
		}
	}
	
	public ChooseCategoryDialog(Context context, Callback callback, String[] categories) {
		super(context);
		
		if (callback == null)
			throw new InvalidParameterException("callback is not supposed to be null!");
		
		this.callback = callback;
		
		setContentView(R.layout.choose_category_dialog);
    	setTitle("Choose category");
    	setOnDismissListener(new OnDismiss());
    	populateList(context, categories);
	}
	
	private void populateList(Context context, final String[] categories) {
		ListView list = (ListView)findViewById(R.id.choose_category_list);
		list.setAdapter(new ArrayAdapter<String>(context, R.layout.choose_category_list_row, R.id.choose_category_list_row_text, categories));
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				ChooseCategoryDialog.this.chosenCategory = categories[position];
				ChooseCategoryDialog.this.dismiss();
			}
		});
	}
}
