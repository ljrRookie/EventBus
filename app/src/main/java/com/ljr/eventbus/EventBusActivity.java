package com.ljr.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ljr.eventbus.event.MessageEvent;
import com.ljr.eventbus.event.StickyMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActivity extends AppCompatActivity {
    private static final String TAG = "EventBusActivity";
    private Button mSendMain;
    private Button mSendSticky;
    private TextView mResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1.注册
        EventBus.getDefault().register(this);
        initView();

    }


    private void initView() {
        mSendMain = (Button) findViewById(R.id.btn_eventbus_main);
        mSendSticky = (Button) findViewById(R.id.btn_eventbus_sticky);
        mResult = (TextView) findViewById(R.id.tv_eventbus_result);
        //跳转到发送页面
        mSendMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: =====");
                Toast.makeText(EventBusActivity.this, "====", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EventBusActivity.this,EventBusSendActivity.class));
            }
        });
        //跳转到发送粘性事件页面
        mSendSticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送粘性事件
                EventBus.getDefault().postSticky(new StickyMessageEvent("我是粘性事件！！！"));
                startActivity(new Intent(EventBusActivity.this,EventBusSendActivity.class));

            }
        });
    }


    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
public void MessageEventBus(MessageEvent event){
    mResult.setText(event.name);
}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册
        EventBus.getDefault().unregister(this);
    }
}
