package com.green

import java.util

import android.app.{ActionBar, Activity, FragmentManager}
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout.Tab
import android.support.v7.app.AppCompatActivity
import android.support.design.widget.{TabItem, TabLayout}
import android.support.v4.app
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.support.v4.view.{PagerAdapter, ViewPager}
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget._

class MainActivity extends AppCompatActivity  with TabLayout.OnTabSelectedListener {
  // allows accessing `.value` on TR.resource.constants
  implicit val context = this

  //  Theme.AppCompat.Light.NoActionBar
  //    四个布局
  private var connectView: View = _
  private var purchaseView: View = _
  private var helpView: View = _
  private var optionView: View = _

  private var viewPager: ViewPager = _
  private var fragmentList : util.ArrayList[Fragment] = _

  //  四个标签 和 TabLayout
  private var tabLayout : TabLayout = _
  private var connectTab: Tab = _
  private var purchaseTab: Tab= _
  private var helpTab: Tab = _
  private var optionTab: Tab = _



  var connectFragment : ConnectFragment = _
  var purchaseFragment : PurchaseFragment = _
  var helpFragment : HelpFragment = _
  var optionFragment : OptionFragment = _

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.index)

    //创建控件和点击事件
    initView()
    initEvents()

  }


  def initView() = {
    connectFragment  = new ConnectFragment
    purchaseFragment = new PurchaseFragment
    helpFragment = new HelpFragment
    optionFragment = new OptionFragment
    tabLayout = findViewById(R.id.tabLayout).asInstanceOf[TabLayout]
    viewPager = findViewById(R.id.viewpager).asInstanceOf[ViewPager]

    fragmentList = new util.ArrayList[Fragment]()
    fragmentList.add(connectFragment)
    fragmentList.add(purchaseFragment)
    fragmentList.add(helpFragment)
    fragmentList.add(optionFragment)

    val fragmentManager : app.FragmentManager = this.getSupportFragmentManager()
    viewPager.setAdapter(new MainFragmentPagerAdapter(fragmentManager,fragmentList))
    //默认显示连接页面
    viewPager.setCurrentItem(0)

    //设置tabLayout与viewPager级联
    tabLayout.setupWithViewPager(viewPager)

    //实例化Tab
    connectTab = tabLayout.getTabAt(0)
    purchaseTab = tabLayout.getTabAt(1)
    helpTab = tabLayout.getTabAt(2)
    optionTab = tabLayout.getTabAt(3)

    //重新设置标签名称，上面会清除全部标签
    connectTab.setText("连接")
    purchaseTab.setText("购买")
    helpTab.setText("帮助")
    optionTab.setText("选项")

  }
  def initEvents() = {

    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener {

      override def onTabSelected(tab: Tab): Unit = {
        if (tab == tabLayout.getTabAt(0)){
          viewPager.setCurrentItem(0)
        } else if (tab == tabLayout.getTabAt(1)){
          viewPager.setCurrentItem(1)
        } else if (tab == tabLayout.getTabAt(2)){
          viewPager.setCurrentItem(2)
        } else if (tab == tabLayout.getTabAt(3)){
          viewPager.setCurrentItem(3)
        }
      }

      override def onTabUnselected(tab: Tab): Unit = Unit

      override def onTabReselected(tab: Tab): Unit = Unit
    })
  }


//  以下三个方法是Tab事件
  override def onTabSelected(tab: TabLayout.Tab): Unit = Unit

  override def onTabReselected(tab: TabLayout.Tab): Unit = Unit

  override def onTabUnselected(tab: TabLayout.Tab): Unit = Unit


  //滑动页面适配器
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






  //TabItem的监听事件
  class MyOnClickListener(i:Int) extends View.OnClickListener{

    private val index = i

    override def onClick(view: View): Unit = viewPager.setCurrentItem(index)
  }


}