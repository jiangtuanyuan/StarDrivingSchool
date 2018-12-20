package cn.star.csxsjx.view.main.bean;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import java.util.List;
import java.util.logging.Logger;

import cn.star.csxsjx.utils.GlideUtils;

/**
 * 图片加载器
 */
public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片
        Glide.with(context)
                .applyDefaultRequestOptions(GlideUtils.getRequestOptions())
                .load(((ImagesBean) path).getUrl())
                .into(imageView);

    }
}
