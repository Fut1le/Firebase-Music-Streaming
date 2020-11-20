package com.kee.vlmusic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import androidx.cardview.widget.CardView;

public class ListAdapter extends BaseAdapter {
    List<String> songNames;
    List<String> thumbnails;
    List<String> songArtist;
    List<String> songDuration;
    Context context;
    public ListAdapter(Context context, List<String> songNames, List<String> thumbnails, List<String> songArtist, List<String> songDuration) {
        this.context = context;
        this.songNames = songNames;
        this.thumbnails = thumbnails;
        this.songArtist = songArtist;
        this.songDuration = songDuration;
    }

    @Override
    public int getCount() {
        return songNames.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.songs_list_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(15)
                .build();

        Picasso.get().load(thumbnails.get(i)).transform(transformation).into(viewHolder.thumbnail);
        viewHolder.songName.setText(songNames.get(i));
        viewHolder.artistName.setText(songArtist.get(i));
        viewHolder.songDuration.setText(songDuration.get(i));
        return view;
    }

    private static class ViewHolder{
       TextView songName;
        TextView artistName;
        TextView songDuration;
       ImageView thumbnail;
       CardView cardView;
       ImageView currentlyPlaying;

        ViewHolder(View view){
            songName = view.findViewById(R.id.songName);
            thumbnail = view.findViewById(R.id.songThumbnail);
            artistName = view.findViewById(R.id.artistName);
            songDuration = view.findViewById(R.id.songDuration);
            cardView = view.findViewById(R.id.cardView);
            currentlyPlaying = view.findViewById(R.id.currentlyPlaying);
        }
    }
}
