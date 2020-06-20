package com.example.pms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class UserViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Bakarwals Record Maint");
        setContentView(R.layout.activity_user_view);

        List<UserModel> userModelList = new ArrayList<>();
        mDatabaseHelper = DatabaseHelper.getInstance(this.getApplicationContext());


        String search_text = getIntent().getStringExtra("search_text");
        if (search_text != null) {
            Log.d("RAJ", "Getting data from database for serach_text: " + search_text);
            boolean getTumbnailDone = false;
            HashMap<String, byte[]> dpThumbnails = null;
            for (HashMap<String, String> user : mDatabaseHelper.getData(search_text)) {
                if (getTumbnailDone == false) {
                    dpThumbnails = mDatabaseHelper.getDpThumbnail();
                    getTumbnailDone = true;
                }
                userModelList.add(new UserModel(R.drawable.ic_add_user_vector, user.get("SerialNo"),
                        user.get("PassNo"), user.get("AdhaarCardNo"), user.get("ElectionId"),
                        user.get("Name"), user.get("FatherName"),
                        Integer.parseInt(user.get("Age")), Integer.parseInt(user.get("Sex")),
                        user.get("PermanentAddress"), user.get("TempAddress"), user.get("MobileNumber"),
                        dpThumbnails.get(user.get("SerialNo"))));
            }
        } else {
            Log.d("RAJ", "Getting data from database all users");
            boolean getTumbnailDone = false;
            HashMap<String, byte[]> dpThumbnails = null;
            for (HashMap<String, String> user : mDatabaseHelper.getData()) {
                Log.d("RAJ", "Adding user to recycle view for " + user.get("SerialNo"));
                if (false) {
                    userModelList.add(new UserModel(R.drawable.ic_add_user_vector, user.get("SerialNo"),
                            user.get("PassNo"), user.get("AdhaarCardNo"), user.get("ElectionId"),
                            user.get("Name"), user.get("FatherName"),
                            Integer.parseInt(user.get("Age")), Integer.parseInt(user.get("Sex")),
                            user.get("PermanentAddress"), user.get("TempAddress"), user.get("MobileNumber")));
                } else {
                    if (getTumbnailDone == false) {
                        dpThumbnails = mDatabaseHelper.getDpThumbnail();
                        getTumbnailDone = true;
                    }
                    userModelList.add(new UserModel(R.drawable.ic_add_user_vector, user.get("SerialNo"),
                            user.get("PassNo"), user.get("AdhaarCardNo"), user.get("ElectionId"),
                            user.get("Name"), user.get("FatherName"),
                            Integer.parseInt(user.get("Age")), Integer.parseInt(user.get("Sex")),
                            user.get("PermanentAddress"), user.get("TempAddress"), user.get("MobileNumber"),
                            dpThumbnails.get(user.get("SerialNo"))));
                }
            }
        }

        recyclerView = findViewById(R.id.UserRecView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        UserAdapter userAdaptor = new UserAdapter(this, userModelList);
        recyclerView.setAdapter(userAdaptor);
        userAdaptor.notifyDataSetChanged();
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}