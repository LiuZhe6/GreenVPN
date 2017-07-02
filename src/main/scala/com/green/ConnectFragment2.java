package com.green;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.green.entity.Node;
import com.green.entity.User;
import com.green.myUtils.SuccinctProgress;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by coder on 17-7-1.
 */
public class ConnectFragment2 extends Fragment{

    private View view;

    private LinearLayout regionSelectLinearLayout;

    private ProgressBar progressBar;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.connect,container,false);

        //初始化
        init();

        return view;
    }

    private void init() {

        regionSelectLinearLayout = (LinearLayout) view.findViewById(R.id.region_select_button);
        regionSelectLinearLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                new GetNodeListFromServerTask().execute("http://47.52.6.38/Api/User/getNodeList","113","6e2624ab85ef5ca7a43ff96d42cabd98");

            }
        });


    }

    class GetNodeListFromServerTask extends AsyncTask<String,Integer,String>{


        private User user;


        @Override
        protected void onPreExecute() {
            SuccinctProgress.showSuccinctProgress(getActivity(),"正在获取",SuccinctProgress.THEME_DOT,false,true);
        }

        /**
         *
         * @param strings url,uid,token
         * @return
         */
        @Override
        protected String doInBackground(String... strings) {
            user = new User();
            user.setUid(strings[1]);
            user.setToken(strings[2]);

            HttpURLConnection connection = null;
            StringBuffer response = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes("uid="+strings[1]+"&token="+strings[2]);
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.connect();

                int code = connection.getResponseCode();
                if (code == 200){
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    response = new StringBuffer();
                    String line ;
                    while ((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    return response.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return "{err:-1}";
        }

        @Override
        protected void onPostExecute(String s) {
            SuccinctProgress.dismiss();

            StringBuilder sb = new StringBuilder();
            ArrayList<Node> nodeList = null;
            try {
                JSONObject jb = new JSONObject(s);
                int resultCode = Integer.parseInt(jb.getString("err"));
                //正确
                if (resultCode == 0){
                    String data = jb.getString("data");
                    System.out.println("err" + resultCode);
                    System.out.println(data);
                    Gson gson = new Gson();
                    nodeList = gson.fromJson(data,new TypeToken<ArrayList<Node>>()
                    {}.getType());


                } else {

                    Toast.makeText(getContext(),"网络连接超时",Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            //传入ArrayList
            Intent intent = new Intent(getActivity(),RegionSelectActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("uid",user.getUid());
            bundle.putString("token",user.getToken());
            bundle.putSerializable("nodeList",nodeList);
            intent.putExtra("bundle",bundle);
            startActivity(intent);

//            Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();

        }
    }
}
