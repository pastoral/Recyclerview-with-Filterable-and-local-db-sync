package com.hashpeak.bpdbcontacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.hashpeak.bpdbcontacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindingActivity: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(bindingActivity.root)

    }

}