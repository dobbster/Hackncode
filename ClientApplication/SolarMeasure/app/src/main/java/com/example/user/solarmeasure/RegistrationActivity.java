package com.example.user.solarmeasure;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;

public class RegistrationActivity extends AppCompatActivity {

    EditText editText, editText2;
    Button button;
    String name;
    int capacity, uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);



        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        button = findViewById(R.id.button);
        final SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = String.valueOf(editText.getText());
                capacity = Integer.parseInt(String.valueOf(editText2.getText()));
                editor.putString("name", name);
                editor.putInt("capacity", capacity);

                new AsyncTask<Void, Void, String>() {

                    class Idgetter {
                        int variable;
                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        try {
                            return new NetworkManager().getDataFromURL(new URL("http://192.168.43.36/powerGrid/regist.php"));
                        } catch (IOException e) {
                            return "NAN";
                        }
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        if(!s.equals("NAN")) {
                            Gson parser = new Gson();
                            Idgetter idgetter = parser.fromJson(s, Idgetter.class);
                            uid = idgetter.variable;
                            editor.putInt("uid", uid);
                            editor.apply();
                            Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                            startActivity(intent);
                            RegistrationActivity.this.finish();
                        }
                    }
                }.execute();
            }
        });

    }
}
