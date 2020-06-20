package com.example.pms;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.NoSuchAlgorithmException;

public class SingleUserViewActivity extends AppCompatActivity {
    public static String getMD5(byte[] source) {
        StringBuilder sb = new StringBuilder();
        java.security.MessageDigest md5 = null;
        try {
            md5 = java.security.MessageDigest.getInstance("MD5");
            md5.update(source);
        } catch (NoSuchAlgorithmException e) {
        }
        if (md5 != null) {
            for (byte b : md5.digest()) {
                sb.append(String.format("%02X", b));
            }
        }
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Bakarwals Record Maint");
        setContentView(R.layout.activity_single_user_view);

        /* get bytes using bundle */
        Bundle extras = getIntent().getExtras();
        byte [] dp_thumbnail = extras.getByteArray("dp_thumbnail");
        ImageView image = findViewById(R.id.suvPhoto);
        //byte[] dp_thumbnail = getIntent().getByteArrayExtra("dp_thumbnail");
        Bitmap bitmap = BitmapFactory.decodeByteArray(dp_thumbnail, 0, dp_thumbnail.length);
        image.setImageBitmap(bitmap);


        EditText suvSerialNo = (EditText) findViewById(R.id.suvSerialNo);
        suvSerialNo.setText(getIntent().getStringExtra("serial_no"));

        EditText suvPassNo = (EditText) findViewById(R.id.suvPassNo);
        suvPassNo.setText(getIntent().getStringExtra("pass_no"));

        EditText suvAdhaarCardNo = (EditText) findViewById(R.id.suvAdhaarCardNo);
        suvAdhaarCardNo.setText(getIntent().getStringExtra("adhaar_card_no"));

        EditText suvElectionId = (EditText) findViewById(R.id.suvElectionId);
        suvElectionId.setText(getIntent().getStringExtra("election_id"));

        EditText suvName = (EditText) findViewById(R.id.suvName);
        suvName.setText(getIntent().getStringExtra("name"));

        EditText suvFatherName = (EditText) findViewById(R.id.suvFatherName);
        suvFatherName.setText(getIntent().getStringExtra("father_name"));

        EditText suvAge = (EditText) findViewById(R.id.suvAge);
        suvAge.setText(getIntent().getStringExtra("age"));

        EditText suvPermanentAddress = (EditText) findViewById(R.id.suvPermanentAddress);
        suvPermanentAddress.setText(getIntent().getStringExtra("permanent_address"));

        EditText suvTempAddress = (EditText) findViewById(R.id.suvTempAddress);
        suvTempAddress.setText(getIntent().getStringExtra("temp_address"));

        EditText suvPhoneNumber = (EditText) findViewById(R.id.suvPhoneNumber);
        suvPhoneNumber.setText(getIntent().getStringExtra("mobile_number"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        EditText editText = findViewById(R.id.suvSerialNo);
        String serial_no = editText.getText().toString();
        if (id == R.id.user_delete) {
            Log.d("RAJ", "deleting user of serial no:" + serial_no);
            return true;
        } else if (id == R.id.user_edit) {
            Log.d("RAJ", "editing user of serial no:" + serial_no);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}