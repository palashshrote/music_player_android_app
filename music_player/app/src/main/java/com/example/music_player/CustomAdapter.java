package com.example.music_player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {
    private String[] arr;
    private TextView textView;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull String[] arr) {
        super(context, resource, arr);
        this.arr = arr;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return arr[position];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_custom_lv, parent, false);
        textView = convertView.findViewById(R.id.textView);
        textView.setText(getItem(position));

        return convertView;
    }
}
