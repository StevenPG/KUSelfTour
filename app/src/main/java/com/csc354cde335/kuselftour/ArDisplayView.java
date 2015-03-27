package com.csc354cde335.kuselftour;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Steven on 2/28/2015.
 * This class is used to define a custom view control for use
 * within the FrameLayout contained within the ARCamera xml file.
 * Here we use a SurfaceView class for this purpose, primarily for
 * encapsulation of camera drawing.
 */
@SuppressWarnings("deprecation")
public class ArDisplayView extends SurfaceView implements SurfaceHolder.Callback{

    /**
     * Debug tag for Log.e messages
     */
    public static final String DEBUG_TAG = "Debug";

    /**
     * Deprecated camera object, necessary for backwards compatibility
     */
    public Camera mCamera;
    static public float horizontalFOV;
    static public float verticalFOV;

    /**
     * This field holds reference to the container that contains the SurfaceView
     */
    public SurfaceHolder mHolder;

    /**
     * The activated activity for the augmented reality view
     */
    public Activity mActivity;

    /**
     * Standard required constructor
     * @param context - activity context
     *
     */
    public ArDisplayView(Context context, Activity activity) {
        super(context);

        mActivity = activity;
        mHolder = getHolder();
        /** although the setType() method is deprecated and the Android SDK
         * documentation says it is no longer necessary, we have found that the
         * Camera code will not function without it, it simply fails to
         * start up, even on the newest devices.
         */
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);
    }

    /**
     * Here we request the Camera, set the camera’s display orientation
     * based on the current orientation of the device (so that the camera
     * preview always displays right-side up), and call the setPreviewDisplay()
     * method to tie the Camera preview to our SurfaceHolder.
     * @param holder - the surface that the camera is being shown on
     */
    public void surfaceCreated(SurfaceHolder holder){
        // Activate Camera hardware
        mCamera = Camera.open();

        // Retrieve camera information for use
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);

        // Get camera parameters for later
        Camera.Parameters params = mCamera.getParameters();
        horizontalFOV = params.getHorizontalViewAngle();
        verticalFOV = params.getVerticalViewAngle();

        // Compensate for camera rotation
        int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }
        mCamera.setDisplayOrientation((info.orientation - degrees + 360) % 360);

        try {
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            Log.e(DEBUG_TAG, "surfaceCreated exception: ", e);
        }
    }

    /**
     * Here we request the camera parameters and check what the
     * supported preview sizes are. We need to find a preview
     * size that can be accommodated by the surface, so we look
     * for the closest match and use it as our preview size.
     * Next, we set the camera preview format, commit the camera
     * parameter changes, and finally call the startPreview()
     * method to begin displaying the camera preview within our surface.
     * @param holder - screen
     * @param format - screen format
     * @param width - screen width
     * @param height - screen height
     */
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        // Request parameters
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> prevSizes = params.getSupportedPreviewSizes();
        for (Camera.Size s : prevSizes)
        {
            if((s.height <= height) && (s.width <= width))
            {
                params.setPreviewSize(s.width, s.height);
                break;
            }
        }

        // Set parameters and begin visual preview
        mCamera.setParameters(params);
        mCamera.startPreview();
    }

    /**
     * Here we shut down the camera preview and release the Camera resources.
     * @param holder - holds the screen object
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
    }
}
