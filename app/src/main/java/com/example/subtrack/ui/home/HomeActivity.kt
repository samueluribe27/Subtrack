package com.example.subtrack.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.subtrack.R
import com.example.subtrack.ui.analysis.AnalysisActivity
import com.example.subtrack.ui.notifications.NotificationsActivity
import com.example.subtrack.ui.subscriptions.CreateSubscriptionActivity
import com.example.subtrack.ui.view.ViewActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_dashboard)
        val viewPage=findViewById<BottomNavigationView>(R.id.nav_subs)

        viewPage.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_view -> {
                    val intent = Intent(this, ViewActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_create -> {
                    val intent = Intent(this, CreateSubscriptionActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_analysis -> {
                    val intent = Intent(this, AnalysisActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
                }
            }

    }





}