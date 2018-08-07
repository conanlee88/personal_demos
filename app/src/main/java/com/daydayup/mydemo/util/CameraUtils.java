package com.daydayup.mydemo.util;

import android.hardware.Camera;

import java.util.List;

/**
 *
 * Created by conan on 2018/7/17.
 */

public class CameraUtils {

    /**
     * 判断相机是否具有前/后置摄像头
     * @param facing 摄像头朝向
     * @return 是否有该朝向的摄像头
     */
    public static boolean hasFacingCamera(int facing){
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0;i < numberOfCameras;i ++){
            Camera.getCameraInfo(i,cameraInfo);
            if (cameraInfo.facing == facing){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断相机是否具有该聚焦模式
     * @param camera 相机对象
     * @param focusMode 聚焦模式
     * @return 是否具有该模式
     */
    public static boolean hasFocusMode(Camera camera,String focusMode){
        List<String> supportedFocusModes = camera.getParameters().getSupportedFocusModes();
        if (supportedFocusModes == null || supportedFocusModes.size() == 0)
            return false;
        for (String mode: supportedFocusModes){
            if (mode.equals(focusMode))
                return true;
        }
        return false;
    }
}
