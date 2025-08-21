package com.s23010167.tailorease;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    // Request codes for permissions and camera result
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;

    private Uri photoUri;     // URI of the captured image
    private File photoFile;   // File where the image is stored
    private ImageView imageView; // Image preview
    private Button sendBtn;   // Send to WhatsApp button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Initialize views
        imageView = findViewById(R.id.imageViewPreview);
        sendBtn = findViewById(R.id.btnSendWhatsapp);

        // Request permission or start camera immediately
        if (checkCameraPermission()) {
            dispatchTakePictureIntent();
        } else {
            requestCameraPermission();
        }

        // WhatsApp send button action
        sendBtn.setOnClickListener(v -> {
            if (photoUri != null) {
                String phoneNumber = "+94740224207"; // shop’s WhatsApp number (not used directly here)
                String message = "Hi 👋, this is my style inspiration 😊. Please have a look and feel free to contact me for more details or customizations. Thank you! 🙏";

                // Create send intent
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("image/*");
                sendIntent.putExtra(Intent.EXTRA_STREAM, photoUri); // attach the captured photo
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);    // attach the message
                sendIntent.setPackage("com.whatsapp"); // ensure it opens in WhatsApp only


                try {
                    // Show system-style message before opening WhatsApp
                    Toast.makeText(this, "Please select the TailorEase shop chat to send your image 📸", Toast.LENGTH_LONG).show();

                    // Delay ensures the toast is visible before intent switch
                    sendBtn.postDelayed(() -> {
                        try {
                            startActivity(sendIntent);
                        } catch (Exception e) {
                            Toast.makeText(this, "WhatsApp not installed or failed to send", Toast.LENGTH_SHORT).show();
                        }
                    }, 2000); // 2 second delay

                } catch (Exception e) {
                    Toast.makeText(this, "Error preparing WhatsApp", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                // Create a file to save the captured image
                photoFile = createImageFile();
                if (photoFile != null) {
                    // Get content URI with FileProvider
                    photoUri = FileProvider.getUriForFile(this,
                            getPackageName() + ".fileprovider",
                            photoFile);

                    // Pass the URI to the camera intent
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                    // Launch camera
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to create image file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Camera not supported", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        // Use timestamp to avoid overwriting files
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalFilesDir("Pictures");
        return File.createTempFile("JPEG_" + timeStamp + "_", ".jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Display the captured image in ImageView
            imageView.setImageURI(photoUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If granted, open the camera
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
