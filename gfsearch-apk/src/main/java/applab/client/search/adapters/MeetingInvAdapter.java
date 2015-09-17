package applab.client.search.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.model.Meeting;
import applab.client.search.model.MeetingActivity;

import java.util.Calendar;
import java.util.List;

/**
 * Created by skwakwa on 9/10/15.
 */
public class MeetingInvAdapter extends BaseAdapter {

    private Context mContext;
    public LayoutInflater minflater;


    List<Meeting> meetingActivities = null;
    String  [] colors = null;
    public int lastExpandedGroupPosition;

    public MeetingInvAdapter(Context mContext,
                                  List<Meeting> meetingActivities,
                                  String  [] colors
    ) {
        this.mContext = mContext;
        this.meetingActivities = meetingActivities;
        minflater = LayoutInflater.from(mContext);
        this.colors =colors;
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
            view = minflater.inflate(R.layout.list_meetings, viewGroup, false);
        }
        TextView month = (TextView) view.findViewById(R.id.list_view_month);
        TextView day = (TextView) view.findViewById(R.id.list_event_day);
        TextView title = (TextView) view.findViewById(R.id.list_event_name);
        TextView detail = (TextView) view.findViewById(R.id.lst_event_detail);
        Meeting ac = meetingActivities.get(i);;

        String [] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        Calendar cal  = Calendar.getInstance();
        cal.setTime(ac.getScheduledDate());
        month.setBackgroundColor(Color.parseColor(getColor()));
        month.setText(months[(cal.get(Calendar.MONTH))%12]);

        title.setText(ac.getTitle());
        if(ac.getType().startsWith("ind")) {
            detail.setText(ac.getFarmerDetails().getFullname());
//            detail.setText(ac.getCrop() + " " + ac.getFarmerDetails().getFullname());
            day.setText(cal.get(Calendar.DAY_OF_MONTH) + " " + String.valueOf(cal.get(Calendar.YEAR)).substring(2));
            day.setText("");
        } else {
            detail.setText(ac.getCrop());
            day.setText("");
        }
        //;
        return view;
    }

    public String getColor(){
        return colors[(new Double(Math.floor(Math.random()*colors.length)).intValue())];
    }
}

