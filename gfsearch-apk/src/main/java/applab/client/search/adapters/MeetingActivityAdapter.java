package applab.client.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.model.MeetingActivity;

import java.util.List;

/**
 * Created by skwakwa on 9/2/15.
 */
public class MeetingActivityAdapter extends BaseAdapter {

    private Context mContext;
    public LayoutInflater minflater;


List<MeetingActivity> meetingActivities = null;

    public int lastExpandedGroupPosition;

    public MeetingActivityAdapter(Context mContext,
                                   List<MeetingActivity> meetingActivities
                                  ) {
        this.mContext = mContext;
        this.meetingActivities = meetingActivities;
        minflater = LayoutInflater.from(mContext);
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
            view = minflater.inflate(R.layout.list_simple_text_text_, viewGroup, false);
        }
        TextView idex = (TextView) view.findViewById(R.id.lst_text_item_icon);
        TextView names = (TextView) view.findViewById(R.id.lst_text_item_title);
       MeetingActivity ac = meetingActivities.get(i);
        names.setText(ac.getActivityName());
        idex.setText(ac.getMeetingIndex());
        return view;
    }
}

