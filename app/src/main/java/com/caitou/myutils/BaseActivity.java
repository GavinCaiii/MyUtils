package com.caitou.myutils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.caitou.myutils.utils.ActivityManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: BaseActivity
 * @classDescription: Activity 基类，封装了6.0以上的运行时权限
 * @Author: Guangzhao Cai
 * @createTime: 2017-01-06.
 */

public class BaseActivity extends AppCompatActivity {

    // 权限结果回调
    private static PermissionListener permissionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
    }

    /**
     * 申请运行时权限
     * @param permissions - 权限集合
     * @param listener - 申请权限结果回调
     */
    public static void requestRuntimePermission(String[] permissions, PermissionListener listener) {
        if (permissions == null || permissions.length <= 0)
            return;
        Activity currentActivity = ActivityManager.getInstance().currentActivity();
        if (currentActivity == null)
            return;
        permissionListener = listener;
        List<String> permissionList = new ArrayList<>();
        try {
            // 检查权限
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(currentActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission);
                }
            }
            // 请求权限
            if (!permissionList.isEmpty()) {
                ActivityCompat.requestPermissions(currentActivity, permissionList.toArray(new String[permissionList.size()]), 1);
            } else {
                // 已经获取权限
                permissionListener.onGranted();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 请求权限的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0) {
                List<String> deniedPermissions = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i ++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        deniedPermissions.add(permissions[i]);
                    }
                }

                // 用户已授权权限
                if (deniedPermissions.isEmpty()) {
                    permissionListener.onGranted();
                } else {
                    permissionListener.onDenied(deniedPermissions);
                }
            }
        }

    }
}
