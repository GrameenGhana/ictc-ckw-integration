package applab.client.search.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
public class ClusterAdapter extends BaseExpandableListAdapter {


    private final String[] groupItem;
    private Context mContext;
    public LayoutInflater minflater;
    public ExpandableListView list;
    public int[] groupIcons;
    private final String[] name;
    private final String[] location;
    private final String[] mainCrop;
    private final String[] groups;
    public int lastExpandedGroupPosition;
    public ClusterAdapter(Context mContext,
                            String[] groupItems,
                            int[] groupIcons,
                            String[] name,
                            String[] location,
                            String[] mainCrop,
                            String[] groups,
                            ExpandableListView list) {
        groupItem = groupItems;
        this.groupIcons=groupIcons;
        this.mContext=mContext;
        this.name=name;
        this.location=location;
        this.mainCrop=mainCrop;
        this.groups=groups;
        minflater = LayoutInflater.from(mContext);
        this.list=list;

    }
    public int getGroupCount() {
        return groupItem.length;
    }

    public int getChildrenCount(int groupPosition) {
        int count=0;
        if(groupPosition==0){
            count=name.length;
        }else if(groupPosition==1){
            count=name.length;
        }else if(groupPosition==2){
            count=name.length;
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
            view = minflater.inflate(R.layout.cluster_child_single,viewGroup, false);
        }
        TextView names=(TextView) view.findViewById(R.id.textView_name);
        TextView locations=(TextView) view.findViewById(R.id.textView_location);
        TextView mainCrops=(TextView) view.findViewById(R.id.textView_mainCrop);
        TextView group=(TextView) view.findViewById(R.id.textView_groups);
        ImageView icon=(ImageView) view.findViewById(R.id.imageView_icon);
        if(mainCrop[childPosition].equalsIgnoreCase("Maize")){
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_maize);
          // icon.setBackground(drawable);
        }else if(mainCrop[childPosition].equalsIgnoreCase("Cassava")){
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_cassava);
            //icon.setBackgroundDrawable(drawable);
        }else if(mainCrop[childPosition].equalsIgnoreCase("Beans")){
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_beans);
           // icon.setBackgroundDrawable(drawable);
        }else if(mainCrop[childPosition].equalsIgnoreCase("Rice")){
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_rice);
           // icon.setBackgroundDrawable(drawable);
        }
        names.setText(name[childPosition]);
        locations.setText(location[childPosition]);
        mainCrops.setText(mainCrop[childPosition]);
        group.setText(groups[childPosition]);
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
