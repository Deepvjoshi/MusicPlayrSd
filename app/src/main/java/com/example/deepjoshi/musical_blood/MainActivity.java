package com.example.deepjoshi.musical_blood;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
String[] items;
    Boolean play=false;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv =(ListView)findViewById(R.id.lv1);
        final ArrayList<File> mysong= findSongs(Environment.getExternalStorageDirectory());
       items= new String[ mysong.size()];

        for (int i =0;i<mysong.size();i++){
           // tosts(mysong.get(i).getName().toString());
            items[i]=mysong.get(i).getName().toString();
        }
        ArrayAdapter<String> adp =new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,items);
    lv.setAdapter(adp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri u = Uri.parse(String.valueOf(mysong.get(position).toURI()));
                if(mp!=null)
                {
                    mp.stop();
                    mp.release();
                    mp=null;
                }
                mp = MediaPlayer.create(getApplicationContext(), u);
                mp.start();

            }});
        ImageView imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.release();
                MainActivity.this.finish();

            }
        });

    }

    public ArrayList<File>findSongs(File root){
        ArrayList<File> al =new ArrayList<File>();
        File[] files =root.listFiles();
        for (File singleFile : files){
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                al.addAll(findSongs(singleFile));
            }
            else {
                if (singleFile.getName().endsWith(".mp3")){
                    al.add(singleFile);
                }

            }
        }
        return al;
    }
    public void tosts(String text){

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }
}
