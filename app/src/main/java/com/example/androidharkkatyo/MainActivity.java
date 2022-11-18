package com.example.androidharkkatyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Context context;
    private EditText name, surname;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.editTextTextPersonName4);
        surname = findViewById(R.id.editTextTextPersonName5);
        add = findViewById(R.id.NextPageButton);

        if (!isConnected()){
            Toast.makeText(MainActivity.this,"No internet Connection!", Toast.LENGTH_LONG).show();
        }

        else {
            Toast.makeText(MainActivity.this,"Internet connection established", Toast.LENGTH_LONG).show();
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = name.getText().toString();
                String usersurname = surname.getText().toString();
                Intent intent = new Intent(MainActivity.this,SecondPage.class);
                intent.putExtra("keyname", username);
                intent.putExtra("keysurname", usersurname);
                startActivity(intent);


            }

        });



    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }


}