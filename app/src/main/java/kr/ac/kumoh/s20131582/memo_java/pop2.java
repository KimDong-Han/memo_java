package kr.ac.kumoh.s20131582.memo_java;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class pop2 extends AppCompatActivity {
    private ImageView img;
    private Uri Iuri;
    private TextView tex;
    private String tts;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        
        setContentView(R.layout.activity_pop2);

        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        Intent iintet = getIntent();
        img = findViewById(R.id.img2);

       // tts  = iintet.getStringExtra("sednI");
       // Iuri = Uri.parse(tts);

        Iuri = getIntent().getParcelableExtra("sednI");
        Glide.with(this).load(Iuri).into(img);
        Log.i("URI_LOG", "URI는: "+Iuri.toString());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= mScaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f,
                    Math.min(mScaleFactor, 10.0f));

            img.setScaleX(mScaleFactor);
            img.setScaleY(mScaleFactor);
            return true;
        }
    }
}