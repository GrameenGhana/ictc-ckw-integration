package applab.client.search.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import applab.client.search.R;
import applab.client.search.model.Farmer;

/**
 * Created by aangjnr on 28/04/2017.
 */

public class BulkSmsRecyclerAdapter  extends RecyclerView.Adapter<BulkSmsRecyclerAdapter.BulkSmsViewHolder> {

     private Context context;
    OnItemClickListener mItemClickListener;
    List<Farmer> mFarmers;



    public BulkSmsRecyclerAdapter(Context c, List<Farmer> farmers){
        this.context = c;
        this.mFarmers = farmers;
     }

    @Override
    public BulkSmsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bulk_message_item, viewGroup, false);

        return new  BulkSmsViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final BulkSmsRecyclerAdapter.BulkSmsViewHolder viewHolder, final int position) {

        Farmer farmer = mFarmers.get(position);
        viewHolder.farmer_name.setText(farmer.getFullname());
        viewHolder.farmer_location.setText(farmer.getLocationOfLand());
        viewHolder.farmer_phone.setText(farmer.getPhoneNumber());



    }



    public class BulkSmsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView farmer_name;
        TextView farmer_location;
        TextView farmer_phone;

        RelativeLayout itemLayout;
        CheckBox checkBox;


        BulkSmsViewHolder(View itemView) {
            super(itemView);

            farmer_name = (TextView) itemView.findViewById(R.id.farmer_name);
            farmer_location = (TextView) itemView.findViewById(R.id.farmer_location);
            farmer_phone = (TextView) itemView.findViewById(R.id.farmer_phone);
            itemLayout = (RelativeLayout) itemView.findViewById(R.id.bulk_message_item_layout);
            checkBox = (CheckBox) itemView.findViewById(R.id.bulk_message_item_checkbox);


            itemView.setOnClickListener(this);
        }




        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getAdapterPosition());


            }

        }
    }


    public void setOnItemClickListener(final BulkSmsRecyclerAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mFarmers.size();

    }

    @Override
    public long getItemId(int position) {


        return  position;


    }
}