package cn.star.csxsjx.view.main;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import cn.star.csxsjx.R;
import cn.star.csxsjx.base.BaseActivity;
import cn.star.csxsjx.utils.GlideUtils;
import cn.star.csxsjx.view.main.bean.GlideImageLoader;
import cn.star.csxsjx.view.main.bean.ImagesBean;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.jz_video)
    JzvdStd jzVideo;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        checkPermissions(this);
        showProgressDialog("加载中..");
    }

    private List<ImagesBean> imagesBeans = new ArrayList<>();

    @Override
    protected void initData() {
        OkGo.<String>get("http://129.204.48.22:8080/sys/Broadcast/list")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        closeProgressDialog();
                        try {
                            JSONArray imags = new JSONArray(response.body());
                            for (int i = 0; i < imags.length(); i++) {
                                Gson gson = new Gson();
                                ImagesBean imagesBean = gson.fromJson(imags.optString(i), ImagesBean.class);
                                imagesBeans.add(imagesBean);
                            }
                            if (imagesBeans.size() > 0) {
                                initImageView();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Logger.d(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        closeProgressDialog();
                    }
                });

        initVideo();
    }

    /**
     * 加载轮播图
     */
    private void initImageView() {
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imagesBeans);
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setDelayTime(4000);
        banner.start();
    }

    /**
     * 加载视频
     */
    private void initVideo() {
        jzVideo.setUp("http://27.209.180.12/sohu/v1/TmvGTmxATiY4Xkh3qv3SkmOGcwb7oCGR0SXBjWsytHrChWoIymcAr.mp4?k=H3qhzY&p=j9lvzSwUop1AqmPmomXmqSXCoSkWsUwIWFo7oB2svm12ZD6S0tvGRD6sWYesfY1svmfCZMX2gLsSotNcWhXs5G1S0MWAyJ2dypES0mEAZD6sfOAOWBAsfFo7NF2tZY1sfYo70ScAZDetwm8I9kIWr&r=TmI20LscWOoUgt8IS3G9wGY43hUxlaok63ZqiqBXLOGxUI6N6Eiy4930e9IprW49JLzSx7qKOyOHXIY&q=OpCChW7IWJodRD6tWY6SotE7ZD6sRhXORhXOfhX4WJo2ZDv4WBe4Zhvswm4cWhWsvmscWY&cip=119.39.21.52"
                , "企业简介", Jzvd.SCREEN_WINDOW_NORMAL);
        Glide.with(this)
                .applyDefaultRequestOptions(GlideUtils.getRequestOptions())
                .load("")
                .into(jzVideo.thumbImageView);

    }


    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.goOnPlayOnPause();//暂停播放
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
    }

    /**
     * 按返回键，实现按home键
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!Jzvd.backPress()) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 检测权限
     *
     * @param context
     */
    public static void checkPermissions(BaseActivity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new RxPermissions(context).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.VIBRATE,
                    Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA)
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {

                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }
}
