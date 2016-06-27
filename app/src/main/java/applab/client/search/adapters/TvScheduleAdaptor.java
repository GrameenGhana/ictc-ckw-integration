package applab.client.search.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.model.Meeting;
import applab.client.search.model.wrapper.CommunicationSchedule;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by skwakwa on 10/2/15.
 */
public class TvScheduleAdaptor extends BaseExpandableListAdapter {


    //    private final String[] groupItem;
    private Context mContext;
    public LayoutInflater minflater;
    public ExpandableListView list;
    public int[] groupIcons;
    List<String> listTitles = null;
    String [] colors ;

    List<CommunicationSchedule> communications;
    Map<String, List<CommunicationSchedule>> itemData = null;

    //    private final String[] name;
//    private final String[] location;
//    private final String[] mainCrop;
//    private final String[] groups;
    public int lastExpandedGroupPosition;

    public TvScheduleAdaptor(Context mContext,
                                   List<CommunicationSchedule> communications,
                                   Map<String, List<CommunicationSchedule>> itemizedThings,
                    String [] colors,
                                   ExpandableListView list) {
//        groupItem = groupItems;
        this.mContext = mContext;
       this.communications = communications;

        this.itemData = itemizedThings;

        this.colors = colors;
        System.out.println("Cnt : "+itemData.get("1").size());
        minflater = LayoutInflater.from(mContext);
        this.list = list;

    }

    public int getGroupCount() {
        return communications.size();
    }

    public int getChildrenCount(int groupPosition) {

        int count = 0;
        try {

            System.out.println("Chile Position " + groupPosition);
            count = itemData.get(String.valueOf(groupPosition)).size();
            System.out.println("Counts : "+count);
        } catch (Exception e) {
            System.out.println("Counting Error");
            e.printStackTrace();
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
            view = minflater.inflate(R.layout.lst_image_icon_details, viewGroup, false);
        }


        CommunicationSchedule  m= communications.get(groupPosition);

        ImageView v =(ImageView)view.findViewById(R.id.img_icon);

        TextView title = (TextView) view.findViewById(R.id.txt_title);
        TextView language = (TextView) view.findViewById(R.id.txt_item_2);
        TextView next_date = (TextView) view.findViewById(R.id.txt_item_3);
        TextView time = (TextView) view.findViewById(R.id.txt_item_4);


        title.setText(m.getTitle());
        language.setText(m.getLanguage());
        next_date.setText(m.getNextDate());
        time.setText(m.getTime());

        v.setImageResource(m.getIcon());



        return view;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = minflater.inflate(R.layout.list_simple_text_text_, viewGroup, false);
        }
        List<CommunicationSchedule> farmers = itemData.get(String.valueOf(groupPosition));
        CommunicationSchedule farmer = farmers.get(childPosition);
        TextView names = (TextView) view.findViewById(R.id.lst_text_item_titles);
        TextView icon = (TextView) view.findViewById(R.id.lst_text_item_icon);

        names.setText(Html.fromHtml(farmer.getTitle()+" &raquo; <span style='font-size:75%'>"+farmer.getNextDate()+"</span>"));
        icon.setText(String.valueOf((childPosition+1)));
        icon.setBackgroundColor(Color.parseColor(getColor()));
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

    public String getColor(){
        return colors[(new Double(Math.floor(Math.random()*colors.length)).intValue())];
    }
}


