package com.joshualorett.networksample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.joshualorett.networksample.sample.CharacterGetter;
import com.joshualorett.networksample.sample.NetworkCharacterGetter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CharacterGetter characterGetter = new NetworkCharacterGetter.Factory().create();
    }
}
