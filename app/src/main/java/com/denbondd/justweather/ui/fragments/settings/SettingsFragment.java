package com.denbondd.justweather.ui.fragments.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.denbondd.justweather.R;
import com.denbondd.justweather.ui.activities.splash.SplashActivity;
import com.denbondd.justweather.util.ActivityExtensions;

import java.util.Objects;

import static com.denbondd.justweather.util.Constants.AUTHOR_LINKEDIN_LINK;
import static com.denbondd.justweather.util.Constants.GITHUB_REPO_LINK;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Preference gitHubRepo = Objects.requireNonNull(findPreference(getString(R.string.githubrepo_key)));
        Preference authorLinkedIn = Objects.requireNonNull(findPreference(getString(R.string.author_linkedin_key)));
        Preference language = Objects.requireNonNull(findPreference(getString(R.string.language_key)));

        gitHubRepo.setOnPreferenceClickListener(preference -> {
            openLink(GITHUB_REPO_LINK);
            return false;
        });

        authorLinkedIn.setOnPreferenceClickListener(preference -> {
            openLink(AUTHOR_LINKEDIN_LINK);
            return false;
        });

        language.setOnPreferenceChangeListener((preference, newValue) -> {
            PreferenceManager.getDefaultSharedPreferences(requireContext()).edit().putString(getString(R.string.language_key), (String) newValue).apply();
            ActivityExtensions.startSplashActivityWithAnim((AppCompatActivity) requireActivity(), new SplashActivity());
            requireActivity().finish();
            return false;
        });
    }

    private void openLink(String link) {
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}