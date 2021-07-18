package kr.ac.kumoh.s20131582.memo_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PopImgActivity extends AppCompatActivity {
    private ImageView im;
    private Uri Iuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_img);

        Intent imgint = getIntent();
        im = findViewById(R.id.pop_img);

        Iuri = getIntent().getParcelableExtra("imguri");
        Glide.with(this).load(Iuri).into(im);
    }
}