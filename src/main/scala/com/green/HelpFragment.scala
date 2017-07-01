package com.green

import java.util

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.{Gravity, LayoutInflater, View, ViewGroup}
import android.widget.{BaseExpandableListAdapter, ExpandableListView, TextView}
import com.green.myUtils.{ChildHolder, GroupHolder}

/**
  * Created by coder on 17-6-30.
  */
class HelpFragment extends Fragment{

  private var view : View = _

  //help页面控件
  private var exListView : ExpandableListView = _
  //分别存放控件中的group和child中的String
  private var groupList: util.ArrayList[String] = _
  private var childList : util.ArrayList[String] = _


  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    super.onCreateView(inflater, container, savedInstanceState)
    view = inflater.inflate(R.layout.help,container,false)

    initHelpPage()

    view
  }


  /**
    * 初始化help页面的布局
    */
  def initHelpPage(): Unit = {

    exListView = view.findViewById(R.id.question_list).asInstanceOf[ExpandableListView]
    groupList  = new util.ArrayList[String]()
    childList = new util.ArrayList[String]()

    groupList.add("如何免费使用?")
    groupList.add("我应该选择什么线路?")
    groupList.add("无法玩网络游戏?")
    groupList.add("套餐里的终端数是什么意思?")
    groupList.add("为什么不能下载GooglePlay的应用?")


    val sb1 : StringBuilder = new StringBuilder
    sb1.append("- 注册时选择使用邮箱注册，可以获得更多的免费时间\n")
    sb1.append("- 每天签到可以随机获得4至24小时的免费使用时间\n")
    sb1.append("- 签到可以获得随机积分，积攒积分可以兑换VIP套餐\n")
    childList.add(sb1.toString())

    val sb2 : StringBuilder = new StringBuilder
    sb2.append("- 系统默认会使用“自动选择”帮您分配\n")
    sb2.append("- 网页浏览可以首先考虑尝试亚洲线路\n")
    sb2.append("- 视频浏览可以尝试使用美国线路\n")
    sb2.append("- 如果访问网站对地区有太多要求，则选择同区域线路\n")
    sb2.append("- 如果需要访问CCTV、优酷、土豆等国内线路视频网站，请\n  选择国内线路\n")
    childList.add(sb2.toString())

    val sb3 : StringBuilder = new StringBuilder
    sb3.append("  请按以下步骤处理:\n")
    sb3.append("- 请务必开启全局加速加速\n")
    sb3.append("- 请同事开启了分应用加速，请确保游戏处于加速名单中\n")
    childList.add(sb3.toString())

    val sb4 : StringBuilder = new StringBuilder
    sb4.append("- 终端也可以理解为设备，比如手机、电脑、平板等\n")
    sb4.append("- 包年套餐可以两台终端同时连接使用，其他套餐则允许一\n  台终端，比如包年套餐可以" +
      "两人同时使用一个账号，只要\n  同时连接终端数不超过套餐规定的即可\n")
    sb4.append("- 如果需要定制终端数，请点击“联系客服”\n")
    childList.add(sb4.toString())

    val sb5 : StringBuilder = new StringBuilder
    sb5.append("- 如果您启用了“分应用加速”，那么请确保在加速的名单\n  中，必须要有“下载管理Downloads”及“系统更新\n  Updater”，" +
      "因为GooglePlay给予这些系统组件进行下载\n")
    sb5.append("- 或者您可以在需要GooglePlay下载的时候，关闭“分应用加\n  速”，以保证顺利实现下载\n")
    childList.add(sb5.toString())


    exListView.setAdapter(new MyExpandableViewAdapter(getActivity()))
//    exListView.setGroupIndicator(null)
  }



  //帮助 问题列表适配器
  class MyExpandableViewAdapter(c:Context) extends BaseExpandableListAdapter{

    private val context:Context = c

    /**
      * 按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
      * @return
      */
    override def hasStableIds: Boolean = true


    /**
      * 获得某个父项的某个子项的id
      * @param groupPosition
      * @param childPosition
      * @return
      */
    override def getChild(groupPosition: Int, childPosition: Int): AnyRef = {
      childList.get(groupPosition)
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
      var childHolder : ChildHolder = null
      if (view == null){

        newView = getActivity.getLayoutInflater.cloneInContext(context).inflate(R.layout.child_item,null)
        childHolder = new ChildHolder(newView.findViewById(R.id.child_title))
        newView.setTag(childHolder)
      } else {
        newView = view
        childHolder = view.getTag().asInstanceOf[ChildHolder]
      }

      childHolder.textView.setText(childList.get(groupPosition).toString)
      //改变TextView的状态
      changeChildTextView(childHolder.textView)
      newView
    }




    /**
      * 获得某个父项的某个子项
      * @param parentPos
      * @param childPos
      * @return 返回的是 子序号 0
      */
    override def getChildId(parentPos: Int, childPos: Int): Long = 0
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
      * @return 只有一项  字符串
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
      *
      * @param groupPosition
      * @param b
      * @param view
      * @param viewGroup
      * @return
      */
    override def getGroupView(groupPosition: Int, b: Boolean, view: View, viewGroup: ViewGroup): View = {

      var newView : View = null
      //自定义的类,用来存储控件的相关信息
      var groupHolder : GroupHolder = null
      //这里的view起缓冲的作用
      if (view == null){

        newView = getActivity.getLayoutInflater.cloneInContext(context).inflate(R.layout.parent_item,null)
        groupHolder = new GroupHolder(newView.findViewById(R.id.parent_title))
        newView.setTag(groupHolder)
      } else {
        groupHolder = view.getTag().asInstanceOf[GroupHolder]
        newView = view
      }
      groupHolder.textView.setText(groupList.get(groupPosition).toString)
      //改变TextView的状态
      changeGroupTextView(groupHolder.textView)


      newView
    }

    /**
      * 获取某个父项的id
      * @param parentPosition
      * @return
      */
    override def getGroupId(parentPosition: Int): Long = parentPosition


    /**
      * 修改GroupTextView的样式
      * @return
      */
    def changeGroupTextView(t:TextView): Unit = {
      //设置字体大小为14sp
      t.setTextSize(14)
      //设置TextView高度为
      t.setHeight(200)

      //设置字体和上下边的间隔
      t.setPadding(200,60,50,-1)
      //设置字体左对齐
      t.setGravity(Gravity.START)
    }

    /**
      * 修改ChildTextView的样式
      * @param t
      */
    def changeChildTextView(t:TextView): Unit = {
      //设置字体大小
      t.setTextSize(13)
      //设置字体和上下边的间隔
      t.setPadding(70,10,10,-1)
      //设置字体左对齐
      t.setGravity(Gravity.START)
      //设置字体颜色
      t.setTextColor(Color.GRAY)
    }
  }

}
