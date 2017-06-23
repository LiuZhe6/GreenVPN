package com.green

import java.util

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.graphics.drawable.Animatable
import android.support.v4.view.{PagerAdapter, ViewPager}
import android.view.{LayoutInflater, View, ViewGroup}

class MainActivity extends AppCompatActivity {
  // allows accessing `.value` on TR.resource.constants
  implicit val context = this

  //    四个布局
  private var connectView: View = _
  private var purchaseView: View = _
  private var helpView: View = _
  private var optionView: View = _

  private var viewPager: ViewPager = _
  private var viewList: util.ArrayList[View] = _

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.index)

    //        初始化
    viewPager = findViewById(R.id.viewpager).asInstanceOf[ViewPager]
    val inflater: LayoutInflater = getLayoutInflater()
    connectView = inflater.inflate(R.layout.connect, null)
    purchaseView = inflater.inflate(R.layout.purchase, null)
    helpView = inflater.inflate(R.layout.help, null)
    optionView = inflater.inflate(R.layout.option, null)

    //        将要分页显示的View装入数组中
    viewList = new util.ArrayList[View]()
    viewList.add(connectView)
    viewList.add(purchaseView)
    viewList.add(helpView)
    viewList.add(optionView)

    //        适配器部分
    val pagerAdapter : NewPagerAdapter = new NewPagerAdapter(viewList)
    viewPager.setAdapter(pagerAdapter)
  }


  case class NewPagerAdapter(viewList: util.ArrayList[View]) extends PagerAdapter{
    override def getCount: Int = viewList.size()

    override def isViewFromObject(view: View, o: scala.Any): Boolean = view == o

    override def destroyItem(container: ViewGroup, position: Int, `object`: scala.Any): Unit = {
      container.removeView(viewList.get(position))
    }

    override def getItemPosition(`object`: scala.Any): Int = super.getItemPosition(`object`)

    override def instantiateItem(container: ViewGroup, position: Int): AnyRef = {
      container.addView(viewList.get(position))
      viewList.get(position)
    }
  }

}