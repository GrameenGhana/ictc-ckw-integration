package applab.client.agrihub.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import applab.client.agrihub.util.DashboardContent;
import applab.client.search.R;
import applab.client.agrihub.ui.adapter.DashboardCategoryAdapter;
import applab.client.search.activity.BaseActivity;
import applab.client.search.utils.*;

public class DashboardMainActivity extends BaseActivity {

    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.IctcTheme);
        setContentView(R.layout.activity_dashboard_main);
        super.setDetails("myAgriHub Dashboard","Startup");

        ActionBar mActionBar = getActionBar();
//        mActionBar.setDisplayShowHomeEnabled(true);
       // mActionBar.setDisplayShowTitleEnabled(true);
       // mActionBar.setTitle("myAgriHub");

        ImageView mImage = (ImageView) findViewById(R.id.main_avatar_image);
        TextView mFirstLine = (TextView) findViewById(R.id.main_first_line);
        TextView mSecondLine = (TextView) findViewById(R.id.main_second_line);
        TextView mThirdLine = (TextView) findViewById(R.id.main_third_line);

        mImage.setImageResource(R.mipmap.ic_action_person);
        mFirstLine.setText(ConnectionUtil.currentUserFullName(this));
        mSecondLine.setText(ConnectionUtil.currentUserType(this));
        mThirdLine.setText("");

        mListView = (ListView) findViewById(R.id.list_view);
        setAdapter(ConnectionUtil.currentUserType(this));
    }

    private void setAdapter(String usertype) {
        String title = "";
        boolean addHeaderView = false;
        BaseAdapter adapter = new DashboardCategoryAdapter(this, DashboardContent.getContent(usertype), false);

        if (addHeaderView) {
            View headerView = getLayoutInflater().inflate(R.layout.header_dashboard_list, mListView, false);
            ((TextView) headerView.findViewById(R.id.header_title)).setText(title);
            mListView.addHeaderView(headerView);
        } else {
            setTitle(title);
        }

        mListView.setAdapter(adapter);
    }
}