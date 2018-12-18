package cn.star.csxsjx.view.main.bean;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import java.util.List;
import java.util.logging.Logger;

/**
 * 图片加载器
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        List<ImagesBean> imagesBeans = (List<ImagesBean>) path;

        for (ImagesBean imaghe : imagesBeans) {
           // com.orhanobut.logger.Logger.e("afaf",imaghe.getUrl());
            Glide.with(context).load(imaghe.getUrl()).into(imageView);
        }
    }
}
