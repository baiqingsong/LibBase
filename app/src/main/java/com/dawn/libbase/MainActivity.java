package com.dawn.libbase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.dawn.library.util.LImageUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView ivLogo = findViewById(R.id.iv_logo);
//        LImageUtil.ignoreHttps();
        LImageUtil.displayImage(this, "https://camera-jargee.jargee.cn/img/1E0E5266886600000000/photo_combine_b7132d75-3d6f-418b-89bf-b8a0c918a723.png", ivLogo);

    }
}