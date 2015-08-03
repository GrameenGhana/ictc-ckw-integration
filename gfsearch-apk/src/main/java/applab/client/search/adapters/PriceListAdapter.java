package applab.client.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import applab.client.search.R;



/**
 * Created by Software Developer on 30/07/2015.
 */
public class PriceListAdapter extends BaseExpandableListAdapter {


    private final String[] groupItem;
    private Context mContext;
    public LayoutInflater minflater;
    public ExpandableListView list;
    public int[] groupIcons;
    private final String[] maizePrices;
    private final String[] ricePrices;
    private final String[] beansPrices;
    public int lastExpandedGroupPosition;
    public PriceListAdapter(Context mContext,
                            String[] groupItems,
                            int[] groupIcons,
                            String[] maizePrices,
                            String[] ricePrices,
                            String[] beansPrices,
                            ExpandableListView list) {
        groupItem = groupItems;
        this.groupIcons=groupIcons;
        this.mContext=mContext;
        this.maizePrices=maizePrices;
        this.ricePrices=ricePrices;
        this.beansPrices=beansPrices;
        minflater = LayoutInflater.from(mContext);
        this.list=list;

    }
    public int getGroupCount() {
        return groupItem.length;
    }

    public int getChildrenCount(int groupPosition) {
        int count=0;
        if(groupPosition==0){
            count=maizePrices.length;
        }else if(groupPosition==1){
            count=ricePrices.length;
        }else if(groupPosition==2){
            count=beansPrices.length;
        }
        return count ;
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
            view = minflater.inflate(R.layout.price_group_single,viewGroup, false);
        }

        TextView title=(TextView) view.findViewById(R.id.textView_title);
        title.setText(groupItem[groupPosition]);

        ImageView image=(ImageView) view.findViewById(R.id.imageView_icon);

            image.setImageResource(groupIcons[groupPosition]);

        return view;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = minflater.inflate(R.layout.price_child_single,viewGroup, false);
        }

        TextView title=(TextView) view.findViewById(R.id.textView_price);
        if(groupPosition==0){
            title.setText(maizePrices[childPosition]);
        }else if(groupPosition==1){
            title.setText(ricePrices[childPosition]);
        }else if(groupPosition==2){
            title.setText(beansPrices[childPosition]);
        }




        return view;
    }

    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    public void onGroupExpanded(int groupPosition) {

        if(groupPosition != lastExpandedGroupPosition){
            list.collapseGroup(lastExpandedGroupPosition);

        }

        super.onGroupExpanded(groupPosition);

        lastExpandedGroupPosition = groupPosition;

    }
}
