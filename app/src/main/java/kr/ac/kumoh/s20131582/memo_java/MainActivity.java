package kr.ac.kumoh.s20131582.memo_java;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.Transliterator;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.RealmSchema;
import io.realm.Sort;

import static androidx.core.content.ContextCompat.getSystemService;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private Memo Memoinfo;
    private FloatingActionButton fbt;
    private MemoAdapter memoAdapter;
    public List<Memo> mlist = new ArrayList<>();
    public RecyclerView rcv;
    public static MainActivity activity = null;
    public static Context mcontext;
    private Button cp;
    String last_con, last_title;
    String first_con, first_title;
    EditText mtitle, mcontent;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(0, 0);
        overridePendingTransition(0, 0);
        finish();
        startActivity(new Intent(MainActivity.this, MainActivity.class));

        overridePendingTransition(0, 0);

        if (resultCode == RESULT_OK) {
//

            String title = data.getStringExtra("title");
            String date = data.getStringExtra("date");
            String content = data.getStringExtra("content");
            String img = data.getStringExtra("img");
            realm.beginTransaction();
            Memoinfo = realm.createObject(Memo.class);
            Memoinfo.setTitle(title);
            Memoinfo.setDate(date);
            Memoinfo.setContent(content);
            Memoinfo.setImg(img);

            realm.commitTransaction();

            mlist.add(new Memo(title, date, content, img));

            memoAdapter = new MemoAdapter(MainActivity.this, mlist);

            rcv.setAdapter(memoAdapter);


        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }


        activity = this;

        Realm.init(this);
        mcontext = this;

        RealmConfiguration conf = new RealmConfiguration.Builder()
                //.deleteRealmIfMigrationNeeded()
                .schemaVersion(3)
               .migration(new Migration())
                .build();

        realm = Realm.getInstance(conf);
        RealmResults<Memo> realmResults = realm.where(Memo.class).findAllAsync().sort("date", Sort.DESCENDING);

        //realm.commitTransaction();
        rcv = findViewById(R.id.recycler_view);


        fbt = (FloatingActionButton) findViewById(R.id.fbt);
        fbt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Add Memo", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MemoActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.anim_slide_in_left, 0);
            }
        });

        for (Memo memo : realmResults) {
            mlist.add(new Memo(memo.getTitle(), memo.getDate(), memo.getContent(), memo.getImg()));
            memoAdapter = new MemoAdapter(MainActivity.this, mlist);

            rcv.setAdapter(memoAdapter);

        }


    }

    public void cpoy(String cpdata) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", cpdata);
        clipboardManager.setPrimaryClip(clip);
    }
    public RealmConfiguration getrealconf()
    {
       return  new RealmConfiguration.Builder()
                //.deleteRealmIfMigrationNeeded()
                .schemaVersion(3)
                .migration(new Migration())
                .build();
    }

    public class Migration implements RealmMigration {
        @Override
        public boolean equals(@Nullable Object obj) {
            return (obj instanceof Migration);
        }

        @Override
        public int hashCode() {
            return 37;
        }

        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            RealmSchema rsc = realm.getSchema();

            if (oldVersion == 0) {
                RealmObjectSchema mMemoSchema = rsc.get("Memo");
                mMemoSchema.addField("imgurl", String.class, null)
                        .transform(new RealmObjectSchema.Function() {
                            @Override
                            public void apply(DynamicRealmObject obj) {
                                obj.set("imgurl", null);
                            }
                        });
                oldVersion++;

            }

        }
    }
}