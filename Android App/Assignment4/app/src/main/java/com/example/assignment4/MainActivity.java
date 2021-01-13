package com.example.assignment4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.RecyclerViewOnClickListener {
    List<Data> dataList;
    RecyclerView recyclerView;
    MyAdapter adapter;
    FloatingActionButton add;
    DBHelper dbHelper;
    String username,type,url;
    Cursor items;
    SessionManager sessionManager;
    ConnectivityManager connectivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        add = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recyclerView);
        dataList = new ArrayList<>();
        adapter = new MyAdapter(dataList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            GetData getData = new GetData();
            getData.execute();
        }
        else
        {
            Intent intent = getIntent();
        username = intent.getStringExtra("username");
        type = intent.getStringExtra("type");
        switch (type)
        {
            case "Applicant":
                items = dbHelper.getUserData(username);
                if (items.getCount() != 0){
                    set_list_data(items);
                }
                break;
            case "Coordinator":
                items = dbHelper.getData();
                if (items.getCount() != 0){
                    set_list_data(items);
                }
                add.setVisibility(View.INVISIBLE);
                break;
            case "Admin":
                items = dbHelper.getAdminData();
                if (items.getCount() != 0){
                    set_list_data(items);
                }
                add.setVisibility(View.INVISIBLE);
                break;
        }

        }



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FormActivity.class);
                intent.putExtra("username",username);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode ==RESULT_OK)
        {
            dataList.clear();

            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                GetData getData = new GetData();
                getData.execute();
            }
            else
            {
                //get the last item inserted in database
                 Cursor lastitem = dbHelper.getLastRecord();
                 set_list_data(lastitem);
                 adapter.notifyDataSetChanged();
            }


        }
        if (requestCode == 2 && resultCode == RESULT_OK)
        {
            dataList.clear();

            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                GetData getData = new GetData();
                getData.execute();
            }
            else
            {
                Cursor items = dbHelper.getData();
                set_list_data(items);
                adapter.notifyDataSetChanged();
            }

        }
    }

    public void set_list_data(Cursor lastitem){
        while (lastitem.moveToNext()) {
            Data item = new Data();
            item.setApp_id(lastitem.getInt(lastitem.getColumnIndex(DBHelper.APP_ID)));
            item.setApp_uname(lastitem.getString(lastitem.getColumnIndex(DBHelper.APP_UNAME)));
            item.setName(lastitem.getString(lastitem.getColumnIndex(DBHelper.NAME)));
            item.setRollno(lastitem.getString(lastitem.getColumnIndex(DBHelper.ROLL_NO)));
            item.setProgram(lastitem.getString(lastitem.getColumnIndex(DBHelper.PROGRAM)));
            item.setDept(lastitem.getString(lastitem.getColumnIndex(DBHelper.DEPARTMENT)));
            item.setEmail(lastitem.getString(lastitem.getColumnIndex(DBHelper.EMAIL)));
            item.setSubject(lastitem.getString(lastitem.getColumnIndex(DBHelper.SUBJECT)));
            item.setBody(lastitem.getString(lastitem.getColumnIndex(DBHelper.BODY)));
            item.setDate(lastitem.getString(lastitem.getColumnIndex(DBHelper.DATE)));
            item.setApp_status(lastitem.getString(lastitem.getColumnIndex(DBHelper.APP_STATUS)));
            item.setCo_approv(lastitem.getString(lastitem.getColumnIndex(DBHelper.CO_APPROV)));
            item.setCo_comment(lastitem.getString(lastitem.getColumnIndex(DBHelper.CO_COMMENT)));
            item.setAd_approv(lastitem.getString(lastitem.getColumnIndex(DBHelper.AD_APPROV)));
            item.setAd_comment(lastitem.getString(lastitem.getColumnIndex(DBHelper.AD_COMMENT)));
            //add it to the list
            dataList.add(item);
            adapter.notifyItemInserted(dataList.size()-1);
        }
    }

    @Override
    public void onItemClick(int postion) {
        Intent intent = new Intent(this,ViewDataActivity.class);
        Data obj = dataList.get(postion);
        Bundle extra = new Bundle();
        extra.putSerializable("data",obj);
        intent.putExtras(extra);
        intent.putExtra("type",type);
        startActivityForResult(intent,2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                sessionManager = new SessionManager(this);
                sessionManager.logout();
                finish();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class GetData extends AsyncTask<Void,Void,Void> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (progressDialog.isShowing())
                progressDialog.dismiss();
            adapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent = getIntent();
            username = intent.getStringExtra("username");
            type = intent.getStringExtra("type");
            String jsonString;
            Handler handler = new Handler();

            switch(type)
            {
                case "Applicant":
                    url = "http://10.0.2.2/crud_api/applications/read_single_user.php?app_uname="+username;
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
                    add.setVisibility(View.INVISIBLE);
                    url = "http://10.0.2.2/crud_api/applications/read_all.php?";
                    jsonString = handler.httpCall(url,"POST");
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
                    add.setVisibility(View.INVISIBLE);
                    url = "http://10.0.2.2/crud_api/applications/read_admin.php?";
                    jsonString = handler.httpCall(url,"POST");
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
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Data item = new Data();
                item.setApp_id(Integer.parseInt(jsonObject1.getString("id")));
                item.setApp_uname(jsonObject1.getString("app_uname"));
                item.setName(jsonObject1.getString("name"));
                item.setRollno(jsonObject1.getString("rollno"));
                item.setProgram(jsonObject1.getString("program"));
                item.setDept(jsonObject1.getString("dept"));
                item.setEmail(jsonObject1.getString("email"));
                item.setSubject(jsonObject1.getString("subject"));
                item.setBody(jsonObject1.getString("body"));
                item.setDate(jsonObject1.getString("date"));
                item.setApp_status(jsonObject1.getString("app_status"));
                item.setCo_approv(jsonObject1.getString("co_approval"));
                item.setCo_comment(jsonObject1.getString("co_comment"));
                item.setAd_approv(jsonObject1.getString("ad_approval"));
                item.setAd_comment(jsonObject1.getString("ad_comment"));
                //add it to the list
                dataList.add(item);
            }
        }
    }
}