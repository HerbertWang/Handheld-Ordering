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
import com.everyware.handheld.bean.CategoryDto;
import com.everyware.handheld.utils.ConstantUtils;
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
		if ("#FFDE4C3C".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_orange);
            holder.textView.setTextColor(0xffffffff);
		} else if ("#FF029887".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_light_blue);
            holder.textView.setTextColor(0xffffffff);
		} else if ("#FF85156D".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_purple);
            holder.textView.setTextColor(0xffffffff);
		} else if ("#FF036a41".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_green);
            holder.textView.setTextColor(0xffffffff);
		} else if ("#FFDD0079".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_pink);
            holder.textView.setTextColor(0xffffffff);
		} else if ("#FFEBEBEB".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_grey);
            holder.textView.setTextColor(0xffffffff);
		} 
		else if ("#FF971620".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_red);
            holder.textView.setTextColor(0xffffffff);
		}
		else if ("#FFf2b200".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_yellow);
            holder.textView.setTextColor(0xffffffff);
		}
		else if ("#FFedd209".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_light_yellow);
            holder.textView.setTextColor(0xffffffff);
		}
		else if ("#ff4a1b04".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_red);
            holder.textView.setTextColor(0xffffffff);
		}
		
		else if ("#FF004568".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_deep_blue);
            holder.textView.setTextColor(0xffffffff);
		}
		else if ("#FF40721D".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_light_green);
            holder.textView.setTextColor(0xffffffff);
		}
		else if ("#FFfb1100".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.btn_light_red);
            holder.textView.setTextColor(0xffffffff);
		}
 else {
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
