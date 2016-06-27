package applab.client.search.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.model.Farmer;
import applab.client.search.utils.IctcCKwUtil;

import java.util.List;
import java.util.Map;


/**
 * Created by Software Developer on 30/07/2015.
 */
public class ClusterAdapter extends BaseExpandableListAdapter {
    //    private final String[] groupItem;
    private Context mContext;
    public LayoutInflater minflater;
    public ExpandableListView list;
    public int[] groupIcons;
    List<String> listTitles = null;

    Map<String, List<Farmer>> clusterData = null;

    public int lastExpandedGroupPosition;

    public ClusterAdapter(Context mContext,
                          List<String> titles,
                          Map<String, List<Farmer>> clusterData,
                          int[] groupIcons,
                          ExpandableListView list) {
//        groupItem = groupItems;
        this.mContext = mContext;
        listTitles = titles;
        this.clusterData = clusterData;
        this.groupIcons = groupIcons;

        minflater = LayoutInflater.from(mContext);
        this.list = list;

    }

    public int getGroupCount() {
        return listTitles.size();
    }

    public int getChildrenCount(int groupPosition) {

        int count = 0;
        try {
            System.out.println("Chile Position " + groupPosition);
            System.out.println("Group name : " + listTitles.get(groupPosition));
            count = clusterData.get(listTitles.get(groupPosition)).size();
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
            view = minflater.inflate(R.layout.price_group_single, viewGroup, false);
        }
        TextView title = (TextView) view.findViewById(R.id.textView_title);
        String cluster = listTitles.get(groupPosition);
        title.setText(Html.fromHtml(cluster));
        title = (TextView) view.findViewById(R.id.textView_summary);
        title.setText(Html.fromHtml("" + clusterData.get(listTitles.get(groupPosition)).size() + " Farmer"));
        ImageView image = (ImageView) view.findViewById(R.id.imageView_icon);
        image.setImageResource(groupIcons[groupPosition]);
        return view;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        if (view == null) {
                view = minflater.inflate(R.layout.cluster_child_single, viewGroup, false);

        }


        List<Farmer> farmers = clusterData.get(listTitles.get(groupPosition));
        Farmer farmer = farmers.get(childPosition);


        IctcCKwUtil.setFarmerDetails(view,R.id.ccs_layout,farmer.getFullname(),farmer);
//        TextView names = (TextView) view.findViewById(R.id.textView_name);
//        TextView locations = (TextView) view.findViewById(R.id.textView_location);
//        TextView mainCrops = (TextView) view.findViewById(R.id.textView_mainCrop);
//        TextView group = (TextView) view.findViewById(R.id.textView_groups);
//        ImageView icon = (ImageView) view.findViewById(R.id.imageView_icon);
//        String crop = farmer.getMainCrop();
//        if (crop.equalsIgnoreCase("Maize")) {
//            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_maize);
//            // icon.setBackground(drawable);
//            mainCrops.setText("Maize");
//            mainCrops.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//        } else if (crop.equalsIgnoreCase("Cassava")) {
//            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_cassava);
//            mainCrops.setText("Cassava");
//            mainCrops.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            //icon.setBackgroundDrawable(drawable);
//        } else if (crop.equalsIgnoreCase("Beans")) {
//            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_beans);
//            mainCrops.setText("Beans");
//            mainCrops.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
//            // icon.setBackgroundDrawable(drawable);
//        } else if (crop.equalsIgnoreCase("Rice")) {
//            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_rice);
//            mainCrops.setText("Rice");
//            mainCrops.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            // icon.setBackgroundDrawable(drawable);
//        }
//
//
//        names.setText(farmer.getLastName() + " " + farmer.getFirstName());
//        locations.setText(farmer.getCommunity()+", "+farmer.getDistrict()+", "+farmer.getRegion());
//       // Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_maize);
//        //mainCrops.setText(crop);
//        //mainCrops.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//        group.setText("Farmer");
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


}
