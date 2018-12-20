package cn.star.csxsjx.utils;

import com.bumptech.glide.request.RequestOptions;

import cn.star.csxsjx.R;

public class GlideUtils {

    /**
     * 轮播图的RequestOptions
     *
     * @return
     */
    public static RequestOptions requestOptions = null;

    public static RequestOptions getRequestOptions() {
        if (requestOptions == null) {
            requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.load_error)
                    .error(R.mipmap.load_error)
                    .fallback(R.mipmap.load_error);
        }
        return requestOptions;
    }
}
