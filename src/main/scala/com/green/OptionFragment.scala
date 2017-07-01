package com.green

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.{LayoutInflater, View, ViewGroup}

/**
  * Created by coder on 17-6-30.
  */
class OptionFragment extends Fragment{

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    super.onCreateView(inflater, container, savedInstanceState)
    val v : View = inflater.inflate(R.layout.option,container,false)
    v
  }

}
