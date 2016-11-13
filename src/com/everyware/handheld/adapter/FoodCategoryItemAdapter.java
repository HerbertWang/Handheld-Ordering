package com.everyware.handheld.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.everyware.handheld.R;
import com.everyware.handheld.bean.ItemMasterDto;
/**
 * 
 * @author ALEX
 *
 */
public class FoodCategoryItemAdapter extends BaseAdapter {
	private List<ItemMasterDto> list;
	private LayoutInflater inflater;
	private Context mContext;

	public FoodCategoryItemAdapter(Context context, List<ItemMasterDto> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
		mContext = context;
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
			convertView = inflater.inflate(R.layout.food_gridview_item, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView
					.findViewById(R.id.textView);
			holder.tvClean = (TextView) convertView.findViewById(R.id.tvClean);
			holder.tvNumber = (TextView) convertView
					.findViewById(R.id.tvNumber);
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
		if (!TextUtils.isEmpty(list.get(position).getIsOutOfStock())) {
			if ("true".equals(list.get(position).getIsOutOfStock())) {
				holder.tvClean.setVisibility(View.VISIBLE);
				holder.tvClean.setText(mContext.getString(R.string.clean));
			} else {
				holder.tvClean.setVisibility(View.GONE);
				if (!TextUtils.isEmpty(list.get(position).getIsLimitedItem())) {
					if ("true".equals(list.get(position).getIsLimitedItem())) {
						holder.tvClean.setVisibility(View.GONE);
						holder.tvNumber.setVisibility(View.VISIBLE);
						holder.tvNumber.setText(list.get(position)
								.getItemCount());
					} else {
						holder.tvClean.setVisibility(View.GONE);
						holder.tvNumber.setVisibility(View.GONE);
					}
				}
			}
		}
		holder.textView.setText(list.get(position).getItemName());
		return convertView;
	}

	private class ViewHolder {
		TextView textView;
		TextView tvClean;
		TextView tvNumber;
	}
}
