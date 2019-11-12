package com.shuiyinhuo.component.mixdev.utils.pkg.domain;

import android.app.Activity;
import android.os.Build;
import android.view.View;

/**
 * 状态栏控制
 */
public class NavaBarCutter {



    //全屏显示
    public void showFullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }


    private void hideNavigationBar(Activity activity) {
        int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            systemUiVisibility ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        // 全屏展示
        /*if (Build.VERSION.SDK_INT >= 16) {
            systemUiVisibility ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }*/

        if (Build.VERSION.SDK_INT >= 18) {
            systemUiVisibility ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
    }


}