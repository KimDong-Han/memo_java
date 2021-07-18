package kr.ac.kumoh.s20131582.memo_java;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.realm.Realm;

import static androidx.core.content.ContextCompat.getSystemService;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {
    public Activity activity;
    public Activity att ;
    private ImageButton pt1;
    private List<Memo> memoList;
    private Realm realm;
    public ClipboardManager clipboardManager;
   private String tit;
    private String B_tit;
   public static Context mMemo;
    public static Activity avt ;


    public MemoAdapter(Activity activity, List<Memo> memolist) {
        this.activity = activity;
        this.memoList = memolist;

    }






    @NonNull
    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }
    @Override
    public int getItemCount() {
        return memoList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView mtitle, mdate, mcontent;
        Button ptt;
        ImageView mimage;
        public ViewHolder(final View itemView) {
            super(itemView);
            mtitle = itemView.findViewById(R.id.mtitle);
            mdate = itemView.findViewById(R.id.mdate);
            mcontent = itemView.findViewById(R.id.mcontent);

            pt1 = itemView.findViewById(R.id.copy1);
            pt1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    B_tit= mcontent.getText().toString();
                    Toast.makeText(activity.getApplicationContext(), "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show();
                    ((MainActivity)MainActivity.mcontext).cpoy(B_tit);
                   // Toast.makeText(activity.getApplicationContext(), "88888888.", Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnCreateContextMenuListener(this);

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Context context = itemView.getContext();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",getAdapterPosition());
                    Intent mainintent = new Intent(context,MemoModify.class);
                    mainintent.putExtras(bundle);
                    mainintent.putExtra("title", memoList.get(getAdapterPosition()).getTitle());
                    mainintent.putExtra("date", memoList.get(getAdapterPosition()).getDate());
                    mainintent.putExtra("content", memoList.get(getAdapterPosition()).getContent());
                    //이미지
                    mainintent.putExtra("imguri",memoList.get(getAdapterPosition()).getImg());
                    //
                    mainintent.putExtra("itemnum", getAdapterPosition());
                    context.startActivity(mainintent);

                }
            });


        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem copy = contextMenu.add(Menu.NONE, 1, 1, "내용 복사");
            MenuItem Delete = contextMenu.add(Menu.NONE, 2, 2, "삭제");
            copy.setOnMenuItemClickListener(editmenu);
            Delete.setOnMenuItemClickListener(editmenu);
        }


        final MenuItem.OnMenuItemClickListener editmenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 1:
                        tit= mcontent.getText().toString();
                        if(tit.getBytes().length==0) {
                            AlertDialog.Builder dig = new AlertDialog.Builder(activity);
                            dig.setMessage("복사할 내용이없습니다.");
                            dig.setTitle(" ");
                            dig.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                                    .show();
                        }else{
                            Toast.makeText(activity.getApplicationContext(), "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show();
                            ((MainActivity)MainActivity.mcontext).cpoy(tit);
                        }

                        //Toast.makeText(activity.getApplicationContext(), mtitle.getText() + "복사", Toast.LENGTH_SHORT).show();

                        break;
                    case 2:

                        Toast.makeText(activity.getApplicationContext(), mtitle.getText() + "삭제", Toast.LENGTH_SHORT).show();

                        realm = Realm.getInstance(MainActivity.activity.getrealconf());
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Memo results =
                                        realm.where(Memo.class).equalTo("title", (String) mtitle.getText()).findFirst();

                                if (results.isValid()) {
                                    results.deleteFromRealm();

                                    //Intent wi = new Intent(activity.getApplicationContext(),MainActivity.class);

                                    memoList.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                    notifyItemRangeChanged(getAdapterPosition(),memoList.size());
                                }

                            }
                        });
                        realm.close();

                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }
                return true;
            }
        };

    }


    @Override
    public void onBindViewHolder(@NonNull MemoAdapter.ViewHolder holder, int position) {
        Memo data = memoList.get(position);
        holder.mtitle.setText(data.getTitle());
        holder.mdate.setText(data.getDate());
        holder.mcontent.setText(data.getContent());
        //이미지
       // Glide.with(holder.itemView.getContext()).load("Uri").into(holder.);

    }




    }


