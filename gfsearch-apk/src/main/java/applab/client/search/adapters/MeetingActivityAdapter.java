package applab.client.search.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.model.Farmer;
import applab.client.search.model.MeetingActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by skwakwa on 9/2/15.
 */
public class MeetingActivityAdapter extends BaseAdapter {


    //    private final String[] groupItem;
    private Context mContext;
    public LayoutInflater minflater;
    public ExpandableListView list;


List<MeetingActivity> meetingActivities = null;

    public int lastExpandedGroupPosition;

    public MeetingActivityAdapter(Context mContext,
                                   List<MeetingActivity> meetingActivities,
                                   ExpandableListView list) {
//        groupItem = groupItems;
        this.mContext = mContext;

        this.meetingActivities = meetingActivities;

        minflater = LayoutInflater.from(mContext);
        this.list = list;

    }

    public int getCount() {
        return meetingActivities.size();
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = minflater.inflate(R.layout.meeting_activity, viewGroup, false);
        }
        TextView idex = (TextView) view.findViewById(R.id.meet_index_itm);
        TextView names = (TextView) view.findViewById(R.id.list_meet_name);
       MeetingActivity ac = meetingActivities.get(i);
        names.setText(ac.getActivityName());
        idex.setText(ac.getMeetingIndex());
        return view;
    }
}

