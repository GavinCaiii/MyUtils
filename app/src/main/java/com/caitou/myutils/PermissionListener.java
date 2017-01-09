package com.caitou.myutils;

import java.util.List;

/**
 * @className: PermissionListener
 * @classDescription: 申请权限结果回调
 * @Author: Guangzhao Cai
 * @createTime: 2017-01-06.
 */

public interface PermissionListener {
    void onGranted();
    void onDenied(List<String> deniedPermissions);
}
