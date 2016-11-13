package com.everyware.handheld.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.everyware.handheld.R;
import com.everyware.handheld.bean.TableDto;
import com.everyware.handheld.utils.ConstantUtils;

/**
 * 
 * @author ALEX
 *
 */
public class GridViewAdapter extends BaseAdapter {
	private List<TableDto> list;
	private LayoutInflater inflater;
	private int width;

	public GridViewAdapter(Context context, List<TableDto> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
		width = ConstantUtils.screenWidth / 4;
	}

	public void setListData(List<TableDto> mList) {
		if (null != list) {
			list.clear();
		}
		list = mList;
		this.notifyDataSetChanged();
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
			LayoutParams params = new LayoutParams(width, width);
			holder.textView.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
        // Michael - 20150507 Split table amendment
		holder.textView.setVisibility(View.VISIBLE);
        /*if("1".equals(list.get(position).getTableStatusId()) && "true".equals(list.get(position).getIsTempTable())){
            holder.textView.setVisibility(View.GONE);
        }*/
        if("01A".equals(list.get(position).getTableCode())){
            holder.textView.setVisibility(View.VISIBLE);
            holder.textView.setWidth(10);
            holder.textView.setHeight(10);
        }
		if ("1".equals(list.get(position).getTableStatusId())) {
			holder.textView
					.setBackgroundResource(R.drawable.table_list_item_default);
            holder.textView.setTextColor(0xff000000);
		} else if ("3".equals(list.get(position).getTableStatusId())) {
			holder.textView
					.setBackgroundResource(R.drawable.table_list_item_pink);
            holder.textView.setTextColor(0xffffffff);
		} else if ("4".equals(list.get(position).getTableStatusId())) {
			holder.textView
					.setBackgroundResource(R.drawable.table_list_item_blue);
            holder.textView.setTextColor(0xffffffff);
		} else if ("5".equals(list.get(position).getTableStatusId())) {
			holder.textView
					.setBackgroundResource(R.drawable.table_list_item_purple);
            holder.textView.setTextColor(0xffffffff);
		} else if ("7".equals(list.get(position).getTableStatusId())) {
			holder.textView
					.setBackgroundResource(R.drawable.table_list_item_green);
            holder.textView.setTextColor(0xffffffff);
		} else {
			holder.textView.setVisibility(View.GONE);
            holder.textView.setTextColor(0xff000000);
		}
		holder.textView.setText(list.get(position).getTableCode());
		return convertView;
	}

	private class ViewHolder {
		TextView textView;
	}
}
