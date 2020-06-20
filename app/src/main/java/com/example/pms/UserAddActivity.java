package com.example.pms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserAddActivity extends AppCompatActivity {
    Button btAddUser;
    EditText etSerialNo, etPassNo, etAdhaarCardNo, etElectionId, etName;
    EditText etFatherName, etPermanentAddress, etTempAddress, etPhoneNumber;
    EditText etAge;
    RadioButton rbMale;
    ImageView ivTakePhoto;

    DatabaseHelper mDatabaseHelper;

    //keep track of camera capture and cropping  intent
    final int CAMERA_CAPTURE = 1;
    final int PIC_CROP = 2;
    final int REQUEST_CODE_GALLERY = 3;

    //captured picture uri
    private Uri picUri;

    private View.OnClickListener onButtonClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.btAddUser:
                    goToAddUserToSqliteDB(v);
                    break;
                case R.id.user_photo:
                    takePicture();
                    //choosePicture();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Bakarwals Record Maint");
        setContentView(R.layout.activity_user_add);

        etSerialNo = (EditText) findViewById(R.id.etSerialNo);
        etPassNo = (EditText) findViewById(R.id.etPassNo);
        etAdhaarCardNo = (EditText) findViewById(R.id.etAdhaarCardNo);
        etElectionId = (EditText) findViewById(R.id.etElectionId);
        etName = (EditText) findViewById(R.id.etName);
        etFatherName = (EditText) findViewById(R.id.etFatherName);
        etAge = (EditText) findViewById(R.id.etAge);
        etPermanentAddress = (EditText) findViewById(R.id.etPermanentAddress);
        etTempAddress = (EditText) findViewById(R.id.etTempAddress);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        rbMale = ((RadioButton) findViewById(R.id.radioM));

        btAddUser = (Button)findViewById(R.id.btAddUser);
        btAddUser.setOnClickListener(onButtonClick);

        ivTakePhoto = (ImageView)findViewById(R.id.user_photo);
        ivTakePhoto.setOnClickListener(onButtonClick);

        mDatabaseHelper = DatabaseHelper.getInstance(this.getApplicationContext());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(),
                        "You don't have permission to access file location!",
                        Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE) {
                picUri = data.getData();
                if (true) {
                    Bundle extras = data.getExtras();
                    Bitmap thePic = extras.getParcelable("data");
                    ImageView picView = (ImageView)findViewById(R.id.user_photo);
                    picView.setImageBitmap(thePic);
                } else {
                    cropPicture();
                }
            } else if(requestCode == PIC_CROP) {
                Bundle extras = data.getExtras();
                Bitmap thePic = extras.getParcelable("data");
                ImageView picView = (ImageView)findViewById(R.id.user_photo);
                picView.setImageBitmap(thePic);
            } else if (requestCode == REQUEST_CODE_GALLERY && data != null){
                Uri uri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ImageView picView = (ImageView)findViewById(R.id.user_photo);
                    picView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void cropPicture() {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        } catch(ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void clickPicture() {
        try {
            //use standard intent to capture an image
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //we will handle the returned data in onActivityResult
            startActivityForResult(captureIntent, CAMERA_CAPTURE);
        } catch(ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support capturing images!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /* choose an existing picture stored on phone */
    public void choosePicture() {
        ActivityCompat.requestPermissions(
                UserAddActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_GALLERY
        );
    }

    public void takePicture() {
        clickPicture();
        /*
        String[] listItems = {"Click New Picture", "Choose an Existing Picture"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserAddActivity.this);
        builder.setTitle("Choose item");

        int checkedItem = 0;
        builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        clickPicture();
                        break;
                    case 1:
                        choosePicture();
                        break;
                }
            }
        });
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        */
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void goToAddUserToSqliteDB(View view) {
        String serialNo = etSerialNo.getText().toString();
        String passNo = etPassNo.getText().toString();
        String adhaarCardNo = etAdhaarCardNo.getText().toString();
        String electionId = etElectionId.getText().toString();
        String name = etName.getText().toString();
        String fatherName = etFatherName.getText().toString();
        String age = etAge.getText().toString();
        String sex = rbMale.isChecked() ? "1" : "0";
        String permanentAddress = etPermanentAddress.getText().toString();
        String tempAddress = etTempAddress.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();

        boolean insertData;
        if (false) {
            insertData = mDatabaseHelper.addData(serialNo,passNo, adhaarCardNo, electionId,
                    name, fatherName, age, sex, permanentAddress, tempAddress, phoneNumber);

        } else {
            ImageView picView = (ImageView)findViewById(R.id.user_photo);
            byte[] dpThumbnail = imageViewToByte(picView);
            insertData = mDatabaseHelper.addData(serialNo,passNo, adhaarCardNo, electionId,
                    name, fatherName, age, sex, permanentAddress, tempAddress, phoneNumber,
                    dpThumbnail);
        }
        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }

        Intent userView = new Intent(this, UserViewActivity.class);
        startActivity(userView);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}