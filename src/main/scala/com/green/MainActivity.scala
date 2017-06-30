package com.green

import java.util

import android.app.{ActionBar, Activity}
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.design.widget.{TabItem, TabLayout}
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
  private var viewList: util.ArrayList[View] = _

  //  四个标签 和 TabLayout
  private var tabLayout : TabLayout = _
  private var connectTab: TabItem = _
  private var purchaseTab: TabItem = _
  private var helpTab: TabItem = _
  private var optionTab: TabItem = _

  //页面适配器
  private var pagerAdapter: PagerAdapter = _


  //help页面控件
  private var exListView : ExpandableListView = _
  //分别存放控件中的group和child中的String
  var groupList: util.ArrayList[String] = _
  var childList : util.ArrayList[String] = _
  private var listViewAdapter : MyExpandableViewAdapter = _



  /**
    * 初始化控件
    *
    * @return
    */
  def init(): Unit = {

    //初始化总页面的布局
    initIndex()

    //初始化help页面的布局
//    initHelpPage()

  }

  /**
    * 初始化总页面布局
    */
  def initIndex():Unit = {
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
    println("1" + tabLayout)
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

  /**
    * 初始化help页面的布局
    */
  def initHelpPage(): Unit = {

    exListView = findViewById(R.id.question_list).asInstanceOf[ExpandableListView]
    groupList  = new util.ArrayList[String]()
    childList = new util.ArrayList[String]()

    groupList.add("如何免费使用?")
    groupList.add("我应该选择什么线路?")
    groupList.add("无法玩网络游戏?")
    groupList.add("套餐里的终端数是什么意思?")
    groupList.add("为什么不能下载GooglePlay的应用?")


    val sb : StringBuilder = new StringBuilder
    sb.append("- 注册时选择使用邮箱注册，可以获得更多的免费时间\n")
    sb.append("- 每天签到可以随机获得4至24小时的免费使用时间\n")
    sb.append("- 签到可以获得随机积分，积攒积分可以兑换VIP套餐")
    childList.add(sb.toString())

    sb.clear()
    sb.append("- 系统默认会使用“自动选择”帮您分配\n")
    sb.append("- 网页浏览可以首先考虑尝试亚洲线路\n")
    sb.append("- 视频浏览可以尝试使用美国线路\n")
    sb.append("- 如果访问网站对地区有太多要求，则选择同区域线路\n")
    sb.append("- 如果需要访问CCTV、优酷、土豆等国内线路视频网站，请选择国内线路\n")
    childList.add(sb.toString())

    sb.clear()


    listViewAdapter = new MyExpandableViewAdapter()
    exListView.setAdapter(listViewAdapter)
  }

//  以下三个方法是Tab事件
  override def onTabSelected(tab: TabLayout.Tab): Unit = ???

  override def onTabReselected(tab: TabLayout.Tab): Unit = ???

  override def onTabUnselected(tab: TabLayout.Tab): Unit = ???

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.index)

    //        全局初始化函数,初始化控件
    init()

  }


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


  //帮助 问题列表适配器
  class MyExpandableViewAdapter extends BaseExpandableListAdapter{



    /**
      * 按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
      * @return
      */
    override def hasStableIds: Boolean = false



    /**
      * 获得某个父项的某个子项的id
      * @param groupPosition
      * @param childPosition
      * @return
      */
    override def getChild(groupPosition: Int, childPosition: Int): AnyRef = {
      childList
    }



    /**
      * 获得子项显示的view
      *
      * @param groupPosition
      * @param childPosition
      * @param b
      * @param view
      * @param viewGroup
      * @return
      */
    override def getChildView(groupPosition: Int, childPosition: Int, b: Boolean, view: View, viewGroup: ViewGroup): View = {

      var newView : View = null

      if (view == null){
        var inflater : LayoutInflater = MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE).asInstanceOf[LayoutInflater]
        newView = inflater.inflate(R.layout.child_item,null)
      } else {
        newView = view
      }

      val string = childList.get(groupPosition)
      newView.setTag(R.layout.parent_item, groupPosition)
      newView.setTag(R.layout.child_item, childPosition)
      val textView = newView.findViewById(R.id.child_title).asInstanceOf[TextView]
      textView.setText(string)
      newView
    }




    /**
      * 获得某个父项的某个子项
      * @param parentPos
      * @param childPos
      * @return
      */
    override def getChildId(parentPos: Int, childPos: Int): Long = childPos
    /**
      * 子项是否可选中，如果需要设置子项的点击事件，需要返回true
      * @param i
      * @param i1
      * @return
      */
    override def isChildSelectable(i: Int, i1: Int): Boolean = false

    /**
      * 获取某个父项的子向数目
      * @param groupPosition
      * @return
      */
    override def getChildrenCount(groupPosition: Int): Int = 1


    /*************************以下是group method*****************************/


    /**
      * 获得某个父项
      * @param parentPosition
      * @return
      */
    override def getGroup(parentPosition: Int): AnyRef = groupList.get(parentPosition)

    /**
      * 获取父项的数量
      * @return
      */
    override def getGroupCount: Int = groupList.size()

    /**
      * 获得父项显示的view
      * @param parentPosition
      * @param b
      * @param view
      * @param viewGroup
      * @return
      */
    override def getGroupView(parentPosition: Int, b: Boolean, view: View, viewGroup: ViewGroup): View = {

      var newView : View = null
      if (view == null){
        val inflater = MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE).asInstanceOf[LayoutInflater]
        newView = inflater.inflate(R.layout.parent_item, null)
      } else {
        newView = view
      }

      val string = groupList.get(parentPosition)

      newView.setTag(R.layout.parent_item,parentPosition)
      newView.setTag(R.layout.child_item,-1)
      val textView = newView.findViewById(R.id.parent_title).asInstanceOf[TextView]
      textView.setText(groupList.get(parentPosition))
      newView
    }

    /**
      * 获取某个父项的id
      * @param parentPosition
      * @return
      */
    override def getGroupId(parentPosition: Int): Long = parentPosition

  }
}