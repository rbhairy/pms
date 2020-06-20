package com.example.pms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {
    private View.OnClickListener onButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btUserSearch:
                    goToAddUserSearch(v);
                    break;
            }
        }
    };

    Button btUserSearch;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Bakarwals Record Maint");
        setContentView(R.layout.activity_search);
        etSearch = (EditText) findViewById(R.id.etSearch);
        btUserSearch = (Button) findViewById(R.id.btUserSearch);
        btUserSearch.setOnClickListener(onButtonClick);
    }

    public void goToAddUserSearch(View view) {
        String search_text = etSearch.getText().toString();
        Intent intent = new Intent(this, UserViewActivity.class);
        intent.putExtra("search_text", search_text);
        this.startActivity(intent);
    }
}