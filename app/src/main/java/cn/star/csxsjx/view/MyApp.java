package cn.star.csxsjx.view;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpParams;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.star.csxsjx.base.NetInterceptor;
import okhttp3.OkHttpClient;

/**
 * 程序入口
 */
public class MyApp extends Application {
    public static Context context;

    public static Context getContext() {
        return context;
    }

    private static Handler mMainThreadHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //主线程的Handler
        mMainThreadHandler = new Handler();
        initOkgo();
        initLogger();
    }

    /**
     * 初始化OkGo
     */
    private void initOkgo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        builder.addNetworkInterceptor(new NetInterceptor("网络拦截：", true));
        //全局的读取超时时间
        builder.readTimeout(5000, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(5000, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(5000, TimeUnit.MILLISECONDS);
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));

        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                             //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }

    /**
     * 初始化日志打印库
     */
    private void initLogger() {
        //Logger 初始化
        Logger.addLogAdapter(new AndroidLogAdapter());

        /*项目上线 执行该注释方法 取消打印日志 防止ADB抓取日志
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });*/
    }

    /**
     * 得到主线程里面的创建的一个hanlder
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }
}
