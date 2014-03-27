package de.work.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.acl.Owner;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class OwnCameraMainActivity extends Activity{

	private Camera mCamera;
    private CameraPreview mPreview;
	private static final int MEDIA_TYPE_IMAGE = 1;
	private static final int MEDIA_TYPE_VIDEO = 2;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	
	private PictureCallback mPicture = new PictureCallback() {

	    @Override
	    public void onPictureTaken(byte[] data, Camera camera) {

	    	System.out.println("Bild aufnehmen");
	    	
	        File pictureFile = MediaSaver.getOutputMediaFile(MediaSaver.MEDIA_TYPE_IMAGE);
	        if (pictureFile == null){
	            Log.d("CameraPreview", "Error creating media file, check storage permissions.");
	            return;
	        }

	        try {
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            fos.write(data);
	            fos.close();
	            System.out.println("Bild gespeichert");
	        } catch (FileNotFoundException e) {
	            Log.d("CameraPreview", "File not found: " + e.getMessage());
	        } catch (IOException e) {
	            Log.d("CameraPreview", "Error accessing file: " + e.getMessage());
	        }
	    }
	};


	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        
        Button btnCapture = (Button)findViewById(R.id.button_capture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCamera.takePicture(null, showInfo(), mPicture);
			}
		});
    }
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mCamera.release();
	}
	
	private PictureCallback showInfo() {
        Toast.makeText(this, "Image taken", Toast.LENGTH_LONG).show();
		return null;
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}
	

	
}
