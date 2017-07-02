package com.green

import java.util

import android.app.ListActivity
import android.content.res.Resources
import android.graphics.Color
import android.os.{Bundle, PersistableBundle}
import android.support.v7.app.AppCompatActivity
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.AbsListView.OnScrollListener
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget._
import com.green.entity.{Node, User}
import com.green.myUtils.DataSaver

/**
  * Created by coder on 17-7-2.
  */
class RegionSelectActivity extends AppCompatActivity with OnScrollListener {

  private var user: User = _

  //listView
  var listView: ListView = _
  //滚动的相关数据
  var scrollState: Int = _
  var lastItem: Int = _
  var visibleItemCount: Int = _

  //节点列表
  private var nodeList: util.ArrayList[Node] = _

  var holder: ViewHolder = _


  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_region_select)

    val bundle: Bundle = getIntent.getExtras.getBundle("bundle")
    val uid: String = bundle.getString("uid")
    val token: String = bundle.getString("token")
    user = new User(uid, token)
    nodeList = bundle.getSerializable("nodeList").asInstanceOf[util.ArrayList[Node]]


    listView = findViewById(R.id.region_list).asInstanceOf[ListView]
    //设置第一个自动选择
    val firstView : View = getLayoutInflater.inflate(R.layout.list_region_info, null)
    val firstTextView : TextView = firstView.findViewById(R.id.region_name).asInstanceOf[TextView]
    firstTextView.setText("自动选择")
    val firstImage : ImageView = firstView.findViewById(R.id.region_icon).asInstanceOf[ImageView]
    firstImage.setImageDrawable(getDrawable(R.drawable.region_auto))
    val firstRadio : RadioButton = firstView.findViewById(R.id.checkbox_button).asInstanceOf[RadioButton]

    listView.addHeaderView(firstView)
    val listAdapter: ListAdapter = new MyListAdapter
    listView.setAdapter(listAdapter)
    if (DataSaver.NODE_INDEX == -1) {
      firstRadio.setChecked(true)
    }

  }


  class MyListAdapter extends BaseAdapter {

    val count: Int = nodeList.size()

    override def getItem(i: Int): AnyRef = i.asInstanceOf[Integer]

    override def getItemId(i: Int): Long = i.toLong

    override def getView(position: Int, view: View, viewGroup: ViewGroup): View = {

      holder = new ViewHolder
      var v: View = null

      val inflater: LayoutInflater = LayoutInflater.from(RegionSelectActivity.this)
      v = inflater.inflate(R.layout.list_region_info, null)
      holder.textView = v.findViewById(R.id.region_name).asInstanceOf[TextView]
      holder.radioButton = v.findViewById(R.id.checkbox_button).asInstanceOf[RadioButton]
      holder.imageView = v.findViewById(R.id.region_icon).asInstanceOf[ImageView]
      v.setTag(holder)

      //获取图片资源
      val regionName = "region_" + nodeList.get(position).getArea.toLowerCase
      val res: Resources = getResources
      val picId = res.getIdentifier(regionName, "drawable", getPackageName)
      holder.imageView.setImageDrawable(getResources.getDrawable(picId, null))

      //设置国家节点名称
      holder.textView.setText(nodeList.get(position).getNodeName + " " + nodeList.get(position).getArea)

      //设置radioButton的点击事件

      holder.radioButton.setOnCheckedChangeListener(new OnCheckedChangeListener {
        override def onCheckedChanged(compoundButton: CompoundButton, isChecked: Boolean): Unit = {
          if (isChecked) {
            Toast.makeText(getApplication, "已选择" + holder.textView.getText.toString, Toast.LENGTH_SHORT).show()
            DataSaver.NODE_INDEX = position
            notifyDataSetChanged()
          }

          finish()
        }
      })

      //选中的项目和当前的是否相等
      if (DataSaver.NODE_INDEX == position) {
        holder.radioButton.setChecked(true)
      } else {
        holder.radioButton.setChecked(false)
      }
      v
    }

    override def getCount: Int = count
  }

  override def onScroll(absListView: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int): Unit = {
    this.visibleItemCount = visibleItemCount
    lastItem = firstVisibleItem + visibleItemCount - 1

  }

  override def onScrollStateChanged(absListView: AbsListView, i: Int): Unit = {
    this.scrollState = scrollState
  }


  class ViewHolder {
    var textView: TextView = _
    var radioButton: RadioButton = _
    var imageView: ImageView = _
  }

}
