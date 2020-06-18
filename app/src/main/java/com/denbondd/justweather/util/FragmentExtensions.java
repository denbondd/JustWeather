package com.denbondd.justweather.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.denbondd.justweather.R;

public class FragmentExtensions {

    /** function for replacing or adding fragment to container
     *
     * @param currActivity current activity
     * @param fragment fragment that we want to add or replace
     * @param tag fragmnet's tag
     * @param containerId id of container where we want to put fragment
     * @param addOrReplace if want to add - it's true, if replace - false
     * @param needAnim whether you need anim or not
     * @param addToBackStack whether you want fragment to be added to backstack
     */
    public static void replaceFragmentWithAnim(
            AppCompatActivity currActivity,
            Fragment fragment,
            String tag,
            int containerId,
            boolean addOrReplace,
            boolean needAnim,
            boolean addToBackStack
    ) {
        FragmentTransaction transaction = currActivity.getSupportFragmentManager().beginTransaction();
        if (needAnim) {
            transaction.setCustomAnimations(
                    R.anim.fragment_enter,
                    0,
                    0,
                    R.anim.fragment_exit
            );
        }
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        if (addOrReplace) {
            transaction.add(containerId, fragment, tag).commit();
        } else {
            transaction.replace(containerId, fragment, tag).commit();
        }
    }
}
