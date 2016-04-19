package applab.client.agsmo.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.*;
import applab.client.agrihub.ui.adapter.DashboardCategoryAdapter;
import applab.client.agrihub.ui.view.PagerSlidingTabStrip;
import applab.client.agrihub.util.DashboardContent;
import applab.client.agsmo.ui.fragment.TrainingBusinessFragment;
import applab.client.agsmo.ui.fragment.TrainingTechnicalFragment;
import applab.client.search.R;
import applab.client.search.activity.BaseActivity;
import applab.client.search.activity.BaseFragmentActivity;
import applab.client.search.utils.ConnectionUtil;

import java.util.ArrayList;

public class TrainingActivity extends BaseFragmentActivity {

    private static final String TAG = TrainingActivity.class.getSimpleName();

    private MyPagerAdapter adapter;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

    private ImageView mImage;
    private TextView mFirstLine;
    private TextView mSecondLine;
    private TextView mThirdLine;

    private String mUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_home);
        //super.setDetails("myAgriHub Dashboard","Training");

        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("myAgriHub > Training");

        mImage = (ImageView) findViewById(R.id.main_avatar_image);
        mFirstLine = (TextView) findViewById(R.id.main_first_line);
        mSecondLine = (TextView) findViewById(R.id.main_second_line);
        mThirdLine = (TextView) findViewById(R.id.main_third_line);

        mImage.setImageResource(R.drawable.ic_action_person);
        mFirstLine.setText(ConnectionUtil.currentUserFullName(this));
        mSecondLine.setText(ConnectionUtil.currentUserType(this));
        mThirdLine.setText("");

        mUsername = ConnectionUtil.currentUsername(this);

        tabs = (PagerSlidingTabStrip) findViewById(R.id.activity_view_tabs);
        pager = (ViewPager) findViewById(R.id.activity_view_pager);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setCurrentItem(0);

        tabs.setOnTabReselectedListener(new PagerSlidingTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                Toast.makeText(TrainingActivity.this, "Tab reselected: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final ArrayList<String> tabNames = new ArrayList<String>() {
            {
                add("Business Management");
                add("Technical");
            }
        };

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames.get(position);
        }

        @Override
        public int getCount() {
            return tabNames.size();
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return TrainingBusinessFragment.newInstance(position, mUsername);
            } else if (position == 1) {
                return TrainingTechnicalFragment.newInstance(position, mUsername);
            } else {
                return TrainingBusinessFragment.newInstance(position, mUsername);
            }
        }
    }
}