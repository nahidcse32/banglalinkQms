package com.sharnit.banglalinkqms.Activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.sharnit.banglalinkqms.Adapter.DisplayAdapter;
import com.sharnit.banglalinkqms.Model.Display;
import com.sharnit.banglalinkqms.R;
import com.sharnit.banglalinkqms.Utils.Url;

import java.util.ArrayList;


public class DisplayActivity extends AppCompatActivity {

    Uri vidUri;
    MediaPlayer.OnPreparedListener PreparedListener = new MediaPlayer.OnPreparedListener() {

        @Override
        public void onPrepared(MediaPlayer m) {
            m.setVolume(0f, 0f);
            m.setLooping(true);
        }
    };
    private VideoView vidView;
    private ListView listView;
    private DisplayAdapter displayAdapter;
    private ArrayList<Display> displayArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        displayArrayList = new ArrayList<>();
        vidView = (VideoView) findViewById(R.id.videoview);
        listView = (ListView) findViewById(R.id.lv_token);

        vidUri = Uri.parse(Url.VIDEO_URL);
        vidView.setVideoURI(vidUri);
        vidView.start();
        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        //vidView.setMediaController(vidControl);
        vidView.setOnPreparedListener(PreparedListener);
        try {
            displayArrayList = getIntent().getParcelableArrayListExtra("dis_data");
            if (displayArrayList.size() > 0) {
                displayAdapter = new DisplayAdapter(getApplicationContext(), displayArrayList);
                listView.setAdapter(displayAdapter);
            }
            //Toast.makeText(getApplicationContext(), type, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("ex", "Exception: " + e.getMessage());
        }

    }

}