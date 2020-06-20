package com.example.pms;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private View.OnClickListener onButtonClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.btAdd:
                    goToAddUser(v);
                    break;
                case R.id.btView:
                    goToViewUser(v);
                    break;
                case R.id.btSearch:
                    goToSearchUser(v);
                    break;
            }
        }
    };

    Button btAdd, btView, btSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Bakarwals Record Maint");
        setContentView(R.layout.activity_main);
        btAdd = (Button)findViewById(R.id.btAdd);
        btAdd.setOnClickListener(onButtonClick);

        btView = (Button)findViewById(R.id.btView);
        btView.setOnClickListener(onButtonClick);

        btSearch = (Button)findViewById(R.id.btSearch);
        btSearch.setOnClickListener(onButtonClick);
    }

    public void goToAddUser(View view) {
        Intent userAdd = new Intent(this, UserAddActivity.class);
        startActivity(userAdd);
    }

    public void goToViewUser(View view) {
        Intent userView = new Intent(this, UserViewActivity.class);
        startActivity(userView);
    }

    public void goToSearchUser(View view) {
        Intent userSearch = new Intent(this, SearchActivity.class);
        startActivity(userSearch);
    }
}