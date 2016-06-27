package applab.client.search.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.model.Meeting;
import applab.client.search.ui.ImageViewerActivity;
import applab.client.search.utils.ImageUtils;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by skwakwa on 9/29/15.
 */
public class FarmerMeetingGrpAdapter extends BaseExpandableListAdapter {


    //    private final String[] groupItem;
    private Context mContext;
    public LayoutInflater minflater;
    public ExpandableListView list;
    public int[] groupIcons;
    List<String> listTitles = null;
    String [] colors ;

    Map<String, List<Meeting>> clusterData = null;

    //    private final String[] name;
//    private final String[] location;
//    private final String[] mainCrop;
//    private final String[] groups;
    public int lastExpandedGroupPosition;

    public FarmerMeetingGrpAdapter(Context mContext,
                          List<String> titles,
                          Map<String, List<Meeting>> clusterData,

                          String [] colors,
                          ExpandableListView list) {
//        groupItem = groupItems;
        this.mContext = mContext;
        listTitles = titles;
        this.clusterData = clusterData;
        this.groupIcons = groupIcons;

        minflater = LayoutInflater.from(mContext);
        this.list = list;
        this.colors = colors;

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
            view = minflater.inflate(R.layout.expandable_listview_with_thumbnail_group_single, viewGroup, false);
        }

        RelativeLayout l = (RelativeLayout) view.findViewById(R.id.simp_rel_lay);

       // l.setBackgroundColor(Color.parseColor("#87A03B"));
//#87A03B

        TextView names = (TextView) view.findViewById(R.id.text);
        //TextView icon = (TextView) view.findViewById(R.id.lst_text_item_icon);
        String [] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        ImageView thumbnail=(ImageView) view.findViewById(R.id.thumbnail);
        String cluster = listTitles.get(groupPosition);
        names.setText(Html.fromHtml(cluster));
        names.setTextColor(Color.parseColor("#000000"));
        Meeting  m= clusterData.get(listTitles.get(groupPosition)).get(0);

        Calendar cal  = Calendar.getInstance();
        cal.setTime(m.getScheduledDate());
        switch (months[(cal.get(Calendar.MONTH))%12]){
            case "Jan":
                thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.jan));
                break;
            case "Feb":
                thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.feb));
                break;
            case "Mar":
                thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.mar));
                break;
            case "Apr":
                thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.apr));
                break;
            case "May":
                thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.may));
                break;
            case "Jun":
                thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.jun));
                break;
            case "Jul":
                thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.jul));
                break;
            case "Aug":
                thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.aug));
                break;
            case "Sep":
                thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sep));
                break;
            case "Oct":
                thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.oct));
                break;
            case "Nov":
                thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.nov));
                break;
            case "Dec":
                thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.dec));
                break;
        }
        //icon.setBackgroundColor(Color.parseColor(getColor()));
        //icon.setText(months[(cal.get(Calendar.MONTH))%12]);


        return view;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = minflater.inflate(R.layout.circle_image_thumbnail_list_single, viewGroup, false);
        }
        List<Meeting> farmers = clusterData.get(listTitles.get(groupPosition));
        final Meeting farmer = farmers.get(childPosition);
        TextView names = (TextView) view.findViewById(R.id.name);
        //TextView icon = (TextView) view.findViewById(R.id.lst_text_item_icon1);
        ImageView img = (ImageView) view.findViewById(R.id.farmerImg);


        LinearLayout l = (LinearLayout) view.findViewById(R.id.simp_rel_lay);
        if(farmer.getFarmerDetails()== null)
            l.setVisibility(View.GONE);
        else {
            //To check it out
            if (farmer.getAttended() == 1 && childPosition > 0) {

                names.setTextColor(Color.parseColor("#cccccc"));
                //icon.setBackgroundColor(Color.parseColor("#cccccc"));
            } else {
                l.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            names.setText(farmer.getFarmerDetails().getFullname());
            final String fileLoc = ImageUtils.FULL_URL_PROFILE_PIX+"/"+farmer.getFarmerDetails().getFarmID()+".jpg";
            File f =  new File(fileLoc);
            if(f.exists()){
                System.out.println("File Loc"+fileLoc);
                img.setImageDrawable(ImageUtils.getICTCImageAsDrawable(mContext,fileLoc));
                img.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            System.out.println("on touch : "+fileLoc);

                            Intent intent = new Intent(mContext,ImageViewerActivity.class);
                            intent.putExtra(ImageViewerActivity.IMAGE_TYPE, "ictc");
                            intent.putExtra(ImageViewerActivity.IMAGE_LOC, fileLoc);
                            intent.putExtra(ImageViewerActivity.IMAGE_DETAILS, farmer.getFarmerDetails().getFullname());
                           mContext.startActivity(intent);

                            return true;


                        }
                        return false;
                    }
                });
            }else{
                img.setImageResource(R.mipmap.ic_person);

            }

            //icon.setText(String.valueOf(farmer.getFarmerDetails().getLastName().charAt(0)));
        }return view;
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

