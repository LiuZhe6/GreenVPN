package com.green;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.green.myUtils.SuccinctProgress;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
                Toast.makeText(getContext(),"点击了",Toast.LENGTH_SHORT).show();
                new GetNodeListFromServerTask().execute("http://47.52.6.38/Api/User/getNodeList","113","19bd92d7a46b6a06a6b51abbe4e07488");

            }
        });


    }

    class GetNodeListFromServerTask extends AsyncTask<String,Integer,String>{


        @Override
        protected void onPreExecute() {
            SuccinctProgress.showSuccinctProgress(getActivity(),null,SuccinctProgress.THEME_DOT,false,true);
        }

        /**
         *
         * @param strings url,uid,token
         * @return
         */
        @Override
        protected String doInBackground(String... strings) {
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
            Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
            SuccinctProgress.dismiss();
        }
    }
}
