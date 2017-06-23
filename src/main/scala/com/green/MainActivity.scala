package com.green

import java.util

import android.app.{ActionBar, Activity}
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.graphics.drawable.Animatable
import android.support.design.widget.{TabItem, TabLayout}
import android.support.v4.view.{PagerAdapter, ViewPager}
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.{TextView, Toast}

class MainActivity extends AppCompatActivity  with TabLayout.OnTabSelectedListener{
  // allows accessing `.value` on TR.resource.constants
  implicit val context = this

  //  Theme.AppCompat.Light.NoActionBar
  //    四个布局
  private var connectView: View = _
  private var purchaseView: View = _
  private var helpView: View = _
  private var optionView: View = _

  private var viewPager: ViewPager = _
  private var viewList: util.ArrayList[View] = _

  //  四个标签 和 TabLayout
  private var tabLayout : TabLayout = _
  private var connectTab: TabItem = _
  private var purchaseTab: TabItem = _
  private var helpTab: TabItem = _
  private var optionTab: TabItem = _

  //页面适配器
  private var pagerAdapter: PagerAdapter = _

  /**
    * 初始化控件
    *
    * @return
    */
  def init(): Unit = {

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

    //标签初始化
    tabLayout = findViewById(R.id.tabLayout).asInstanceOf[TabLayout]
    connectTab = findViewById(R.id.connectTab).asInstanceOf[TabItem]
    purchaseTab = findViewById(R.id.purchaseTab).asInstanceOf[TabItem]
    helpTab = findViewById(R.id.helpTab).asInstanceOf[TabItem]
    optionTab = findViewById(R.id.optionTab).asInstanceOf[TabItem]


    //        适配器部分 and tabLayout适配viewPager
    pagerAdapter = new NewPagerAdapter(viewList)
    viewPager.setAdapter(pagerAdapter)
    tabLayout.setupWithViewPager(viewPager)
    tabLayout.getTabAt(0).setText("连接")
    tabLayout.getTabAt(1).setText("购买")
    tabLayout.getTabAt(2).setText("帮助")
    tabLayout.getTabAt(3).setText("选项")
  }


//  以下三个方法是Tab事件
  override def onTabSelected(tab: TabLayout.Tab): Unit = ???

  override def onTabReselected(tab: TabLayout.Tab): Unit = ???

  override def onTabUnselected(tab: TabLayout.Tab): Unit = ???

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.index)

    //        初始化控件
    init()

  }


  case class NewPagerAdapter(viewList: util.ArrayList[View]) extends PagerAdapter {
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