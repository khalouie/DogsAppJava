package rasoul.khalouie.dogsapp.view;

import android.os.Bundle;

import androidx.core.content.SharedPreferencesCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rasoul.khalouie.dogsapp.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment(){

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_prefrences,rootKey);
    }

}