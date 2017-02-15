package com.everyware.handheld.adapter;

import java.util.List;

import com.everyware.handheld.R;
import com.everyware.handheld.bean.MenuHeaderDto;
import com.everyware.handheld.utils.ConstantUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class MenuAdapter extends BaseAdapter {
	private List<MenuHeaderDto> list;
	private LayoutInflater inflater;
	private int width;
	private int height;

	public MenuAdapter(Context context, List<MenuHeaderDto> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
		width = (int) (ConstantUtils.screenWidth/(2.5));
		height = (int) (width / 3.3);
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
			convertView = inflater.inflate(R.layout.gridview_item, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView
					.findViewById(R.id.textView);
			LayoutParams params = new LayoutParams(width, height);
			holder.textView.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setBackgroundResource(R.drawable.btn_deep_grey);
        holder.textView.setTextColor(0xff000000);
		holder.textView.setText(list.get(position).getMenuName());
		return convertView;
	}

	private class ViewHolder {
		TextView textView;
	}
}