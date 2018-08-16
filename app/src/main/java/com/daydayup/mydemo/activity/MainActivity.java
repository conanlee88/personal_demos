package com.daydayup.mydemo.activity;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.databinding.ActivityMainBinding;
import com.daydayup.mydemo.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    private void initView() {
        mBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
    }

    private void initEvent() {
        mBinding.cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionItem permissionItem = new PermissionItem(Manifest.permission.CAMERA,
                        "相机",R.drawable.permission_ic_camera);
                PermissionItem permissionItem2 = new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        "相机",R.drawable.permission_ic_camera);
                List<PermissionItem> permissionItems = new ArrayList<>();
                permissionItems.add(permissionItem);
                permissionItems.add(permissionItem2);
                //权限申请
                HiPermission.create(MainActivity.this)
                        .animStyle(R.style.PermissionDefaultBlueStyle)
                        .permissions(permissionItems)
                        .checkMutiPermission(new PermissionCallback() {
                            @Override
                            public void onClose() {
                                Log.v("conan","onClose");
                            }

                            @Override
                            public void onFinish() {
                                //所有权限都申请成功，才回调的方法
                                Log.v("conan","onFinish");
                                Intent intent = new Intent(MainActivity.this,MyCameraViewActivity.class);
                                startActivityForResult(intent,0);
                            }

                            @Override
                            public void onDeny(String permission, int position) {
                                Log.v("conan","onDeny");
                                ToastUtils.makeToast("请打开相机权限，否则无法拍照");
                            }

                            @Override
                            public void onGuarantee(String permission, int position) {
                                //几个权限申请成功就会回调几次
                                Log.v("conan","onGuarantee");
                            }
                        });
            }
        });

        mBinding.audioRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AudioRecorderActivity.class);
                startActivity(intent);
            }
        });

        mBinding.viewEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewEffectActivity.class);
                startActivity(intent);
            }
        });

        mBinding.test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
//            Bitmap bitmap = data.getParcelableExtra("img_data");
//            mBinding.photoTookFromPreActivity.setImageBitmap(bitmap);
        }
    }
}
