package com.denbondd.justweather.util

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.denbondd.justweather.R

fun AppCompatActivity.startActivityWithAnim(activity: AppCompatActivity) {
    startActivity(
        Intent(this, activity::class.java),
        ActivityOptions.makeCustomAnimation(
            this,
            R.anim.splash_enter,
            R.anim.splash_exit
        ).toBundle()
    )
}

fun AppCompatActivity.setMenuIcon(drawerLayout: DrawerLayout, toolbar: Toolbar) {
    val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openND, R.string.closeND)
    drawerLayout.addDrawerListener(toggle)
    toggle.syncState()
}