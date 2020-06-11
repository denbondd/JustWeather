package com.denbondd.justweather.util;

import android.app.ActivityOptions;
import android.content.Intent;

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
}
