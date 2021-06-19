package com.aliceapps.espressoutils.hilthelpers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aliceapps.espressoutils.R;

public class HiltEmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //Toolbar
        setSupportActionBar(findViewById(R.id.test_toolbar));
    }
}
