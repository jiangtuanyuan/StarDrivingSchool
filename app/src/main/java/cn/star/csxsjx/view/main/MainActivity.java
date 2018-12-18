package cn.star.csxsjx.view.main;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youth.banner.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.star.csxsjx.R;
import cn.star.csxsjx.base.BaseActivity;
import cn.star.csxsjx.utils.ToastUtil;
import cn.star.csxsjx.view.main.bean.GlideImageLoader;
import cn.star.csxsjx.view.main.bean.ImagesBean;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {

    @BindView(R.id.banner)
    Banner banner;

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
                        ToastUtil.showToast("OK");
                        try {
                            JSONArray imags = new JSONArray(response.body());
                            for (int i = 0; i < imags.length(); i++) {

                                imagesBeans.add(getGson().fromJson(imags.optString(0), ImagesBean.class));

                            }
                            if (imagesBeans.size() > 0) {
                                initImageView();
                                ToastUtil.showToast("又图片");
                            } else {
                                Logger.e("agfasfsafasf");
                                ToastUtil.showToast("没图片");
                            }
                        } catch (JSONException j) {
                            Logger.d(j);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        closeProgressDialog();
                    }
                });
    }

    /**
     * 加载到轮播
     */
    private void initImageView() {
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imagesBeans);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
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
