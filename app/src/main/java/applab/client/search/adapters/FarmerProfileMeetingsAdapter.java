package applab.client.search.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import applab.client.search.R;
import applab.client.search.model.Farmer;
import applab.client.search.model.FarmerInputs;
import applab.client.search.model.Meeting;

/**
 * Created by Software Developer on 16-Jun-16.
 */
public class FarmerProfileMeetingsAdapter extends BaseExpandableListAdapter {
    private final LayoutInflater minflater;
    private String[] group;
    private List<FarmerInputs> inputs;
    private List<Meeting> meetings;
    private Context mContext;
    private int[] thumbs;


    public FarmerProfileMeetingsAdapter(Context c, List<FarmerInputs> Inputs, List<Meeting> Meetings,String[] groups, int[] thumbs){
        this.mContext=c;
        this.inputs=Inputs;
        this.meetings=Meetings;
        this.group=groups;
        minflater = LayoutInflater.from(mContext);
        this.thumbs=thumbs;
    }
    @Override
    public int getGroupCount() {
        return group.length;
    }

    @Override
    public int getChildrenCount(int i) {
        int count=0;
        if(i==0){
            count=meetings.size();
        }else if(i==1){
            count=inputs.size();
        }
        return count;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = minflater.inflate(R.layout.expandable_listview_with_thumbnail_group_single, viewGroup, false);
        }

        TextView title = (TextView) view.findViewById(R.id.text);
        ImageView thumb=(ImageView) view.findViewById(R.id.thumbnail);
        title.setText(Html.fromHtml(group[i]));
        thumb.setImageResource(thumbs[i]);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
