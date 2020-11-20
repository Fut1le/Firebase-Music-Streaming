package com.kee.vlmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity {
    private boolean checkPermission = false;
    ProgressDialog progressDialog;
    ListView listView;
    List<String> songsNameList;
    List<String> songsUrlList;
    List<String> songsArtistList;
    List<String> songsDurationList;
    ListAdapter adapter;
    JcPlayerView jcPlayerView;
    List<JcAudio> jcAudios;
    List<String> thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please Wait...");
        listView = findViewById(R.id.songsList);
        songsNameList = new ArrayList<>();
        songsUrlList = new ArrayList<>();
        songsArtistList = new ArrayList<>();
        songsDurationList = new ArrayList<>();
        jcAudios = new ArrayList<>();
        thumbnail = new ArrayList<>();
        jcPlayerView = findViewById(R.id.jcplayer);
        retrieveSongs();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                jcPlayerView.playAudio(jcAudios.get(i));
                jcPlayerView.setVisibility(View.VISIBLE);
                jcPlayerView.createNotification();
                adapter.notifyDataSetChanged();
            }
        });
    }

    // RETRIEVING THE SONGS FROM THE SERVER
    public void retrieveSongs() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Songs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Song song = ds.getValue(Song.class);
                    songsNameList.add(song.getSongName());
                    songsUrlList.add(song.getSongUrl());
                    songsArtistList.add(song.getSongArtist());
                    songsDurationList.add(song.getSongDuration());
                    thumbnail.add(song.getImageUrl());

                    jcAudios.add(JcAudio.createFromURL(song.getSongName(), song.getSongUrl()));
                }
                adapter = new ListAdapter(getApplicationContext(), songsNameList, thumbnail, songsArtistList, songsDurationList);
                jcPlayerView.initPlaylist(jcAudios, null);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MusicActivity.this, "FAILED!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.uploadItem){
            if (validatePermissions()){
                Intent intent = new Intent(this,UploadSongActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // METHOD TO HANDEL RUNTIME PERMISSIONS
    private boolean validatePermissions(){
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        checkPermission = true;
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        checkPermission = false;
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
        return checkPermission;

    }
}