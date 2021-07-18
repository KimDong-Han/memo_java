package kr.ac.kumoh.s20131582.memo_java;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoActivity extends AppCompatActivity {
    private Activity MemoActivity=this;
    private TextView tvDate;
    private FloatingActionButton fbt2;
    private FloatingActionButton fbt3;
    private EditText title;
    private EditText content;
    private Button cp;
    private Bitmap bimg2;
    private final int RequestCode = 201;
    private ImageView img;
    private String titles,contents,dates,dates2;
    private String dates32;
    private String st_uri;
    private String t_uri = "drawable://" +R.drawable.gall;
    private Uri  Iuri  =Uri.parse("android.resource://kr.ac.kumoh.s20131582.memo_java/"+R.drawable.gall);

    private int PICK = 101;

    @SuppressLint("WrongViewCast")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        tvDate = findViewById(R.id.date);
        title =(EditText)findViewById(R.id.title);
        content= (EditText)findViewById(R.id.content);
        cp = (Button)findViewById(R.id.copy1);
        img = findViewById(R.id.im1);
        long now1 = System.currentTimeMillis();
        fbt2 = (FloatingActionButton) findViewById(R.id.fbt2);
        fbt3 = (FloatingActionButton) findViewById(R.id.fbt3);
        Date d = new Date(now1);
        SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        tvDate.setText(""+adf.format(d));
        title.setText(""+adf.format(d)+"의 메모");
        dates32 = adf.format(d);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent imint = new Intent(MemoActivity.this,PopImgActivity.class);
                imint.putExtra("imguri",Iuri);
                startActivity(imint);
            }
        });
        fbt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                img.setVisibility(View.VISIBLE);
                Intent img_itt = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                img_itt.addCategory(Intent.CATEGORY_OPENABLE);
                img_itt.setType("image/*");
                //img_itt.setAction(Intent.ACTION_GET_CONTENT);


                startActivityForResult(img_itt,PICK);
            }
        });
        fbt2.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                    titles = title.getText().toString();
                    dates = tvDate.getText().toString();
                    contents = content.getText().toString();
                if(titles.getBytes().length==0&&contents.getBytes().length==0){
                    AlertDialog.Builder dig = new AlertDialog.Builder(MemoActivity);
                        dig.setMessage("메모를 입력하세요");
                    
                        dig.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                }else{

                    Intent add = new Intent(MemoActivity.this, MainActivity.class);
                    add.putExtra("title", titles);
                    add.putExtra("date", dates);
                    add.putExtra("content", contents);
                    //이미지
                    add.putExtra("img",Iuri.toString());

                    setResult(RESULT_OK, add);

                    finish();

                }


            }
        });


    }


    @Override
    public void onBackPressed() {
        if(content.getText().toString().length()==0&&title.getText().toString().length()==0)
        {

            //Toast.makeText(getApplicationContext(),"app1",Toast.LENGTH_SHORT).show();
            this.finish();
        }else{
            android.app.AlertDialog.Builder dlog = new android.app.AlertDialog.Builder(this);

            dlog.setMessage("메모를 저장하시겠습니까?");

            dlog.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   fbt2.callOnClick();

                }
            });

            dlog.setNegativeButton("저장안함", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dlog.show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memo_add,menu);
        getMenuInflater().inflate(R.menu.memo_cam,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_save) {
            //Toast.makeText(getApplicationContext(), "SAVE Memo", Toast.LENGTH_SHORT).show();
            titles = title.getText().toString();
            dates = tvDate.getText().toString();
            contents = content.getText().toString();

            Intent add = new Intent(MemoActivity.this, MainActivity.class);
            add.putExtra("title", titles);
            add.putExtra("date", dates);
            add.putExtra("content", contents);
            //이미지 넘기기
            add.putExtra("imguri",Iuri.toString());
            setResult(RESULT_OK, add);

            finish();
        }else if(id == R.id.action_cam){

            Intent camm = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
            startActivity(camm);



        }
        return super.onOptionsItemSelected(item);
    }
    //이미지 삽입
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK)
        {
            if(resultCode == RESULT_OK)
            {
              try
              {
                  Log.i("URI_LOG","STEP1");
               final  int taskflag = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                       | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                 ContentResolver  resolver = this.getContentResolver();
                  Log.i("URI_LOG","STEP2");
                  resolver.takePersistableUriPermission(data.getData(),taskflag);
                  Log.i("URI_LOG","STEP3");
                  Iuri = data.getData();
                  Log.i("URI_LOG","STEP4");
                  Glide.with(this).load(Iuri).into(img);
                    Log.i("URI_LOG","넘어온 이미지는 =>"+Iuri.toString());

              }catch (Exception e)
              {
                  e.printStackTrace();
              }

            }

        }



    }
}