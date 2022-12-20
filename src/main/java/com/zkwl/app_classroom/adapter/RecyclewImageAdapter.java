package com.zkwl.app_classroom.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_classroom.databinding.ItemImgBinding;

import java.util.List;

public class RecyclewImageAdapter extends BaseRecyclerAdapter<String, BindingHolder<ItemImgBinding>> {


    public RecyclewImageAdapter(Context context, List<String> imglist, int layoutId) {
        super(context, imglist, layoutId);
    }


    @Override
    public void bindViewDatas(BindingHolder<ItemImgBinding> holder, String item, int position) {
        Log.e("",""+item);
        Glide.with(getContext()).asBitmap().load(
                item)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int width = resource.getWidth();//图片原始宽度
                        int height = resource.getHeight();//图片原始高度
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
                        holder.mBinding.goodsImage.setLayoutParams(lp);
                        holder.mBinding.goodsImage.setImageBitmap(resource);
                    }
                });
        
    }

}