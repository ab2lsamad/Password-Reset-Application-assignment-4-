package com.example.assignment4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    TextView error_label;
    Button login_btn;
    Spinner spinner;
    DBHelper dbHelper;
    Cursor data;
    SessionManager sessionManager;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.editText_username);
        password = findViewById(R.id.editText_password);
        error_label = findViewById(R.id.textView_error);
        login_btn = findViewById(R.id.login_button);
        spinner = findViewById(R.id.spinner);
        final String[] usertype = {"Select User Type","Applicant","Coordinator","Admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,usertype);
        spinner.setAdapter(adapter);
        dbHelper = new DBHelper(this);

        sessionManager = new SessionManager(this);
        if (sessionManager.IsLoggedIn())
        {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("username",sessionManager.getUsername());
            intent.putExtra("type",sessionManager.getType());
            finish();
            startActivity(intent);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = spinner.getSelectedItem().toString();
                if (item.equals("Applicant")){
                    username.setText("user1");
                    password.setText("pass1");
                }
                else if (item.equals("Coordinator")){
                    username.setText("coordinator");
                    password.setText("copass1");
                }
                else if (item.equals("Admin")){
                    username.setText("admin");
                    password.setText("adminpass1");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString()))
                        error_label.setText("Fill in the fields first!");
                    else if (spinner.getSelectedItem().toString().equals("Select User Type"))
                        error_label.setText("Please Select a User Type First!");
                    else
                    {
                        LoginTask task = new LoginTask();
                        task.execute();
                    }
                }
                else
                {
                    switch (spinner.getSelectedItem().toString())
                    {
                        case "Applicant":
                            if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString()))
                                error_label.setText("Fill in the fields first!");
                            else
                            {
                                data = dbHelper.findApplicant(username.getText().toString(),password.getText().toString());
                                if (data.getCount() == 0)
                                    error_label.setText("User Not Found!");
                                else
                                {
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    while (data.moveToNext()){
                                        intent.putExtra("username",data.getString(0));
                                        intent.putExtra("type","applicant");
                                        sessionManager.createSession(data.getString(0),"applicant");
                                    }
                                    finish();
                                    startActivity(intent);
                                }
                            }
                            break;
                        case "Coordinator":
                            if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString()))
                                error_label.setText("Fill in the fields first!");
                            else
                            {
                                data = dbHelper.findCoordinator(username.getText().toString(),password.getText().toString());
                                if (data.getCount() == 0)
                                    error_label.setText("User Not Found!");
                                else
                                {
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    while (data.moveToNext()){
                                        intent.putExtra("username",data.getString(0));
                                        intent.putExtra("type","coordinator");
                                        sessionManager.createSession(data.getString(0),"coordinator");
                                    }
                                    finish();
                                    startActivity(intent);
                                }
                            }
                            break;
                        case "Admin":
                            if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString()))
                                error_label.setText("Fill in the fields first!");
                            else
                            {
                                data = dbHelper.findAdmin(username.getText().toString(),password.getText().toString());
                                if (data.getCount() == 0)
                                    error_label.setText("User Not Found!");
                                else
                                {
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    while (data.moveToNext()){
                                        intent.putExtra("username",data.getString(0));
                                        intent.putExtra("type","admin");
                                        sessionManager.createSession(data.getString(0),"admin");
                                    }
                                    finish();
                                    startActivity(intent);
                                }
                            }
                            break;
                        case "Select User Type":
                            error_label.setText("Please Select a User Type First");
                    }
                }

            }
        });

    }

    public class LoginTask extends AsyncTask<Void,Void,Void> {

        HashMap<String,String> user;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (progressDialog.isShowing())
                progressDialog.dismiss();

            if (user != null)
            {
                if (username.getText().toString().equals(user.get("username")) && password.getText().toString().equals(user.get("password")))
                {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("username",username.getText().toString());
                    intent.putExtra("type",spinner.getSelectedItem().toString());
                    sessionManager.createSession(username.getText().toString(),spinner.getSelectedItem().toString());
                    finish();
                    startActivity(intent);
                }
                else
                {
                    error_label.setText("User Not Found!");
                }
            }
            else
                error_label.setText("Null User");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String jsonString;
            Handler handler = new Handler();
            String type = spinner.getSelectedItem().toString();
            switch (type)
            {
                case "Applicant":
                    url = "http://10.0.2.2/login_api/login/applicant_read.php?username="+username.getText().toString();

                    jsonString = handler.httpCall(url,"GET");
                    if (jsonString != null)
                    {
                        try {
                              parseJson(jsonString);
                        } catch (JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "JSON Parsing Error!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "JSON Server Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    break;
                case "Coordinator":
                    url = "http://10.0.2.2/login_api/login/co_read.php?username="+username.getText().toString();
                    jsonString = handler.httpCall(url,"GET");
                    if (jsonString != null)
                    {
                        try {
                            parseJson(jsonString);
                        } catch (JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "JSON Parsing Error!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "JSON Server Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    break;
                case "Admin":
                    url = "http://10.0.2.2/login_api/login/admin_read.php?username="+username.getText().toString();
                    jsonString = handler.httpCall(url,"GET");
                    if (jsonString != null)
                    {
                        try {
                            parseJson(jsonString);
                        } catch (JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "JSON Parsing Error!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "JSON Server Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    break;
            }
            return null;
        }

        public void parseJson(String jsonString) throws JSONException {
            user = new HashMap<>();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonObject1 = jsonObject.getJSONObject("user");
            user.put("username",jsonObject1.getString("username"));
            user.put("password",jsonObject1.getString("password"));
        }
    }
}