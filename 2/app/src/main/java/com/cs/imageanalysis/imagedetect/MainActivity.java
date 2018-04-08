package com.cs.imageanalysis.imagedetect;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.cs.imageanalysis.fragment.SelectImageSourceFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SelectImageSourceFragment selectImageSourceFragment = new SelectImageSourceFragment();
        fragmentTransaction.replace(R.id.main_container, selectImageSourceFragment).commit();
    }
}
