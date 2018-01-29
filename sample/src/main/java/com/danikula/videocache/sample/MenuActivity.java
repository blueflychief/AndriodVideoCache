package com.danikula.videocache.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MenuActivity extends FragmentActivity {
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        listView = findViewById(R.id.listView);
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, buildListData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListEntry item = (ListEntry) listView.getAdapter().getItem(position);
                startActivity(new Intent(MenuActivity.this, item.activityClass));
            }
        });

        findViewById(R.id.cleanCacheButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Utils.cleanVideoCacheDir(MenuActivity.this);
                } catch (IOException e) {
                    Log.e(null, "Error cleaning cache", e);
                    Toast.makeText(MenuActivity.this, "Error cleaning cache", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @NonNull
    private List<ListEntry> buildListData() {
        return Arrays.asList(
                new ListEntry("Single Video", SingleVideoActivity.class),
                new ListEntry("Multiple Videos", MultipleVideosActivity.class),
                new ListEntry("Video Gallery with pre-caching", VideoGalleryActivity.class),
                new ListEntry("Shared Cache", SharedCacheActivity.class)
        );
    }


    private static final class ListEntry {

        private final String title;
        private final Class activityClass;

        public ListEntry(String title, Class activityClass) {
            this.title = title;
            this.activityClass = activityClass;
        }

        @Override
        public String toString() {
            return title;
        }
    }

}
