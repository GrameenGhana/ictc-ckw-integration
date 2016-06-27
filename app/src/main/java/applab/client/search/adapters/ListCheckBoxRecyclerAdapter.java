package applab.client.search.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import applab.client.search.R;
import applab.client.search.model.Farmer;

/**
 * Created by Software Developer on 16-Jun-16.
 */
public class ListCheckBoxRecyclerAdapter extends RecyclerView.Adapter<ListCheckBoxRecyclerAdapter.ViewHolder>{
    private final Context mContext;
    private final List<Farmer> farmers;
    private final boolean [] selected;
    String  [] colors = null;
    public ListCheckBoxRecyclerAdapter(Context c, List<Farmer> farmers, String  [] colors) {
        mContext = c;
        this.farmers = farmers;
        this.selected = new  boolean[farmers.size()];
        Arrays.fill(selected,false);
        this.colors = colors;

    }
    @Override
    public ListCheckBoxRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_checkbox_activity, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        vh.farmer=(TextView) v.findViewById(R.id.lst_farmer);
        vh.chk=(CheckBox) v.findViewById(R.id.lst_chk);
        vh.tr=(ImageView) v.findViewById(R.id.lst_farmer_initial);
        return vh;

    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public CheckBox chk;
        public TextView farmer;
        public ImageView tr;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(mContext,
                    "Clicked on Checkbox: " + farmers.get(getAdapterPosition()).getFullname() +
                            " is " ,
                    Toast.LENGTH_LONG).show();
            System.out.println("Selected " + farmers.get(getAdapterPosition()).getFullname());
        }
    }
    @Override
    public void onBindViewHolder(ListCheckBoxRecyclerAdapter.ViewHolder holder, int position) {
       holder.farmer.setText(farmers.get(position).getFullname());
        holder.chk.setTag(position);

        holder.chk.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        int pos = (Integer) cb.getTag();
                        System.out.println("List Item : "+pos);
                        Farmer fm = farmers.get(pos);
                        Toast.makeText(mContext,
                                "Clicked on Checkbox: " + fm.getFullname() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();

                        selected[pos] =cb.isChecked();
                    }
                });

    }
    public boolean[] getSelectedIndex(){
        return selected;

    }
    @Override
    public int getItemCount() {
        return 0;
    }
}
