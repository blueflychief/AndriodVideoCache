package com.danikula.videocache.sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;

import java.io.File;

public class GalleryVideoFragment extends Fragment implements CacheListener {

    String url;

    int position;
    boolean playerStarted;

    VideoView videoView;
    ProgressBar progressBar;

    private boolean visibleForUser;

    private final VideoProgressUpdater updater = new VideoProgressUpdater();

    public static GalleryVideoFragment newInstance(String url) {
        GalleryVideoFragment fragment = new GalleryVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressBar);
        videoView = view.findViewById(R.id.videoView);
        if (getArguments() != null) {
            url = getArguments().getString("url");
        }
        startProxy();

        if (visibleForUser) {
            startPlayer();
        }
    }

    private void startPlayer() {
        videoView.seekTo(position);
        videoView.start();
        playerStarted = true;
    }

    private void startProxy() {
        HttpProxyCacheServer proxy = App.getProxy(getActivity());
        proxy.registerCacheListener(this, url);
        videoView.setVideoPath(proxy.getProxyUrl(url));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        visibleForUser = isVisibleToUser;
        if (videoView != null) {
            if (visibleForUser) {
                startPlayer();
            } else if (playerStarted) {
                position = videoView.getCurrentPosition();
                videoView.pause();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updater.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        updater.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        videoView.stopPlayback();
        App.getProxy(getActivity()).unregisterCacheListener(this);
    }

    @Override
    public void onCacheAvailable(File file, String url, int percentsAvailable) {
        progressBar.setSecondaryProgress(percentsAvailable);
    }

    private void updateVideoProgress() {
        int videoProgress = videoView.getCurrentPosition() * 100 / videoView.getDuration();
        progressBar.setProgress(videoProgress);
    }

//    @SeekBarTouchStop(R.id.progressBar)
//    void seekVideo() {
//        int videoPosition = videoView.getDuration() * progressBar.getProgress() / 100;
//        videoView.seekTo(videoPosition);
//    }

    private final class VideoProgressUpdater extends Handler {

        public void start() {
            sendEmptyMessage(0);
        }

        public void stop() {
            removeMessages(0);
        }

        @Override
        public void handleMessage(Message msg) {
            updateVideoProgress();
            sendEmptyMessageDelayed(0, 500);
        }
    }
}
