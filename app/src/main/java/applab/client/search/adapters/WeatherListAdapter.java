package applab.client.search.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.model.Weather;
import applab.client.search.utils.IctcCKwUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by skwakwa on 9/2/15.
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {

    private Context mContext;
    public LayoutInflater minflater;


    List<Weather> contents = null;


    public WeatherListAdapter(Context mContext,
                             List<Weather> meetingActivities
    ) {
        this.mContext = mContext;
        this.contents = meetingActivities;
        minflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return contents.size();
    }

    public Object getItem(int i) {
        return null;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView city;
        public TextView temp;
        public TextView desc;
        public ImageView iv;
        public TextView time;
        public ViewHolder(View v) {
            super(v);
            city = (TextView) v.findViewById(R.id.txt_weather_city);
            temp = (TextView) v.findViewById(R.id.txt_weather_temp);
            desc = (TextView) v.findViewById(R.id.txt_weather_description);
            iv = (ImageView) v.findViewById(R.id.img_weather_icon);
            time = (TextView) v.findViewById(R.id.txt_weather_time);
        }
    }

    @Override
    public WeatherListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_single, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(WeatherListAdapter.ViewHolder holder, int i) {
        holder.temp.setText(String.valueOf(contents.get(i).getTemprature()) + " C ");
        Date d = new Date(contents.get(i).getTime() * 1000);
        holder.desc.setText(String.valueOf(contents.get(i).getDetail()) + "");
        String mDrawableName = "w_" + contents.get(i).getIcon();
        int resID = mContext.getResources().getIdentifier(mDrawableName, "drawable", mContext.getPackageName());
        holder.iv.setImageResource(resID);
        if (contents.get(i).getTime() == 0l)
           holder.time.setText("-");
        else
            holder.time.setText("Up to " + IctcCKwUtil.formatStringDateTime(d, "d MMM hh:mm"));
        holder.city.setText(contents.get(i).getLocation());
    }

    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

   /* public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = minflater.inflate(R.layout.weather_single, viewGroup, false);
        }
        TextView city = (TextView) view.findViewById(R.id.txt_weather_city);
        TextView temp = (TextView) view.findViewById(R.id.txt_weather_temp);
        temp.setText(String.valueOf(contents.get(i).getTemprature()) + " C ");
        Date d = new Date(contents.get(i).getTime() * 1000);
        TextView desc = (TextView) view.findViewById(R.id.txt_weather_description);
        desc.setText(String.valueOf(contents.get(i).getDetail()) + "");
        ImageView iv = (ImageView) view.findViewById(R.id.img_weather_icon);
        String mDrawableName = "w_" + contents.get(i).getIcon();
        int resID = mContext.getResources().getIdentifier(mDrawableName, "drawable", mContext.getPackageName());
        iv.setImageResource(resID);
        TextView time = (TextView) view.findViewById(R.id.txt_weather_time);
        if (contents.get(i).getTime() == 0l)
            time.setText("-");
        else
            time.setText("Up to " + IctcCKwUtil.formatStringDateTime(d, "d MMM hh:mm"));
        city.setText(contents.get(i).getLocation());
        return view;
    }*/
}

