package lgyw.com.helloword.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MrRight
 * 六、获取节点信息
 *
 * 获取了界面窗口变化后，这个时候就要获取控件的节点。整个窗口的节点本质是个树结构，通过以下操作节点信息
 *
 * 1、获取窗口节点（根节点）
 *      AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
 *
 *
 * 2、获取指定子节点（控件节点）
 *      //通过文本找到对应的节点集合
 *      List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
 *      //通过控件ID找到对应的节点集合，如com.tencent.mm:id/gd
 *      List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(clickId);
 *
 *
 * 3、当我们获取了节点信息之后，对控件节点进行模拟点击、长按等操作，AccessibilityNodeInfo类提供了performAction()方法让我们执行模拟操作，具体操作可看官方文档介绍，这里列举常用的操作
 *      //模拟点击
 *      accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
 *      //模拟长按
 *      accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
 *      //模拟获取焦点
 *      accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
 *      //模拟粘贴
 *      accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
 */
public class AccessibilitySampleService extends AccessibilityService {

    private final String TAG="AccessibilityService";

    private static final String GET_RED_PACKET = "领取红包";
    private static final String CHECK_RED_PACKET = "查看红包";
    private static final String RED_PACKET_PICKED = "手慢了，红包派完了";
    private static final String RED_PACKET_PICKED2 = "手气";
    private static final String RED_PACKET_PICKED_DETAIL = "红包详情";
    private static final String RED_PACKET_SAVE = "已存入零钱";
    private static final String RED_PACKET_NOTIFICATION = "[微信红包]";


    private List<AccessibilityNodeInfo> parents;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        parents = new ArrayList<>();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        Log.d(TAG,"RECEIVE EVENT!");
        switch (event.getEventType()){
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                List<CharSequence> texts = event.getText();
                if(!texts.isEmpty()){
                    for(CharSequence charSequence:texts){
                        String content=charSequence.toString();
                        if(content.contains(RED_PACKET_NOTIFICATION)){
                            if(event.getParcelableData()!=null&&event.getParcelableData() instanceof Notification){
                                Notification notification= (Notification) event.getParcelableData();
                                PendingIntent pendingIntent = notification.contentIntent;
                                try {
                                    pendingIntent.send();
                                    Log.d(TAG,"enter wechat from notification");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                String className = event.getClassName().toString();
                switch (className) {
                    case "com.tencent.mm.ui.LauncherUI":
                        //点击最后一个红包
                        Log.d(TAG, "点击红包");
                        getLastPacket();
                        break;
                    case "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI":
                        //开红包
                        Log.d(TAG, "开红包");
                        inputClick("com.tencent.mm:id/bg7");
                        break;
                    case "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI":
                        //退出红包
                        Log.d(TAG, "退出红包");
                        inputClick("com.tencent.mm:id/gd");
                        break;
                }
                break;
            default:
        }
    }

    /**
     * 获取List中最后一个红包，并进行模拟点击
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void getLastPacket() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        recycle(rootNode);
        if(parents.size()>0){
            parents.get(parents.size() - 1).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    /**
     * 通过ID获取控件，并进行模拟点击
     * @param clickId id
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void inputClick(String clickId) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(clickId);
            for (AccessibilityNodeInfo item : list) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }



    /**
     * 回归函数遍历每一个节点，并将含有"领取红包"存进List中
     *
     * @param info info
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            if (info.getText() != null) {
                if (GET_RED_PACKET.equals(info.getText().toString())) {
                    if (info.isClickable()) {
                        info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                    AccessibilityNodeInfo parent = info.getParent();
                    while (parent != null) {
                        if (parent.isClickable()) {
                            parents.add(parent);
                            break;
                        }
                        parent = parent.getParent();
                    }
                }
            }
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    recycle(info.getChild(i));
                }
            }
        }
    }



    @Override
    public void onInterrupt() {

    }




}
