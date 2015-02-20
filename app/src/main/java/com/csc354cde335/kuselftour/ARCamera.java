package com.csc354cde335.kuselftour;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * This is a preliminary camera activity created just to
 * demonstrate application features and visualize flow.
 */
public class ARCamera extends Activity {

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcamera);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arcamera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    // IDE defined methods above, Developer methods below

    // All below is just a camera test and not be kept-- DELETE BELOW

    private Camera mCamera;
    private CameraView mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCamera = Camera.open();
        mView = new CameraView(this);

        setContentView(mView);
    }

    // extending SurfaceView to render the camera images
    private class CameraView extends SurfaceView implements SurfaceHolder.Callback{
        private SurfaceHolder mHolder;

        public CameraView(Context context) {
            super(context);

            mHolder = this.getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

            setFocusable(true);

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {

            try {
                mCamera.setPreviewDisplay(mHolder);
            } catch (IOException e) {
                mCamera.release();
            }
            mCamera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

            mCamera.stopPreview();
            mCamera.release();

        }

    }

}
