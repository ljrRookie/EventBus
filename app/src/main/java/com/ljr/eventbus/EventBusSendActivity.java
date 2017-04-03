package com.ljr.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ljr.eventbus.event.MessageEvent;
import com.ljr.eventbus.event.StickyMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusSendActivity extends AppCompatActivity {
    private Button mEventbusSendMain;
    private Button mEventbusSendSticky;
    private TextView mEventbusResult;
    //判断是否第一次接收粘性事件,只能注册一次
    boolean isFirstFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_send);
        initView();

    }


    private void initView() {
        mEventbusSendMain = (Button) findViewById(R.id.btn_eventbus_send_main);
        mEventbusSendSticky = (Button) findViewById(R.id.btn_eventbus_send_sticky);
        mEventbusResult = (TextView) findViewById(R.id.tv_eventbus_result);
        //主线程发送事件并传递数据
        mEventbusSendMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("消息消息，我是发送的消息"));
                finish();
            }
        });
        //接收粘性事件数据
        mEventbusSendSticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstFlag) {
                    isFirstFlag = false;
                    //注册
                    EventBus.getDefault().register(EventBusSendActivity.this);
                }

            }
        });
    }

    //接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void StickyMessageEvent(StickyMessageEvent event) {
        mEventbusResult.setText(event.name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}
