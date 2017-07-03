package com.green;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.green.entity.Node;
import com.green.entity.User;
import com.green.myUtils.DataSaver;

import java.util.ArrayList;

/**
 * Created by coder on 17-7-2.
 */
public class SelectRegionActivity extends AppCompatActivity {

    //用户信息
    private User user;
    //节点列表
    private ArrayList<Node> nodeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_select);

        //得到fragment传入的bundle
        Bundle bundle = getIntent().getExtras().getBundle("bundle");
        String uid = bundle.getString("uid");
        String token = bundle.getString("token");
        user = new User(uid, token);
        nodeList = (ArrayList<Node>) bundle.getSerializable("nodeList");
        //添加第一个 “自动选择”
        Node node = new Node();
        node.setNodeName("自动选择");
        node.setNid(0);
        node.setArea("auto");
        nodeList.add(0, node);

        //设置RecycleView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.region_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        NodeRecyclerAdapter nodeRecyclerAdapter = new NodeRecyclerAdapter(nodeList);
        recyclerView.setAdapter(nodeRecyclerAdapter);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        TextView textView;

        RadioButton radioButton;

        LinearLayout layout;


        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.region_icon);
            textView = (TextView) view.findViewById(R.id.region_name);
            radioButton = (RadioButton) view.findViewById(R.id.checkbox_button);
            layout = (LinearLayout) view.findViewById(R.id.node_list_layout);
        }
    }

    class NodeRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener  {

        private ArrayList<Node> nodeArrayList;


        public NodeRecyclerAdapter(ArrayList<Node> nodeList) {
            nodeArrayList = nodeList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_region_info, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Node node = nodeArrayList.get(position);
            System.out.println(node.getNodeName());
            holder.textView.setText(node.getNodeName());
            String drawableName = "region_" + node.getArea().toLowerCase();
            System.out.println("drawableName : "+drawableName);
            int picId = getResources().getIdentifier(drawableName, "drawable", getPackageName());
            holder.imageView.setImageResource(picId);

            if (DataSaver.NODE_INDEX == position){
                holder.radioButton.setChecked(true);
            } else {
                holder.radioButton.setChecked(false);
            }
            //设置radiobutton不可点击
            holder.radioButton.setClickable(false);

            holder.layout.setTag(position);
            holder.layout.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return nodeArrayList.size();
        }


        /**
         * radioButton点击事件
         * @param view
         */
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.node_list_layout:
                    DataSaver.NODE_INDEX = (int) view.getTag();
                    notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),"点击了"+nodeArrayList.get(DataSaver.NODE_INDEX).getNodeName(),
                            Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    }
}
