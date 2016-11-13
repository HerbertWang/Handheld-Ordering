package com.everyware.handheld.adapter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.everyware.handheld.R;
import com.everyware.handheld.bean.ItemMasterDto;
import com.everyware.handheld.bean.OrderListBean;
import com.everyware.handheld.bean.TxSalesDetailDto;
import com.everyware.handheld.utils.ConstantUtils;
import com.everyware.handheld.utils.HandlerOrderListCallBack;

/**
 * Created by Michael on 20/9/2015.
 */
public class QuickCodeAutoCompleteAdapter extends ArrayAdapter<ItemMasterDto> implements Filterable {
    private LayoutInflater mInflater;
    //private Geocoder mGeocoder;
    private StringBuilder mSb = new StringBuilder();

    public QuickCodeAutoCompleteAdapter(final Context context) {
        super(context, -1);
        mInflater = LayoutInflater.from(context);
        //mGeocoder = new Geocoder(context);
    }

    /*@Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final TextView tv;
        if (convertView != null) {
            tv = (TextView) convertView;
        } else {
            tv = (TextView) mInflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }

        tv.setText(createFormattedItemMasterDtoFromItemMasterDto(getItem(position)));
        return tv;
    }

    private String createFormattedItemMasterDtoFromItemMasterDto(final ItemMasterDto inputItemMasterDto) {
        mSb.setLength(0);
        mSb.append(inputItemMasterDto.getItemName());
        return mSb.toString();
    }*/

    /*@Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(final CharSequence constraint) {
                List<ItemMasterDto> itemMasterDtoList = null;
                if (constraint != null) {
                    try {
                        itemMasterDtoList = ConstantUtils.allAvailableItemMasterDtoList;
                    } catch (Exception e) {
                    }
                }
                if (itemMasterDtoList == null) {
                    itemMasterDtoList = new ArrayList<ItemMasterDto>();
                }

                final FilterResults filterResults = new FilterResults();
                filterResults.values = itemMasterDtoList;
                filterResults.count = itemMasterDtoList.size();

                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(final CharSequence contraint, final FilterResults results) {
                clear();
                for (ItemMasterDto itemMasterDto : (List<ItemMasterDto>) results.values) {
                    add(itemMasterDto);
                }
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(final Object resultValue) {
                return resultValue == null ? "" : ((ItemMasterDto) resultValue).getItemName();
            }
        };
        return myFilter;
    }*/
}
