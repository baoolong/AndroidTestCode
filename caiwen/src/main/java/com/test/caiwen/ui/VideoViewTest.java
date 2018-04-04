package com.test.caiwen.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.test.caiwen.R;

/**
 * @author MrRight Created by MrRight on 2018/3/19.
 */

public class VideoViewTest extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoviewtest);
        initView();
    }

    /**
     * 香港卫视：http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8
     * CCTV1高清：http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8
     * CCTV3高清：http://ivi.bupt.edu.cn/hls/cctv3hd.m3u8
     * CCTV5高清：http://ivi.bupt.edu.cn/hls/cctv5hd.m3u8
     * CCTV5+高清：http://ivi.bupt.edu.cn/hls/cctv5phd.m3u8
     * CCTV6高清：http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8
     * 苹果提供的测试源（点播）：http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear2/prog_index.m3u8
     */
    private void initView() {
        VideoView videoView=findViewById(R.id.mVideoView);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(100,100);
        videoView.setLayoutParams(params);
        String url="http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8";

        videoView.setVideoPath(url);
        videoView.requestFocus();
        videoView.start();

    }
}
