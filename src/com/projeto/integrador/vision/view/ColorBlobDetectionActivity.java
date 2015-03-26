package com.projeto.integrador.vision.view;

import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.imgproc.Imgproc;

import com.projeto.integrador.vision.R;
import com.projeto.integrador.vision.view.impl.ColorBlobDetector;
import com.projeto.integrador.vision.view.impl.ColorDetectionWithVoice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

@SuppressLint("ClickableViewAccessibility")
public class ColorBlobDetectionActivity extends Activity implements OnTouchListener, CvCameraViewListener2 {
    private static final String  TAG = "OCVSample::Activity";

    private boolean mIsColorSelected = false;
    private Mat mRgba;
    private Scalar mBlobColorRgba;
    private Scalar mBlobColorHsv;
    private ColorBlobDetector mDetector;
    private Mat mSpectrum;
    private Size SPECTRUM_SIZE;
    private Scalar CONTOUR_COLOR;
    private MediaPlayer mpBlack;
    private MediaPlayer mpWhite;
    private MediaPlayer mpYellow;
    private MediaPlayer mpBlue;
    private MediaPlayer mpRed;
    private MediaPlayer mpGreen;
    private ColorDetectionWithVoice colorWithVoice;
    private CameraBridgeViewBase mOpenCvCameraView;

    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @SuppressLint("ClickableViewAccessibility")
		@Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.setOnTouchListener(ColorBlobDetectionActivity.this);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public ColorBlobDetectionActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_color_detection);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.color_blob_detection_activity_surface_view);
        mOpenCvCameraView.setCvCameraViewListener(this);
        initializerSoundColorsDetection();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mDetector = new ColorBlobDetector();
        mSpectrum = new Mat();
        mBlobColorRgba = new Scalar(255);
        mBlobColorHsv = new Scalar(255);
        SPECTRUM_SIZE = new Size(200, 64);
        CONTOUR_COLOR = new Scalar(255,0,0,255);
    }

    public void onCameraViewStopped() {
        mRgba.release();
    }

    public boolean onTouch(View v, MotionEvent event) {
        int cols = mRgba.cols();
        int rows = mRgba.rows();

        int xOffset = (mOpenCvCameraView.getWidth() - cols) / 2;
        int yOffset = (mOpenCvCameraView.getHeight() - rows) / 2;

        int x = (int)event.getX() - xOffset;
        int y = (int)event.getY() - yOffset;

        Log.i(TAG, "Touch image coordinates: (" + x + ", " + y + ")");

        if ((x < 0) || (y < 0) || (x > cols) || (y > rows)) return false;

        Rect touchedRect = new Rect();

        touchedRect.x = (x>4) ? x-4 : 0;
        touchedRect.y = (y>4) ? y-4 : 0;

        touchedRect.width = (x+4 < cols) ? x + 4 - touchedRect.x : cols - touchedRect.x;
        touchedRect.height = (y+4 < rows) ? y + 4 - touchedRect.y : rows - touchedRect.y;

        Mat touchedRegionRgba = mRgba.submat(touchedRect);

        Mat touchedRegionHsv = new Mat();
        Imgproc.cvtColor(touchedRegionRgba, touchedRegionHsv, Imgproc.COLOR_RGB2HSV_FULL);

        // Calculate average color of touched region
        mBlobColorHsv = Core.sumElems(touchedRegionHsv);
        int pointCount = touchedRect.width*touchedRect.height;
        for (int i = 0; i < mBlobColorHsv.val.length; i++)
            mBlobColorHsv.val[i] /= pointCount;

        mBlobColorRgba = converScalarHsv2Rgba(mBlobColorHsv);

        /*******************************************************************************************
         * 
         * 
         * No trecho de código abaixo está o RGB da cor selecionada.
         * 
         * 
         * ****************************************************************************************/
        
        Log.i(TAG, "Touched rgba color: (" + mBlobColorRgba.val[0] + ", " + mBlobColorRgba.val[1] +
                ", " + mBlobColorRgba.val[2] + ", " + mBlobColorRgba.val[3] + ")");
        eventColorsVoiceDetection();   
        mDetector.setHsvColor(mBlobColorHsv);
        
        Log.i(TAG,"Color selecionada " + mBlobColorHsv);
        

        Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);

        mIsColorSelected = true;

        touchedRegionRgba.release();
        touchedRegionHsv.release();

        return false; // don't need subsequent touch events
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();

        if (mIsColorSelected) {
            mDetector.process(mRgba);
            List<MatOfPoint> contours = mDetector.getContours();
            Log.e(TAG, "Contours count: " + contours.size());
            Imgproc.drawContours(mRgba, contours, -1, CONTOUR_COLOR);

            Mat colorLabel = mRgba.submat(4, 68, 4, 68);
            colorLabel.setTo(mBlobColorRgba);

            Mat spectrumLabel = mRgba.submat(4, 4 + mSpectrum.rows(), 70, 70 + mSpectrum.cols());
            mSpectrum.copyTo(spectrumLabel);
        }

        return mRgba;
    }

    private Scalar converScalarHsv2Rgba(Scalar hsvColor) {
        Mat pointMatRgba = new Mat();
        Mat pointMatHsv = new Mat(1, 1, CvType.CV_8UC3, hsvColor);
        Imgproc.cvtColor(pointMatHsv, pointMatRgba, Imgproc.COLOR_HSV2RGB_FULL, 4);

        return new Scalar(pointMatRgba.get(0, 0));
    }
    
    private void eventColorsVoiceDetection(){
        //Blue
        if(mBlobColorRgba.val[2] > mBlobColorRgba.val[0] && mBlobColorRgba.val[2] > mBlobColorRgba.val[1]){
        	 colorBlue();
        }
        
        //Green
        if(mBlobColorRgba.val[1] > mBlobColorRgba.val[0] && mBlobColorRgba.val[1] > mBlobColorRgba.val[2]){
       	 	coloGreen();
        }
        
        //Red
        if(mBlobColorRgba.val[0] > mBlobColorRgba.val[1] && mBlobColorRgba.val[0] > mBlobColorRgba.val[2]){
        	 colorRed();
        } 	
    }
    
    private void colorRed(){
    	if(mBlobColorRgba.val[0] >= 120 && mBlobColorRgba.val[1] >= 120 && mBlobColorRgba.val[2] <=50){
         	mpYellow.start();
         	return;
         }
    	 if(mBlobColorRgba.val[0] <= 60 && mBlobColorRgba.val[1] <= 60 && mBlobColorRgba.val[2] <= 60 ){
          	mpBlack.start();
          	return;
         }
    	 if(mBlobColorRgba.val[0] >= 170 && mBlobColorRgba.val[1] >= 190 && mBlobColorRgba.val[2] >= 190 ){
         	mpWhite.start();
         	return;
         }
    	mpRed.start();
    }
    
    private void coloGreen(){
    	if(mBlobColorRgba.val[0] <= 60 && mBlobColorRgba.val[1] <= 60 && mBlobColorRgba.val[2] <= 60 ){
        	mpBlack.start();
        	return;
        }
    	if(mBlobColorRgba.val[0] >= 190 && mBlobColorRgba.val[1] >= 170 && mBlobColorRgba.val[2] >= 190 ){
        	mpWhite.start();
        	return;
        }
   	 	mpGreen.start();
    }
    
    private void colorBlue(){
    	if(mBlobColorRgba.val[0] <= 60 && mBlobColorRgba.val[1] <= 60 && mBlobColorRgba.val[2] <= 60 ){
         	mpBlack.start();
         	return;
         }
    	 if(mBlobColorRgba.val[0] >= 190 && mBlobColorRgba.val[1] >= 190 && mBlobColorRgba.val[2] >= 170 ){
         	mpWhite.start();
         	return;
         }
    	mpBlue.start();
    }
    
    private void initializerSoundColorsDetection(){
    	mpBlack = MediaPlayer.create(ColorBlobDetectionActivity.this, R.raw.color_black);
        mpWhite = MediaPlayer.create(getApplicationContext(), R.raw.color_white);
        mpYellow = MediaPlayer.create(getApplicationContext(), R.raw.color_yellow);
        mpBlue = MediaPlayer.create(getApplicationContext(), R.raw.color_blue);
        mpRed = MediaPlayer.create(getApplicationContext(), R.raw.color_red);
        mpGreen = MediaPlayer.create(getApplicationContext(), R.raw.color_green);
    }
}
