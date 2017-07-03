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
import com.green.entity.HttpResult;
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
public class ConnectFragment2 extends Fragment implements View.OnClickListener {

    private View view;

    //获取节点区域
    private LinearLayout regionSelectLinearLayout;

    //账号有效期
    private LinearLayout userInfoLayout;

    //签到
    private LinearLayout signInLayout;

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
        regionSelectLinearLayout.setOnClickListener(this);
        userInfoLayout = (LinearLayout) view.findViewById(R.id.user_info_layout);
        userInfoLayout.setOnClickListener(this);
        signInLayout = (LinearLayout) view.findViewById(R.id.sign_in);
        signInLayout.setOnClickListener(this);
    }

    /**
     * LinearLayout点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.region_select_button:
                new GetNodeListFromServerTask().execute("http://47.52.6.38/Api/User/getNodeList","113","40dc245fbcdc9dfbeada6e4ee750a1a5");
                break;
            case R.id.user_info_layout:
                new GetUserInfoTask().execute("http://47.52.6.38/Api/User/getUserInfo","113","40dc245fbcdc9dfbeada6e4ee750a1a5");
                break;
            case R.id.sign_in:
                new SignInTask().execute("http://47.52.6.38/Api/User/checkin","113","40dc245fbcdc9dfbeada6e4ee750a1a5");
                break;
            default:
                break;
        }
    }

    /**
     * 获取节点列表的任务
     */
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

            return sendPostRequest(strings[0],strings[1],strings[2]);
        }

        @Override
        protected void onPostExecute(String s) {
            SuccinctProgress.dismiss();

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
            Intent intent = new Intent(getActivity(),SelectRegionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("uid",user.getUid());
            bundle.putString("token",user.getToken());
            bundle.putSerializable("nodeList",nodeList);
            intent.putExtra("bundle",bundle);
            startActivity(intent);


        }
    }

    /**
     * 获取用户信息的任务
     */
    class GetUserInfoTask extends AsyncTask<String,Void,String>{

        private User user;

        @Override
        protected void onPreExecute() {
            user = new User();
            SuccinctProgress.showSuccinctProgress(getActivity(),"正在获取",SuccinctProgress.THEME_DOT,false,true);
        }

        /**
         *
         * @param strings url,uid,token
         * @return
         */
        @Override
        protected String doInBackground(String... strings) {
            //设置uid 和 token 到一个全局对象中
            user.setUid(strings[1]);
            user.setToken(strings[2]);
            return sendPostRequest(strings[0],strings[1],strings[2]);
        }

        @Override
        protected void onPostExecute(String s) {
            SuccinctProgress.dismiss();

            HttpResult httpResult = new HttpResult();
            try {
                JSONObject jb = new JSONObject(s);
                int  err = Integer.parseInt(jb.getString("err"));
                //没有错误则继续解析
                if (err == 0){
                    String data = jb.getString("data");
                    JSONObject jsonOb = new JSONObject(data);
                    user.setUsername(jsonOb.getString("username"));
                    user.setEmail(jsonOb.getString("email"));
                    user.setConnections(jsonOb.getString("connections"));
                    user.setExpiration(jsonOb.getString("expiration"));
                    user.setLevel(jsonOb.getString("level"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("uid:" + user.getUid() + "\n");
            sb.append("token:" + user.getToken() + "\n");
            sb.append("username:" + user.getUsername() + "\n");
            sb.append("email:" + user.getEmail() + "\n");
            sb.append("connections:" + user.getConnections() + "\n");
            sb.append("expiration:" + user.getExpiration() + "\n");
            sb.append("level:" + user.getLevel() + "\n");


            Toast.makeText(getContext(),sb.toString(),Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 签到任务
     */
    class SignInTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            SuccinctProgress.showSuccinctProgress(getActivity(),"正在获取",SuccinctProgress.THEME_DOT,false,true);
        }

        @Override
        protected String doInBackground(String... strings) {
            return sendPostRequest(strings[0],strings[1],strings[2]);
        }

        @Override
        protected void onPostExecute(String s) {
            SuccinctProgress.dismiss();
            HttpResult httpResult = new HttpResult();
            try {
                JSONObject jb = new JSONObject(s);
                httpResult.setStatus(jb.getString("status"));
                httpResult.setInfo(jb.getString("info"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringBuilder sb = new StringBuilder();
            sb.append("status:" + httpResult.getStatus() + "\n");
            sb.append("info:" + httpResult.getInfo() + "\n");

            Toast.makeText(getContext(),sb.toString(),Toast.LENGTH_SHORT).show();

        }
    }

    /**
     *
     * @param url http地址
     * @param uid   用户的uid
     * @param token token令牌
     * @return http返回结果
     */
    public String sendPostRequest(String url,String uid, String token){
        HttpURLConnection connection = null;
        StringBuffer response = null;
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("POST");
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes("uid="+uid+"&token="+token);
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



}
