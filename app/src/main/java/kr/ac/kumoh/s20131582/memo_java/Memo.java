package kr.ac.kumoh.s20131582.memo_java;

import android.media.Image;

import io.realm.RealmObject;
import io.realm.annotations.Required;
public class Memo extends RealmObject {

    private String title; //제목들어감
    private String date; //날짜
    private String content; //내용
    private String imgurl;//사진

    public Memo()
    {
        this.title ="Empty";
    }

    public Memo(String title,String date,String content,String imgurl)
    {
        this.title = title;
        this.date = date;
        this.content = content;
        this.imgurl = imgurl;

    }
    public String getTitle() { return title; }
    public String getDate(){ return date; }
    public String getContent(){ return content; }
    public String getImg(){ return imgurl; }


    public void setTitle(String title) {
        this.title = title;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setImg(String imgurl) {this.imgurl = imgurl; }


}
