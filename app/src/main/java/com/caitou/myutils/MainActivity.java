package com.caitou.myutils;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends BaseActivity {

    String[] permissions = new String[] {Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.request_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestRuntimePermission(permissions, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "用户已全部授权", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(List<String> deniedPermissions) {
                        if (deniedPermissions.isEmpty())
                            return;
                        for (String deniedPermission : deniedPermissions) {
                            Toast.makeText(MainActivity.this, "用户拒绝 " + deniedPermission, Toast.LENGTH_SHORT).show();
//                            if (deniedPermission.equals(Manifest.permission.CALL_PHONE)) {
//                                Toast.makeText(MainActivity.this, "必须要电话权限", Toast.LENGTH_SHORT).show();
//                            }
                        }
                    }
                });
            }
        });
    }
}
