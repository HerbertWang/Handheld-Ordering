package com.alex.food.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.alex.food.R;
import com.alex.food.bean.CategoryDto;
import com.alex.food.utils.ConstantUtils;
/**
 * 
 * @author ALEX
 *
 */
public class CategoryAdapter extends BaseAdapter {
	private List<CategoryDto> list;
	private LayoutInflater inflater;
	private int width;
	private int height;

	public CategoryAdapter(Context context, List<CategoryDto> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
		width = ConstantUtils.screenWidth / 4;
		height = (int) (width / 1.3);
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
		if ("#FFEA4F3D".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_orange);
            holder.textView.setTextColor(0xffffffff);
		} else if ("#FF006193".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_blue);
            holder.textView.setTextColor(0xffffffff);
		} else if ("#FF9E1981".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_purple);
            holder.textView.setTextColor(0xffffffff);
		} else if ("#FF4B8522".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_green);
            holder.textView.setTextColor(0xffffffff);
		} else if ("#FFDD0079".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_pink);
            holder.textView.setTextColor(0xffffffff);
		} else if ("#FFEBEBEB".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_grey);
            holder.textView.setTextColor(0xff000000);
		} else {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_grey);
            holder.textView.setTextColor(0xff000000);
		}
		holder.textView.setText(list.get(position).getCategoryName());
		return convertView;
	}

	private class ViewHolder {
		TextView textView;
	}
}
