package de.work.camera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

public class MainActivity extends Activity{

	private Uri fileUri;

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
	
		// Bildaufnahme
		// create Intent to take a picture and return control to the calling application
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	    fileUri = MediaSaver.getOutputMediaFileUri(MediaSaver.MEDIA_TYPE_IMAGE); // create a file to save the image
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

	    // start the image capture Intent
	    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	    
	    // Videoaufnahme
//	  	create new Intent
//	    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//
//	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);  // create a file to save the video
//	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name
//
//	    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
//
//	    // start the Video Capture Intent
//	    startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
	            Toast.makeText(this, "Image saved to:\n" +
	                     fileUri, Toast.LENGTH_LONG).show();
	            
	            // Hier könnte der Imagepath gepspeichert werden(fileUri)
	            
	        } else if (resultCode == RESULT_CANCELED) {
	            Toast.makeText(this, "User canceled", Toast.LENGTH_LONG).show();
	        } else {
	            Toast.makeText(this, "Error while taking picture", Toast.LENGTH_LONG).show();
	        }
	    }

	    if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Video captured and saved to fileUri specified in the Intent
	            Toast.makeText(this, "Video saved to:\n" +
	                     data.getData(), Toast.LENGTH_LONG).show();
	        } else if (resultCode == RESULT_CANCELED) {
	            Toast.makeText(this, "User canceled", Toast.LENGTH_LONG).show();
	        } else {
	        	  Toast.makeText(this, "Error while taking video", Toast.LENGTH_LONG).show();
	        }
	    }
	}
	
	

}
