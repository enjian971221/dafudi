package com.zkwl.app_healthy.util;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.zkwl.app_healthy.R;

public class HealthyImgUtil {

    /**
     * 加载图片
     */
    public static void loadImage(ImageView imageView, Object path,int radius) {


        if (path instanceof Integer) {
            imageView.setImageResource((Integer) path);
        } else if (path instanceof String) {
            String url = (String) path;
            Glide.with(imageView).load(url) .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius))).into(new ImageViewTarget<Drawable>(imageView) {
                @Override
                protected void setResource(@Nullable Drawable resource) {
                    imageView.setImageDrawable(resource);
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    imageView.setImageResource(R.mipmap.ic_load_fail);
                }
            });
        } else {
//            TipUtil.show("不支持的参数类型");
        }
    }
    public static void loadGif(ImageView imageView, Object path) {
        if (path instanceof Integer) {
            imageView.setImageResource((Integer) path);
        } else if (path instanceof String) {
            String url = (String) path;
            Glide.with(imageView).asGif().diskCacheStrategy(DiskCacheStrategy.RESOURCE).load(path).into(new ImageViewTarget<GifDrawable>(imageView) {
                @Override
                protected void setResource(@Nullable GifDrawable resource) {
                    imageView.setImageDrawable(resource);
                }
            });        } else {
//            TipUtil.show("不支持的参数类型");
        }
    }

    /**
     * 加载模糊图片
     */

}
