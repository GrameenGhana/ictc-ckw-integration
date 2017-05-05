package applab.client.search.settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import applab.client.search.R;

import java.util.List;

/**
 * Activity to support the editing of preferences.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.IctcTheme);
        setTitle(getResources().getString(R.string.action_settings));


        SettingsManager.getInstance().setDefaultSettings(false);
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preferences_headers, target);
    }

    public static class GeneralFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.general_preferences);
        }
    }

    public static class ConnectionFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.connection_preferences);
        }
    }

    public static class BackgroundSynchronization extends PreferenceFragment {
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.background_synchronization);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = this.getParentActivityIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                this.startActivity(intent);
                break;
        }
        return true;
    }


    protected boolean isValidFragment (String fragmentName) {
        return true;
    }
}
