package com.example.assignment4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class ViewDataActivity extends AppCompatActivity {
    TextView getname,getrollno,getprogram,getdept,getemail,getsubject,getbody,getdate;
    RadioButton co1,co2,co3,ad1,ad2,ad3;
    EditText getco_comment,getad_comment;
    Button co_submit,ad_submit;
    Intent intent;
    Data obj;
    DBHelper dbHelper;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        dbHelper = new DBHelper(this);
        intent = getIntent();
        String type = intent.getStringExtra("type");
        Log.i("TYPE", "type in view:"+type);
        obj = (Data) intent.getSerializableExtra("data");


        getname = findViewById(R.id.textView_getname);
        getname.setText(obj.getName());
        getrollno = findViewById(R.id.textView_getrollno);
        getrollno.setText(obj.getRollno());
        getprogram = findViewById(R.id.textView_getprogram);
        getprogram.setText(obj.getProgram());
        getdept = findViewById(R.id.textView_getdept);
        getdept.setText(obj.getDept());
        getemail = findViewById(R.id.textView_getemail);
        getemail.setText(obj.getEmail());
        getsubject = findViewById(R.id.textView_getsubject);
        getsubject.setText(obj.getSubject());
        getbody = findViewById(R.id.textView_getbody);
        getbody.setText(obj.getBody());
        getdate = findViewById(R.id.textView_getdate);
        getdate.setText(obj.getDate());
        co_submit = findViewById(R.id.button_co_submit);
        ad_submit = findViewById(R.id.button_ad_submit);

        switch (obj.getCo_approv())
        {
            case "Pending":
                co1 = findViewById(R.id.radio_co_pending);
                co1.setChecked(true);
                break;
            case "Approve":
                co2 = findViewById(R.id.radio_co_approv);
                co2.setChecked(true);
                break;
            case "Disapprove":
                co3 = findViewById(R.id.radio_co_disapprov);
                co3.setChecked(true);
                //dbHelper.setAppStatus(obj.getApp_id(),"Disapprove");
                break;
        }
        switch (obj.getAd_approv())
        {
            case "Pending":
                ad1 = findViewById(R.id.radio_ad_pending);
                ad1.setChecked(true);
                break;
            case "Approve":
                ad2 = findViewById(R.id.radio_ad_approv);
                ad2.setChecked(true);
                //dbHelper.setAppStatus(obj.getApp_id(),"Approve");
                break;
            case "Disapprove":
                ad3 = findViewById(R.id.radio_ad_disapprov);
                ad3.setChecked(true);
                //dbHelper.setAppStatus(obj.getApp_id(),"Disapprove");
                break;
        }
        if (!TextUtils.isEmpty(obj.getCo_comment()))
        {
            getco_comment = findViewById(R.id.editText_co_comment);
            getco_comment.setText(obj.getCo_comment());
        }
        if (!TextUtils.isEmpty(obj.getAd_comment()))
        {
            getad_comment = findViewById(R.id.editText_ad_comment);
            getad_comment.setText(obj.getAd_comment());
        }

        if (type.equals("Applicant"))
        {
            findViewById(R.id.radio_co_pending).setEnabled(false);
            findViewById(R.id.radio_co_approv).setEnabled(false);
            findViewById(R.id.radio_co_disapprov).setEnabled(false);
            findViewById(R.id.editText_co_comment).setEnabled(false);
            findViewById(R.id.button_co_submit).setVisibility(View.GONE);
            findViewById(R.id.radio_ad_pending).setEnabled(false);
            findViewById(R.id.radio_ad_approv).setEnabled(false);
            findViewById(R.id.radio_ad_disapprov).setEnabled(false);
            findViewById(R.id.editText_ad_comment).setEnabled(false);
            findViewById(R.id.button_ad_submit).setVisibility(View.GONE);
        }
        else if (type.equals("Coordinator"))
        {
            findViewById(R.id.radio_ad_pending).setEnabled(false);
            findViewById(R.id.radio_ad_approv).setEnabled(false);
            findViewById(R.id.radio_ad_disapprov).setEnabled(false);
            findViewById(R.id.editText_ad_comment).setEnabled(false);
            findViewById(R.id.button_ad_submit).setVisibility(View.GONE);
            if ((obj.getCo_approv().equals("Approve") || obj.getCo_approv().equals("Disapprove")))
            {
                findViewById(R.id.radio_co_pending).setEnabled(false);
                findViewById(R.id.radio_co_approv).setEnabled(false);
                findViewById(R.id.radio_co_disapprov).setEnabled(false);
                findViewById(R.id.editText_co_comment).setEnabled(false);
                findViewById(R.id.button_co_submit).setVisibility(View.GONE);
            }
        }
        else if (type.equals("Admin"))
        {
            findViewById(R.id.radio_co_pending).setEnabled(false);
            findViewById(R.id.radio_co_approv).setEnabled(false);
            findViewById(R.id.radio_co_disapprov).setEnabled(false);
            findViewById(R.id.editText_co_comment).setEnabled(false);
            findViewById(R.id.button_co_submit).setVisibility(View.GONE);
            if ((obj.getAd_approv().equals("Approve") || obj.getAd_approv().equals("Disapprove")))
            {
                findViewById(R.id.radio_ad_pending).setEnabled(false);
                findViewById(R.id.radio_ad_approv).setEnabled(false);
                findViewById(R.id.radio_ad_disapprov).setEnabled(false);
                findViewById(R.id.editText_ad_comment).setEnabled(false);
                findViewById(R.id.button_ad_submit).setVisibility(View.GONE);
            }
        }

        co_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selesctedId = ((RadioGroup)findViewById(R.id.radio_group1)).getCheckedRadioButtonId();
                final String co_status = ((RadioButton)findViewById(selesctedId)).getText().toString();
                final String co_comment = ((EditText)findViewById(R.id.editText_co_comment)).getText().toString();
                final AlertDialog.Builder builder = new AlertDialog.Builder(ViewDataActivity.this);
                builder.setTitle("Alert!");
                builder.setMessage("Are you sure you want to submit?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                            //we are connected to a network
                            obj.setCo_approv(co_status);
                            obj.setCo_comment(co_comment);
                            if (co_status.equals("Disapprove"))
                            {
                                obj.setApp_status("Disapprove");
                            }
                            UpdateData updateData = new UpdateData();
                            updateData.execute();
                        }
                        else
                        {
                            dbHelper.editCoColumns(obj.getApp_id(),co_status,co_comment);
                            Toast.makeText(ViewDataActivity.this,"Data Updated!",Toast.LENGTH_SHORT).show();
                            if (co_status.equals("Disapprove"))
                            {
                                dbHelper.setAppStatus(obj.getApp_id(),"Disapprove");
                            }
                        }

                        setResult(RESULT_OK,intent);
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
        ad_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selesctedId = ((RadioGroup)findViewById(R.id.radio_group2)).getCheckedRadioButtonId();
                final String ad_status = ((RadioButton)findViewById(selesctedId)).getText().toString();
                final String ad_comment = ((EditText)findViewById(R.id.editText_ad_comment)).getText().toString();
                final AlertDialog.Builder builder = new AlertDialog.Builder(ViewDataActivity.this);
                builder.setTitle("Alert!");
                builder.setMessage("Are you sure you want to submit?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                            //we are connected to a network
                            obj.setAd_approv(ad_status);
                            obj.setAd_comment(ad_comment);
                            if (ad_status.equals("Approve"))
                            {
                                obj.setApp_status("Approve");
                            }
                            else if (ad_status.equals("Disapprove"))
                            {
                                obj.setApp_status("Disapprove");
                            }
                            UpdateData updateData = new UpdateData();
                            updateData.execute();
                        }
                        else
                        {
                            dbHelper.editAdColumns(obj.getApp_id(),ad_status,ad_comment);
                            Toast.makeText(ViewDataActivity.this,"Data Updated!",Toast.LENGTH_SHORT).show();
                            if (ad_status.equals("Approve"))
                            {
                                dbHelper.setAppStatus(obj.getApp_id(),"Approve");
                            }
                            else if (ad_status.equals("Disapprove"))
                            {
                                dbHelper.setAppStatus(obj.getApp_id(),"Disapprove");
                            }
                        }

                        setResult(RESULT_OK,intent);
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

    public class UpdateData extends AsyncTask<Void,Void,Void> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(ViewDataActivity.this);
            progressDialog.setMessage("Updating...");
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
                url = new URL("http://10.0.2.2/crud_api/applications/update.php?");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",obj.getApp_id());
                jsonObject.put("app_uname",obj.getApp_uname());
                jsonObject.put("name",obj.getName());
                jsonObject.put("rollno",obj.getRollno());
                jsonObject.put("program",obj.getProgram());
                jsonObject.put("dept",obj.getDept());
                jsonObject.put("email",obj.getEmail());
                jsonObject.put("subject",obj.getSubject());
                jsonObject.put("body",obj.getBody());
                jsonObject.put("date",obj.getDate());
                jsonObject.put("app_status",obj.getApp_status());
                jsonObject.put("co_approval",obj.getCo_approv());
                jsonObject.put("co_comment",obj.getCo_comment());
                jsonObject.put("ad_approval",obj.getAd_approv());
                jsonObject.put("ad_comment",obj.getAd_comment());
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
                            Toast.makeText(getApplicationContext(), "Application Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if (rc == 404)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Application Not Updated", Toast.LENGTH_SHORT).show();
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