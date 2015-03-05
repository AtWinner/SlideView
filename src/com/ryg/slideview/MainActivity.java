package com.ryg.slideview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ryg.model.MessageItem;
import com.ryg.slideview.SlideView.OnSlideListener;
import com.ryg.sqlite.DBController;

import android.R.integer;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener, OnClickListener,
        OnSlideListener {

    private static final String TAG = "MainActivity";

    private ListViewCompat mListView;

    private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();

    private SlideView mLastSlideViewWithStatusOn;
    private DBController controller;//数据库操作的业务逻辑类
    private List<HashMap<String, String>> taskList;//任务表的数据集
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new DBController(MainActivity.this, 1);
//        controller.insert();
//        controller.insert("悠唐生活", "80公里，骑行，测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试", 
//        		"0", "无", "北京市东城区崇文门外大街40号", "5", "150", "1", "hehe", "0", "2");
//        controller.execSql("update NeedInfo set Heavy = 20 where id in (2,4)");
        taskList = controller.queryTask();
        initView();
       bindEvent();
    }

    private void initView() {
    	
        mListView = (ListViewCompat) findViewById(R.id.list);

        for (int i = 0; i < taskList.size(); i++) {
            MessageItem item = new MessageItem();
            HashMap<String, String> map = taskList.get(i);
            if (i % 3 == 0) {
                item.iconRes = R.drawable.default_qq_avatar;
            } else {
                item.iconRes = R.drawable.wechat_icon;
            }
            item.Charges = Double.valueOf(map.get("Charges"));
            item.Heavy = Double.valueOf(map.get("Heavy"));
            item.ID = map.get("ID");
            item.ImageName = map.get("ImageName");
            item.IsRealname = Integer.parseInt(map.get("IsRealname"));
            item.OtherWelfare = map.get("OtherWelfare");
            item.Stars= Integer.parseInt(map.get("Stars"));
            item.TaskAddress = map.get("TaskAddress");
            item.TaskRequirement = map.get("TaskRequirement");
            item.TitleName = map.get("TitleName");
            item.start = Integer.parseInt(map.get("start"));
            item.end = Integer.parseInt(map.get("end"));
            mMessageItems.add(item);
        }
        mListView.setAdapter(new SlideAdapter());
        mListView.setOnItemClickListener(this);
    }
    
    private void bindEvent()
    {
    	mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Toast.makeText(MainActivity.this, String.valueOf(arg2), Toast.LENGTH_SHORT).show();
				return false;
			}
		});
    }
    private class SlideAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override 
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) 
        {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.list_item, null);

                slideView = new SlideView(MainActivity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(MainActivity.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

            holder.icon.setImageResource(item.iconRes);
            holder.deleteHolder.setOnClickListener(MainActivity.this);
            holder.titleText.setText(item.TitleName);
            
            holder.textViewHeavy.setText(item.Heavy == 0 ? "无" : (String.valueOf(item.Heavy) + "kg"));
            holder.textViewHeavy.setTextColor(Color.RED);
            
            holder.textViewOtherWelfare.setText(item.OtherWelfare);
            if(item.OtherWelfare.equals("无"))
            {
            	holder.textViewOtherWelfare.setTextColor(Color.BLACK);
            }
            else
            {
            	holder.textViewOtherWelfare.setTextColor(Color.RED);
            }
            
            holder.textViewTaskAddress.setText(item.TaskAddress);
            
            SpannableString ss = new SpannableString(item.TaskRequirement);
            ss.setSpan(new ForegroundColorSpan(Color.RED), item.start, item.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.textViewTaskRequirement.setText(ss);
            
            holder.textViewCharges.setText(String.valueOf(item.Charges));
            holder.textViewCharges.setTextColor(Color.RED);
            if(item.IsRealname == 1)
            {
            	holder.IsRealName.setText("实名认证");
            	holder.IsRealName.setTextColor(Color.rgb(68, 139, 203));
            }
            else
            {
            	holder.IsRealName.setText("");
            }
            return slideView;
        }

    }

    

    private static class ViewHolder {
        public ImageView icon;
        public TextView titleText;
        public ViewGroup deleteHolder;
        public TextView textViewTaskRequirement;
        public TextView textViewHeavy;
        public TextView textViewOtherWelfare;
        public TextView textViewTaskAddress;
        public TextView textViewCharges;
        public TextView IsRealName;
        ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.icon);
            deleteHolder = (ViewGroup)view.findViewById(R.id.holder);
            titleText = (TextView)view.findViewById(R.id.titleText);
            textViewTaskAddress = (TextView)view.findViewById(R.id.textViewTaskAddress);
            textViewTaskRequirement = (TextView)view.findViewById(R.id.textViewTaskRequirement);
            textViewHeavy = (TextView)view.findViewById(R.id.textViewHeavy);
            textViewOtherWelfare = (TextView)view.findViewById(R.id.textViewOtherWelfare);
            textViewCharges = (TextView)view.findViewById(R.id.textViewCharges);
            IsRealName = (TextView)view.findViewById(R.id.IsRealName);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        Log.e(TAG, "onItemClick position=" + position);
    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.holder) {
            Log.e(TAG, "onClick v=" + v);
        }
    }
}
