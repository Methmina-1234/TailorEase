package com.s23010167.tailorease;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

public class CameraActivity_si extends AppCompatActivity {

    // Request codes for camera capture and permission requests
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;

    // URI and File for the captured photo
    private Uri photoUri;
    private File photoFile;

    // Views for preview and sending
    private ImageView imageView;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_camera);

        // Initialize views
        imageView = findViewById(R.id.imageViewPreview);
        sendBtn = findViewById(R.id.btnSendWhatsapp);

        // Check if camera permission is granted; if yes, open camera
        if (checkCameraPermission()) {
            dispatchTakePictureIntent();
        } else {
            requestCameraPermission();
        }

        // Send button listener to share captured image via WhatsApp
        sendBtn.setOnClickListener(v -> {
            if (photoUri != null) {
                String phoneNumber = "+94740224207"; // TailorEase shop number
                String message = "හෙලෝ 👋, මේක මගේ විලාසිතාවයි 😊. කරුණාකර බලන්න සහ වැඩි විස්තර හෝ රූපය අනුව සැකසුම් සඳහා මාව අමතන්න. ස්තුතියි! 🙏";

                // Create intent to share image + text
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("image/*");
                sendIntent.putExtra(Intent.EXTRA_STREAM, photoUri); // attach image
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);    // attach message
                sendIntent.setPackage("com.whatsapp"); // open only in WhatsApp


                try {
                    // Inform user to select the correct chat in WhatsApp
                    Toast.makeText(this, "ඔබගේ රූපය යැවීමට කරුණාකර TailorEase සාප්පුවේWhatsApp Chat එක තෝරන්න \uD83D\uDCF8", Toast.LENGTH_LONG).show();

                    // Delay ensures the toast is visible before launching WhatsApp
                    sendBtn.postDelayed(() -> {
                        try {
                            startActivity(sendIntent);
                        } catch (Exception e) {
                            Toast.makeText(this, "WhatsApp ස්ථාපනය කර නැත හෝ යැවීමට අසමත් විය", Toast.LENGTH_SHORT).show();
                        }
                    }, 2000); // 2 second delay

                } catch (Exception e) {
                    Toast.makeText(this, "WhatsApp සකස් කිරීමේ දෝෂයකි", Toast.LENGTH_SHORT).show();
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
                // Create a file to store the captured image
                photoFile = createImageFile();
                if (photoFile != null) {
                    // Get URI for FileProvider
                    photoUri = FileProvider.getUriForFile(this,
                            getPackageName() + ".fileprovider", photoFile);

                    // Pass URI to camera intent
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                    // Launch camera
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "රූප ගොනුව තැනීම අසාර්ථක විය", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "කැමරා සහය නොදක්වයි", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalFilesDir("Pictures"); // private folder
        return File.createTempFile("JPEG_" + timeStamp + "_", ".jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Display captured image in ImageView
            imageView.setImageURI(photoUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open camera
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "කැමරා අවසරය අවශ්‍යයි", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
