package applab.client.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.model.Community;
import applab.client.search.model.Farmer;
import applab.client.search.model.Meeting;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class UpcomingMeetingsAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Meeting> meeting;
    // private final List<Community> farmers;


    public UpcomingMeetingsAdapter(Context c, List<Meeting> meeting) {
        mContext = c;
        this.meeting = meeting;

    }


    public int getCount() {
        return meeting.size();
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.upcoming_meetings_single, null);

        } else {
            grid = (View) view;
        }

        TextView meeting_text = (TextView) grid.findViewById(R.id.meeting);
        Date date = new Date(Long.valueOf(meeting.get(i).getScheduledMeetingDate()));

        Format format = new SimpleDateFormat("dd MM yyyy");

        meeting_text.setText(meeting.get(i).getTitle()+ " \n"+format.format(date));
        return grid;
    }

}
