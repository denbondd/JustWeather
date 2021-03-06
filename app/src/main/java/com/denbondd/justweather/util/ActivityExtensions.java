package com.denbondd.justweather.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denbondd.justweather.R;

public class ActivityExtensions {
    public static void startSplashActivityWithAnim(AppCompatActivity currentActivity, AppCompatActivity nextActivity) {
        currentActivity.startActivity(
                new Intent(currentActivity, nextActivity.getClass()),
                ActivityOptions.makeCustomAnimation(
                        currentActivity,
                        R.anim.splash_enter,
                        R.anim.splash_exit
                ).toBundle()
        );
    }

    public static void startSplashActivityWithAnim(AppCompatActivity currentActivity, Intent nextIntent) {
        try {
            currentActivity.startActivity(
                    nextIntent,
                    ActivityOptions.makeCustomAnimation(
                            currentActivity,
                            R.anim.splash_enter,
                            R.anim.splash_exit
                    ).toBundle()
            );
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void setMenuIcon(AppCompatActivity currentAct, DrawerLayout drawerLayout, Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                currentAct,
                drawerLayout,
                toolbar,
                R.string.openND,
                R.string.closeND
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
