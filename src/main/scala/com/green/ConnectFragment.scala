package com.green

import java.io.{BufferedReader, IOException, InputStream, InputStreamReader}
import java.net.{HttpURLConnection, URL}
import java.util

import android.os.{AsyncTask, Bundle, Looper}
import android.support.v4.app.Fragment
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.{LinearLayout, Toast}
import com.green.entity.Node

/**
  * Created by coder on 17-6-30.
  */
class ConnectFragment extends Fragment{

  private var view : View = _



  private var regionSelectLinear: LinearLayout = _


  def init() = {
    regionSelectLinear = view.findViewById(R.id.region_select_button).asInstanceOf[LinearLayout]
    regionSelectLinear.setOnClickListener(new View.OnClickListener {
      override def onClick(view: View): Unit = {
        Toast.makeText(getContext,"点击了",Toast.LENGTH_SHORT).show()
        new GetNodeListFromServerTask().execute("http://baidu.com")
      }
    })
  }

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    super.onCreateView(inflater, container, savedInstanceState)
    val v : View = inflater.inflate(R.layout.connect,container,false)

    view = v
    //初始化
    init()

    v
  }


  class GetNodeListFromServerTask extends AsyncTask[String,Int,String]{


    override def doInBackground(params: String*): AnyRef = {

      val url : URL = new URL(params(0))
      val connection : HttpURLConnection = url.openConnection().asInstanceOf[HttpURLConnection]
      connection.setRequestMethod("GET")
      connection.setConnectTimeout(8000)
      connection.setReadTimeout(8000)
      connection.connect()
      val code : Int = connection.getResponseCode
      var response: StringBuilder = null
      if (code == 200){
        val in : InputStream = connection.getInputStream
        //下面对获取到的输入流进行读取
        val reader : BufferedReader = new BufferedReader(new InputStreamReader(in))
         response = new StringBuilder
        var line: String = new String
        while((line = reader.readLine()) != null){
          response.append(line)
        }

      }

      connection.disconnect()
      if (response != null){
        response.toString()
      } else {
        null
      }

    }

    override def onPostExecute(result: String): Unit = {
      Toast.makeText(getContext,result,Toast.LENGTH_SHORT).show()
    }
  }


}
