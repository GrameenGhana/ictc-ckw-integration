package applab.client.search.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.model.Meeting;
import applab.client.search.model.wrapper.ItemWrapper;

import java.util.*;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.model.Farmer;
import applab.client.search.model.wrapper.ItemWrapper;

import java.util.List;
/**
 * Created by skwakwa on 2/4/16.
 */
public class ParentExpandableListAdapter  extends BaseExpandableListAdapter {

    

    private Context mContext;
    public LayoutInflater minflater;
    List<String> listTitles = null;
    ExpandableListView list;
    List<ItemWrapper> items= null;
    Map<String, List<ItemWrapper>> listData = null;
    int[] thumbs;

    public int lastExpandedGroupPosition;

    public ParentExpandableListAdapter(Context mContext,
                             List<ItemWrapper> items,ExpandableListView list,int[] thumbnail
    ) {this.list = list;
        this.mContext = mContext;
        this.items = items;
        this.thumbs=thumbnail;
        minflater = LayoutInflater.from(mContext);
        processData();

    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = minflater.inflate(R.layout.lst_table, viewGroup, false);
        }
        ItemWrapper farmer = items.get(i);
        TextView key = (TextView) view.findViewById(R.id.txt_tab_key);
        TextView val = (TextView) view.findViewById(R.id.txt_tab_value);


        if(farmer.getKey().equalsIgnoreCase("")){


            key.setText(String.valueOf(farmer.getValue()));
            key.setTextColor(Color.parseColor("#ff0e4918"));
            key.setTypeface(null, Typeface.BOLD);
            val.setText("");
        }else {
            key.setText(farmer.getKey());

            val.setText(String.valueOf(farmer.getValue()));
        }return view;
    }

    @Override
    public int getGroupCount() {
        return listTitles.size();
    }



    public int getChildrenCount(int groupPosition) {

        int count = 0;
        try {
            System.out.println("Chile Position " + groupPosition);
            System.out.println("Group name : " + listTitles.get(groupPosition));
            count = listData.get(listTitles.get(groupPosition)).size();
        } catch (Exception e) {

        }

        return count;
    }

    public Object getGroup(int i) {
        return null;
    }

    public Object getChild(int i, int i1) {
        return null;
    }

    public long getGroupId(int i) {
        return 0;
    }

    public long getChildId(int i, int i1) {
        return 0;
    }

    public boolean hasStableIds() {
        return true;
    }

    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = minflater.inflate(R.layout.list_item_with_thumbnail, viewGroup, false);
        }
        System.out.println("parent Post : "+groupPosition);
        String cluster = listTitles.get(groupPosition);
        System.out.println("parent Titles : "+cluster);
        TextView title = (TextView) view.findViewById(R.id.text);
        ImageView thumb=(ImageView) view.findViewById(R.id.thumbnail);
        title.setText(Html.fromHtml(cluster));
        thumb.setImageResource(thumbs[groupPosition]);
        return view;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = minflater.inflate(R.layout.lst_table, viewGroup, false);
        }
        List<ItemWrapper> farmers = listData.get(listTitles.get(groupPosition));

        ItemWrapper itemWr =  farmers.get(childPosition);;
        TextView key = (TextView) view.findViewById(R.id.txt_tab_key);
        TextView val = (TextView) view.findViewById(R.id.txt_tab_value);

        key.setText(itemWr.getKey());
        if(itemWr.isHeader()) {
//            key.setText(String.valueOf(itemWr.getValue()));
            key.setTypeface(null, Typeface.BOLD);
        }else{
            key.setTypeface(null, Typeface.NORMAL);

        }
        val.setText(itemWr.getValue());
        return view;
    }

    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public void onGroupExpanded(int groupPosition) {

        if (groupPosition != lastExpandedGroupPosition) {
            list.collapseGroup(lastExpandedGroupPosition);

        }

        super.onGroupExpanded(groupPosition);

        lastExpandedGroupPosition = groupPosition;

    }


    public void processData(){

        int cnt=0;
        String oldName="";
        listTitles = new ArrayList<String>();
        listData = new HashMap<String, List<ItemWrapper>>();
        int dItem=0;

        List<ItemWrapper>  iWrapper  = new ArrayList<ItemWrapper>();;
        for(ItemWrapper wr : items){

         if(wr.getKey().equalsIgnoreCase("")){
             if(cnt>1) {
                 System.out.println("old name "+oldName);
                 listData.put(oldName, iWrapper);
             }
             listTitles.add(wr.getValue());
             oldName = wr.getValue();

             dItem++;
             iWrapper = new ArrayList<ItemWrapper>();
         }else{
             iWrapper.add(wr);
         }

            cnt++;
        }
        listData.put(oldName, iWrapper);

        System.out.println("Counter Exp : "+dItem);
    }
}


