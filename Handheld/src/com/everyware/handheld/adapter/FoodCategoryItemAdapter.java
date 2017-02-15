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
            holder.textView.setTextColor(0xff000000);
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
		else if ("#FF4a1b04".equals(list.get(position).getBackgroundColor())) {
			holder.textView.setBackgroundResource(R.drawable.icon_deep_brown);
            holder.textView.setTextColor(0xffffffff);
		} 
		
		else {
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
