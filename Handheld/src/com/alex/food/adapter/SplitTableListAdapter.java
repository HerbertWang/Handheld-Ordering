package com.alex.food.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alex.food.R;
import com.alex.food.bean.TableDto;
/**
 * 
 * @author ALEX
 *
 */
public class SplitTableListAdapter extends BaseAdapter {
	private List<TableDto> list;
	private Context mContext;
	private LayoutInflater inflater;

	public SplitTableListAdapter(Context context, List<TableDto> list) {
		mContext = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.split_grid_item, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView
					.findViewById(R.id.textView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(list.get(position).getTableCode());
		return convertView;
	}

	private class ViewHolder {
		TextView textView;
	}
}
