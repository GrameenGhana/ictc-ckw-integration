package applab.client.agsmo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import applab.client.search.R;
import applab.client.search.utils.ImageUtil;

import java.util.ArrayList;

public class TrainingBusinessFragment extends Fragment implements View.OnClickListener {

	private static final String ARG_POSITION = "position";
    private static final String ARG_USERNAME = "username";

	private int position;
    private static String username;

    public static TrainingBusinessFragment newInstance(int position, String u) {
		TrainingBusinessFragment f = new TrainingBusinessFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
        b.putString(ARG_USERNAME, u);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);
        username = getArguments().getString(ARG_USERNAME);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_training_business, container, false);
        ImageUtil.displayImage((ImageView) rootView.findViewById(R.id.a), "http://www.qaexperts.pro/assets/img/golden_winner_cup.jpg", null);
        ImageUtil.displayImage((ImageView) rootView.findViewById(R.id.b), "http://betattooideas.info/images/start-button-royalty-free-stock-photos-image-17357238.jpg", null);

        return createView(rootView);
    }

    protected View createView(View rootView) {

        /*ArrayList<Nurse> n = (eventType.equalsIgnoreCase("facility"))
                ? ModelRepository.getFacilityNurses(eventTypeId, mInProgress.isChecked(), mEligible.isChecked(), mPassed.isChecked())
                : ModelRepository.getDistrictNurses(eventTypeId, mInProgress.isChecked(), mEligible.isChecked(), mPassed.isChecked());

        AlphaInAnimationAdapter animationAdapter;
        listView.setAdapter(stickyListHeadersAdapterDecorator);
        */

		return rootView;
	}

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), "Going to course", Toast.LENGTH_SHORT).show();
    }


}