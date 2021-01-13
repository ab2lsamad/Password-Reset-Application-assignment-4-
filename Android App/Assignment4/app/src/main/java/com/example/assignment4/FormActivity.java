package com.example.assignment4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity {
    TextView tv_name,tv_rollno,tv_program,tv_dept,tv_email,tv_subject,tv_body,tv_date;
    EditText et_name,et_rollno,et_email,et_subject,et_body,et_date;
    Spinner sp_program,sp_dept;
    Button save_btn,cancel_btn,date_btn;
    int myyear,mymonth,myday;
    String emailPattern = "[0-9]+-[0-9]+@uog.edu.pk";
    String rollnoPattern = "[0-9]+-[0-9]+";
    DBHelper dbHelper;
    Intent intent;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2);

        intent = getIntent();
        dbHelper = new DBHelper(FormActivity.this);

        tv_name = findViewById(R.id.textView_name);
        tv_rollno = findViewById(R.id.textView_rollno);
        tv_program = findViewById(R.id.textView_program);
        tv_dept = findViewById(R.id.textView_dept);
        tv_email = findViewById(R.id.textView_email);
        tv_subject = findViewById(R.id.textView_subject);
        tv_body = findViewById(R.id.textView_body);
        tv_date = findViewById(R.id.textView_date);
        et_name = findViewById(R.id.editText_name);
        et_rollno = findViewById(R.id.editText_rollno);
        et_email = findViewById(R.id.editText_email);
        et_subject = findViewById(R.id.editText_subject);
        et_body = findViewById(R.id.editText_body);
        et_date = findViewById(R.id.editText_date);
        et_date.setEnabled(false);
        et_date.setFocusable(false);
        save_btn = findViewById(R.id.btn_save);
        cancel_btn = findViewById(R.id.btn_cancel);
        date_btn = findViewById(R.id.btn_date);
        sp_program = findViewById(R.id.spinner_program);
        sp_dept = findViewById(R.id.spinner_dept);

        String[] programs = {"Select Program","BS","MS/Mphil","PhD"};
        ArrayAdapter<String> adptr_program = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,programs);
        sp_program.setAdapter(adptr_program);

        String[] depts = {"Select Department","CS","IT","SE"};
        ArrayAdapter<String> adptr_dept = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,depts);
        sp_dept.setAdapter(adptr_dept);

        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                myyear = c.get(Calendar.YEAR);
                mymonth = c.get(Calendar.MONTH);
                myday = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dp = new DatePickerDialog(FormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       et_date.setEnabled(true);
                       et_date.setFocusable(true);
                       et_date.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                       et_date.setEnabled(false);
                       et_date.setFocusable(false);
                    }
                },myyear,mymonth,myday);
                dp.show();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_name.getText().toString())||TextUtils.isEmpty(et_rollno.getText().toString())||
                TextUtils.isEmpty(et_email.getText().toString())||TextUtils.isEmpty(et_subject.getText().toString())||
                TextUtils.isEmpty(et_body.getText().toString())||TextUtils.isEmpty(et_date.getText().toString())||
                sp_program.getSelectedItem().toString() == sp_program.getItemAtPosition(0).toString()||
                sp_dept.getSelectedItem().toString() == sp_dept.getItemAtPosition(0).toString())
                {
                    Toast.makeText(FormActivity.this,"Please fill in all fields",Toast.LENGTH_SHORT).show();
                }
                else if (!et_email.getText().toString().matches(emailPattern) && !TextUtils.isEmpty(et_email.getText().toString()))
                {
                    Toast.makeText(FormActivity.this,"Enter valid email address",Toast.LENGTH_SHORT).show();
                }
                else if (!et_rollno.getText().toString().matches(rollnoPattern) && !TextUtils.isEmpty(et_rollno.getText().toString()))
                {
                    Toast.makeText(FormActivity.this,"Enter valid roll no.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to a network
                        InsertData insertData = new InsertData();
                        insertData.execute();
                        setResult(RESULT_OK);
                        finish();
                    }
                    else
                    {
                        boolean result = dbHelper.insert_app_data(intent.getStringExtra("username"),et_name.getText().toString(),
                            et_rollno.getText().toString(), sp_program.getSelectedItem().toString(),
                            sp_dept.getSelectedItem().toString(), et_email.getText().toString(),
                            et_subject.getText().toString(), et_body.getText().toString(), et_date.getText().toString());
                    Log.i("DB","before result true");
                    if (result == true){
                        Log.i("DB","before toast");
                        Toast.makeText(FormActivity.this,"Data inserted locally",Toast.LENGTH_SHORT).show();
                        Log.i("DB","after toast");
                        setResult(RESULT_OK);
                        finish();
                        Log.i("DB","after activity finish");
                    }
                    else
                        Toast.makeText(FormActivity.this,"Data not inserted locally",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
                builder.setTitle("Alert!");
                builder.setMessage("Are you sure you want to cancel?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    public class InsertData extends AsyncTask<Void,Void,Void> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(FormActivity.this);
            progressDialog.setMessage("Sending...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("http://10.0.2.2/crud_api/applications/createapplication.php?");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("app_uname",intent.getStringExtra("username"));
                jsonObject.put("name",et_name.getText().toString());
                jsonObject.put("rollno",et_rollno.getText().toString());
                jsonObject.put("program",sp_program.getSelectedItem().toString());
                jsonObject.put("dept",sp_dept.getSelectedItem().toString());
                jsonObject.put("email",et_email.getText().toString());
                jsonObject.put("subject",et_subject.getText().toString());
                jsonObject.put("body",et_body.getText().toString());
                jsonObject.put("date",et_date.getText().toString());
                String jsonString = jsonObject.toString();

                OutputStream ostream = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(ostream));
                bufferedWriter.write(jsonString);
                bufferedWriter.flush();
                bufferedWriter.close();
                ostream.close();
                int rc = connection.getResponseCode();
                if (rc == 200)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Application Created", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if (rc == 404)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Application Not Created", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}