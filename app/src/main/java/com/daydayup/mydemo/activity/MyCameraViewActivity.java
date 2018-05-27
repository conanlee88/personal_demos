package com.daydayup.mydemo.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.databinding.ActivityMyCameraViewBinding;
import com.daydayup.mydemo.util.BitmapUtils;

import java.io.IOException;

/**
 * Created by 52979 on 2017/12/12.
 *
 * @des ${TODO}
 */

public class MyCameraViewActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private Camera mCamera;
    private SurfaceHolder mHolder;
    private ActivityMyCameraViewBinding mBinding;
    private boolean mIsOpenFlashlight = false;
    private byte[] mImageBuffer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉上方状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mBinding = DataBindingUtil.setContentView(MyCameraViewActivity.this,
                R.layout.activity_my_camera_view);
        mHolder = mBinding.surfaceView.getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);
        initEvent();
    }

    private void initEvent() {
        mBinding.btnOpenFlashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mCamera.stopPreview();
//                mCamera.release();
//                mCamera = Camera.open();
//                mIsOpenFlashlight = !mIsOpenFlashlight;
                Camera.Parameters parameters = mCamera.getParameters();
                if (mIsOpenFlashlight) parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                else parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
                try {
                    mCamera.setPreviewDisplay(mHolder);
                    mCamera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.release();
                finish();
            }
        });

        mBinding.btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        mImageBuffer = data;
                        BitmapFactory.Options options = new BitmapFactory.Options();

                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
                        bitmap = BitmapUtils.rotaingImageView(90,bitmap);
                        mBinding.picTook.setImageBitmap(bitmap);
                        mBinding.surfaceView.setVisibility(View.GONE);
//                        mCamera.stopPreview();
                    }
                });
            }
        });

        mBinding.btnUsePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("conan","点击了使用照片");
                try{
//                    Intent intent = new Intent();
                    //无法将该图片数据直接用setResult方法传回去，因为超过了intent传值的最大限度
//                    intent.putExtra("img_data",mImageBuffer);
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(mImageBuffer, 0, mImageBuffer.length);
//                    intent.putExtra("img_data",bitmap);
//                    Log.v("conan",mImageBuffer.toString());
//                    setResult(0,intent);
                    finish();
                }catch (Exception e){
                    Log.v("conan",e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
        Log.v("conan","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("conan","onStop");
    }

    @Override
    protected void onDestroy() {
        Log.v("conan","onDestroy");
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
        //设置相机的拍摄角度为竖向
        mCamera.setDisplayOrientation(90);
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder == null)
            return;
        mCamera.stopPreview();

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
