package com.mhy.shapeview.sample;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.mhy.shapeview.ShapeView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShapeView shapeView = findViewById(R.id.downloadSpeedTextView);
        View view = findViewById(R.id.view);
        view.setOnClickListener(null);
        RippleDrawable rippleDrawable = new RippleDrawable(
                ColorStateList.valueOf(Color.GRAY), new ColorDrawable(Color.RED), null);
        ViewCompat.setBackground(view, rippleDrawable);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
