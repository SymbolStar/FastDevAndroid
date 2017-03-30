package cn.com.igdj.library.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.igdj.library.R;

public class DialogListAdapter extends BaseAdapter{

	private String[] items;
	private Context mContext;
	
	public DialogListAdapter(Context context, String[] items){
		this.mContext = context;
		this.items = items;
	}
	@Override
	public int getCount() {
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_item_view, null);
		}
		
		TextView text = (TextView) convertView.findViewById(R.id.item_text);
		text.setText(items[position]);
		
		return convertView;
	}

}
