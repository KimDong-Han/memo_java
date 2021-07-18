package kr.ac.kumoh.s20131582.memo_java;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.URI;

import io.realm.Realm;

import static kr.ac.kumoh.s20131582.memo_java.MainActivity.activity;

public class MemoModify extends AppCompatActivity {
    String titles, dates, contents,first_con,first_title;
    private int uri_Code = 111;
    String   fi_st;
    String last_con, last_title;
    String get_uri;
    String test_uri;
    Uri Iuri=Uri.parse("android.resource://kr.ac.kumoh.s20131582.memo_java/drawable/gall.png");
    Uri Mod_Iuri;
    String drauri = "drawable://" +R.drawable.gall;
    Uri temp_uri = Uri.parse("android.resource://kr.ac.kumoh.s20131582.memo_java/"+R.drawable.gall);
    int itemnums;
    TextView date;
    EditText mtitle, mcontent;
    ImageView mImage;
    Realm realm;
    private int PICK_chg = 102;
    public static Activity avt ;
    MainActivity mv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_modify);

        Intent intent = getIntent();
        titles = intent.getStringExtra("title");
        dates = intent.getStringExtra("date");
        contents = intent.getStringExtra("content");
       get_uri = intent.getStringExtra("imguri");

        itemnums = intent.getIntExtra("itemnum",1);

        Iuri = Uri.parse(get_uri);
         fi_st = get_uri;
        Mod_Iuri = Uri.parse(get_uri);
        date = findViewById(R.id.date);
        mtitle = findViewById(R.id.title);
        mcontent = findViewById(R.id.content);

        mImage  = findViewById(R.id.img_mod);

        date.setText(dates);
        mtitle.setText(titles);
        mcontent.setText(contents);
        //이미지

        Glide.with(this).load(Iuri).into(mImage);
        first_con = mcontent.getText().toString();
        first_title = mtitle.getText().toString();

    mImage.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View view) {
            Intent imint2 = new Intent(MemoModify.this,pop2.class);

            imint2.putExtra("sednI",Iuri);
            Log.i("click","URI체크--> "+Iuri.toString());
            startActivity(imint2);
        }
    });
    mImage.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MemoModify.this);
        dialog.setTitle("수정/삭제");
           final String[] List = new String[] {"이미지 변경","삭제"};
           dialog.setItems(List, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                switch (i)
                {
                    case 0:
                        ch_img();
                       //Toast.makeText(getApplicationContext(),"수정누름",Toast.LENGTH_SHORT).show();
                       break;
                    case 1:
                        Iuri=temp_uri;
                        Glide.with(MemoModify.this).load(Iuri).into(mImage);
                        mImage.setVisibility(View.VISIBLE);
                        break;
                       // Toast.makeText(getApplicationContext(),"삭제누름",Toast.LENGTH_SHORT).show();
                }
               }
           });
           dialog.show();
            return true;
        }
    });
    }


    @Override
    public void onBackPressed() {

        last_con = mcontent.getText().toString();
        last_title = mtitle.getText().toString();
        if(mcontent.getText().toString().equals(first_con)&&mtitle.getText().toString().equals(first_title)&&
        fi_st.equals(Iuri.toString()))
        {
           finish();
        }else{
            AlertDialog.Builder dlog = new AlertDialog.Builder(this);

            dlog.setMessage("변경된 내용을 저장하시겠습니까?");

            dlog.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    modi();

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
        getMenuInflater().inflate(R.menu.memo_su,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id ==R.id.action_modify){
           // realm = Realm.getDefaultInstance(activity.getrealconf());
            realm = Realm.getInstance(activity.getrealconf());
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    final Memo result =
                            realm.where(Memo.class).equalTo("title",titles).findFirst();
                    result.setTitle(mtitle.getText().toString());
                    result.setDate(date.getText().toString());
                    result.setContent(mcontent.getText().toString());
                    //이미지
                   result.setImg(Iuri.toString());


                }
            });
            realm.close();
           delact();
           Intent mod = new Intent(MemoModify.this,MainActivity.class);
            startActivityForResult(mod,1);
            finish();


            //overridePendingTransition(R.anim.anim_slide_out_bottom,R.anim.anim_slide_in_top);
        }else if(id ==R.id.action_Del)
        {
            android.app.AlertDialog.Builder Del_dlog = new android.app.AlertDialog.Builder(this);

            Del_dlog.setMessage("메모를 삭제하시겠습니까??");

            Del_dlog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    realm = Realm.getInstance(activity.getrealconf());
                   // realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            final Memo results =
                                    realm.where(Memo.class).equalTo("title",titles).findFirst();
                            if(results.isValid()){
                                results.deleteFromRealm();
                            }
                            //Toast.makeText(getApplicationContext(),"삭제 완료",Toast.LENGTH_SHORT).show();
                        }
                    });
                    realm.close();
                    delact();
                    Intent mod = new Intent(MemoModify.this,MainActivity.class);
                    startActivityForResult(mod,1);
                    finish();

                }
            });

            Del_dlog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            Del_dlog.show();

            // overridePendingTransition(R.anim.anim_slide_out_bottom,R.anim.anim_slide_in_bottom);
        }


        return super.onOptionsItemSelected(item);
    }
    public void delact(){
        if(activity!=null){//중복호출방지//메인엑티비티에 선언후 사용해야함
            activity = (MainActivity) activity;
            activity.finish();


        }
    }
    public  void ch_img(){

        Intent img_chg = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        img_chg.addCategory(Intent.CATEGORY_OPENABLE);
        img_chg.setType("image/*");
        startActivityForResult(img_chg,PICK_chg);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_chg)
        {
            if(resultCode == RESULT_OK)
            {
                try
                {
                    Log.i("URI_LOG","STEP1");
                    final  int taskflag = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    ContentResolver resolver = this.getContentResolver();
                    Log.i("URI_LOG","STEP2");
                    resolver.takePersistableUriPermission(data.getData(),taskflag);
                    Log.i("URI_LOG","STEP3");
                    Iuri = data.getData();
                    Log.i("URI_LOG","STEP4");
                    Glide.with(this).load(Iuri).into(mImage);
                    Log.i("URI_LOG","넘어온 이미지는 =>"+Iuri.toString());

                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

        }
    }

    public void modi(){

        realm = Realm.getInstance(activity.getrealconf());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final Memo result =
                        realm.where(Memo.class).equalTo("title",titles).findFirst();
                result.setTitle(mtitle.getText().toString());
                result.setDate(date.getText().toString());
                result.setContent(mcontent.getText().toString());
                //이미지
                result.setImg(Iuri.toString());
                //Toast.makeText(getApplicationContext(), " 수정완료", Toast.LENGTH_SHORT).show();


            }
        });
        realm.close();

        delact();
        Intent mod = new Intent(MemoModify.this,MainActivity.class);
        startActivityForResult(mod,1);
        finish();
    }

}