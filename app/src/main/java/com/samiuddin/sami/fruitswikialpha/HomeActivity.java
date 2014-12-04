// this is the main activity that appears after splash screen appears
// this activity includes a camera and a surface view

package com.samiuddin.sami.fruitswikialpha;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;


public class HomeActivity extends Activity implements CvCameraViewListener2{

    private static final String TAG = "FruitsWikiV3::MainActivity";
    private CameraBridgeViewBase mOpenCvCameraView;
    private Mat mRgba, mIntermediateMat, mGray;
    private Mat mRgbaT, mGrayT;
    TextView tView;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");

                    System.loadLibrary("FeatLib");

                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this, mLoaderCallback);
    }

    public HomeActivity() {
        Log.i(TAG, "Instantiated new HomeActivity for FruitsWiki");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_home);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.CameraView);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        tView = (TextView)findViewById(R.id.tView);
        //tView.setText(FindFeatures(mRgba.getNativeObjAddr(),mGray.getNativeObjAddr()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mIntermediateMat = new Mat(height, width, CvType.CV_8UC4);
        mGray = new Mat(height, width, CvType.CV_8UC1);
    }

    public void onCameraViewStopped() {
        mRgba.release();
        mGray.release();
        mIntermediateMat.release();
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        //THIS MODIFIED CODE CAUSES MEMORY BUFFER OVERFLOW
        // TO SOLVE IT - FIND A WAY TO CLEAR MEMORY (delete old video frames)
        //all the lines with mRgbaT and mGrayT are to be deleted to get the original code
        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        //mRgbaT = mRgba.t();
        //mGrayT = mGray.t();

        //Core.flip(mRgba.t(), mRgbaT, 1);
        //Core.flip(mGray.t(), mGrayT, 1);

        //FindFeatures(mGrayT.getNativeObjAddr(), mRgbaT.getNativeObjAddr());
        FindFeatures(mGray.getNativeObjAddr(), mRgba.getNativeObjAddr());
        //Imgproc.resize(mRgbaT, mRgbaT, mRgba.size());
        //Imgproc.resize(mGrayT, mGrayT, mGray.size());
        //return mRgbaT;
        return mRgba;
    }

    public native void FindFeatures(long matAddrGr, long matAddrRgba);
}
