package cn.star.csxsjx.utils;

import android.view.Gravity;
import android.widget.Toast;

import cn.star.csxsjx.view.MyApp;


/**
 * Toast 工具类
 */
public class ToastUtil {
    private static Toast toast;

    public static void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.context, msg, Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public static void showToast(int resId) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.context, resId, Toast.LENGTH_SHORT);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setText(resId);
        toast.show();
    }
    public static void cancleToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
