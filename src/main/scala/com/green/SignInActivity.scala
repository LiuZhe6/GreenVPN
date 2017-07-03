package com.green

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.{Button, ImageView, TextView}

/**
  * Created by coder on 17-7-3.
  */
class SignInActivity extends AppCompatActivity with View.OnClickListener{

  private var cancelImageButton : ImageView = _
  private var shareButton : TextView = _
  private var cancel_button : TextView = _
  private var textView : TextView = _

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.sign_in_dialog)

    cancelImageButton = findViewById(R.id.close_btn).asInstanceOf[ImageView]
    shareButton = findViewById(R.id.share_button).asInstanceOf[TextView]
    cancel_button = findViewById(R.id.cancel_button).asInstanceOf[TextView]
    textView = findViewById(R.id.share_task_succeed_tip).asInstanceOf[TextView]


    val s : String = getResources.getString(R.string.share_task_succeed_tip)
    val str : String = String.format(s,new Integer(9999))
    textView.setText(str)

    cancelImageButton.setOnClickListener(this)

  }

  /**
    * 点击事件
    * @param view
    */
  override def onClick(view: View): Unit = {
    view.getId match {
      case R.id.close_btn => finish()
      case R.id.cancel_button => finish()
    }
  }
}
