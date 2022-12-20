package com.zkwl.app_classroom.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.adapter.CommentAdapter;
import com.zkwl.app_classroom.databinding.ActivityVideoBinding;
import com.zkwl.app_classroom.model.VideoModel;
import com.zkwl.app_classroom.util.ClassroomImgUtil;
import com.zkwl.app_classroom.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;


@AndroidEntryPoint
public class VideoActivity extends BaseActivity<VideoModel, ActivityVideoBinding> {

    private List<JSONObject> mlist =new ArrayList<>();
    private CommentAdapter commentAdapter;
    private StandardGSYVideoPlayer videoPlayer;
    private OrientationUtils orientationUtils;
    private String parent_comment_id;
    private JSONArray courescata;
    private String kechengid;


    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        registerMessageEvent(message -> {
            Timber.d("message:%s", message);
            ToastUtils.showToast(getContext(), message);
        });
        String coure = getIntent().getStringExtra("courescata");
        courescata = JSONArray.parseArray(coure);
        kechengid =getIntent().getStringExtra("id");



        StatusBar.setStatusBar(this,true);
        getViewModel().getCommentlist(getIntent().getStringExtra("id"));
        getViewModel().getClasshourinfo(getIntent().getStringExtra("id"));
        getViewModel().getadlistLiveData().observe(this,data ->{

            mlist.clear();
            for(int i = 0;i<data.getData().size();i++){
                mlist.add(data.getData().getJSONObject(i));
            }
            commentAdapter.notifyDataSetChanged();
        });
        getViewModel().GetliveDataClasshourinfo().observe(this,data ->{
            videoPlayer.setUp(data.getData().getString("play_url"), true, data.getData().getString("title"));
            videoPlayer.startPlayLogic();
            String title = data.getData().getString("title");
            getViewDataBinding().titleContent.setText(title);


            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ClassroomImgUtil.loadImage(imageView,data.getData().getString("image_url"),2);
            videoPlayer.setThumbImageView(imageView);
        });

         commentAdapter =new CommentAdapter(getContext(), mlist, R.layout.item_comment, new CommentAdapter.onclick() {
            @Override
            public void click(JSONObject tump) {
                //一级评论回复
                parent_comment_id =tump.getIntValue("id")+"";
                getViewDataBinding().commentMsgedit.setHint("回复"+tump.getString("nick_name")+":("+tump.getString("msg")+")");
            }

             @Override
             public void erjiclick(JSONObject tump) {
                 //二级评论回复
                 parent_comment_id =tump.getIntValue("parent_comment_id")+"";
                 getViewDataBinding().commentMsgedit.setHint("回复"+tump.getString("nick_name")+":("+tump.getString("msg")+")");
             }

             @Override
             public void dianzan(JSONObject tump) {
                getViewModel().add_like(tump.getIntValue("id")+"");
             }
         });
        getViewDataBinding().recyclerView.setAdapter(commentAdapter);



        videoPlayer =findViewById(R.id.video_player);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPlayer.startWindowFullscreen(getContext(), false, true);
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        videoPlayer.setGSYVideoProgressListener(new GSYVideoProgressListener() {
            @Override
            public void onProgress(long progress, long secProgress, long currentPosition, long duration) {
                Log.e("onProgress","progress"+progress+"secProgress"+secProgress+"currentPosition"+currentPosition+"duration"+duration);
                if (progress==99){
//                    ToastUtils.showToast(getContext(),"10s后即将播放下一节");
//                    for (int i=0;i<courescata.size();i++){
//                        if (kechengid.equals(courescata.getJSONObject(i).getString("id"))){
//                            Log.e("kechengid",kechengid+""+courescata.getJSONObject(i).getString("id"));
//                            kechengid=courescata.getJSONObject(i+1).getString("id");
//                            getViewModel().getClasshourinfo(kechengid);
//                        }
//                    }
                }
            }
        });



        getViewDataBinding().commitMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(getViewDataBinding().commentMsgedit.getText().toString())){
                    ToastUtils.showToast(getContext(),"请输入内容");
                    return;
                }
                if (parent_comment_id!=null && !"".equals(parent_comment_id)){
                    getViewModel().reply_comment(parent_comment_id,getViewDataBinding().commentMsgedit.getText().toString());
                    return;
                }
                getViewModel().add_comment(getIntent().getStringExtra("id"),getViewDataBinding().commentMsgedit.getText().toString());
            }
        });

        getViewModel().getliveDataadd_comment().observe(this,data ->{
            getViewDataBinding().commentMsgedit.setText("");
            getViewModel().getCommentlist(getIntent().getStringExtra("id"));
        });
        getViewModel().getliveDataadd_like().observe(this,data ->{
            getViewModel().getCommentlist(getIntent().getStringExtra("id"));
        });
        getViewModel().getliveDatareply_comment().observe(this,data ->{
            parent_comment_id="";
            getViewDataBinding().commentMsgedit.setHint("请输入回复内容");
            getViewDataBinding().commentMsgedit.setText("");
            getViewModel().getCommentlist(getIntent().getStringExtra("id"));
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            Log.e("onBackPressed","performClick");
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        finish();
    }
}
