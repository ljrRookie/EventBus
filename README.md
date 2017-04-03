# EventBus
EventBus一款针对Android优化的发布/订阅事件总线。<br>
主要功能是替代Intent,Handler,BroadCast在Fragment，Activity，Service，线程之间传递消息.优点是开销小，代码更优雅。<br>
以及将发送者和接收者解耦。<br>
![img](https://github.com/greenrobot/EventBus/blob/master/EventBus-Publish-Subscribe.png)<br>
##### 源码： [https://github.com/greenrobot/EventBus](https://github.com/greenrobot/EventBus).
添加依赖compile 'org.greenrobot:eventbus:3.0.0'<br>
## 基本使用步骤：
1. 添加依赖:<br>        compile 'org.greenrobot:eventbus:3.0.0'
2. 注册:      <br>      EventBus.getDefault().register(this);
3. 解注册:    <br>      EventBus.getDefault().unregister(this);  （建议在onDestroy里）
4. 构造发送消息类: <br>  (例如：MessageEvent.class)
5. 发布消息:    <br>    EventBus.getDefault().post(new 发送消息类(参数));   (例如:EventBus.getDefault().post(new MessageEvent("我是事件数据")));
6. 接收消息 :       
      @Subscribe(threadMode = ThreadMode.MAIN)<br> 
       public void MessageEventBus(MessageEvent event){<br>
        mResult.setText(event.name);<br>
    }<br>
### 接收模式：threadMode
#### 1.ThreadMode.MAIN： 表示这个方法在主线程中执行
#### 2.ThreadMode.BACKROUND:  表示该方法在后台执行，不能并发处理
#### 3.ThreadMode.ASYNC:  表示该方法在后台执行，能异步并发处理 
#### 4.ThreadMode.POSTION:  表示该方法和消息发送方在同一个线程中执行

## 粘性事件（StickyEvent）
StickyEvent在内存中保存最新的消息，取消原有消息，执行最新消息，只有在注册后才会执行，如果没有注册，消息会一直保留来内存中。<br>
#### 使用步骤:
1. 添加依赖:<br>        compile 'org.greenrobot:eventbus:3.0.0'
2. 构造发送消息类: <br>  (例如：StickyMessageEvent.class)
3. 发布消息:    <br>    EventBus.getDefault().postSticky(new 发送消息类(参数));   (例如:EventBus.getDefault().postSticky(new StickyMessageEvent("我是粘性事件数据"));)   
4. 接收粘性事件消息 : 
       @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) <br>
    public void StickyMessageEvent(StickyMessageEvent event) { <br>
        mEventbusResult.setText(event.name); <br>
    } <br>
5. 注册:      <br>      EventBus.getDefault().register(this);
6. 解注册:     
EventBus.getDefault().removeAllStickyEvents();<br>
EventBus.getDefault().unregister(this);  （建议在onDestroy里）
# 代码效果
1. 主线程发送事件
2. 发送粘性事件<br>
![img](https://github.com/ljrRookie/EventBus/blob/master/EventBusGif/GIF.gif)
