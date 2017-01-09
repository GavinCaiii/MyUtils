package com.caitou.myutils.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.caitou.myutils.MainActivity;
import com.caitou.myutils.R;


/**
 * @className:
 * @classDescription:
 * @Author: Guangzhao Cai
 * @createTime: 2016-11-11.
 */

public class NotificationUtil {

    private static NotificationUtil instance;

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mNotifyBuilder;

    private static Context mContext;

    public static NotificationUtil getInstance() {
        if (instance == null) {
            instance = new NotificationUtil();
        }
        return instance;
    }

    private NotificationUtil(){
        if (mContext == null) {
            // TODO:获取全局的context
//            mContext = AppContext.getInstance();
        }
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyBuilder = new NotificationCompat.Builder(mContext);
    }

    /**
     * 显示通知
     * @author Guangzhao Cai
     * @createTime 2016-11-21
     * @lastModify 2016-11-21
     * @param
     * @return
     */
    public void showNotification(String title, String contentText, String ticker,
                                 Class pendingClass, boolean isOngoing, int id) {
        if (mNotifyBuilder == null || mNotificationManager == null)
            return;
        // 设置点击通知后跳转到的activity
        if (mContext != null && pendingClass != null) {
            Intent intent = new Intent(mContext, pendingClass);
            PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mNotifyBuilder.setContentIntent(contentIntent);
        } else {
            Intent intent = new Intent(mContext, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mNotifyBuilder.setContentIntent(contentIntent);
        }
        mNotifyBuilder.setContent(null);
        // 设置icon
        mNotifyBuilder.setSmallIcon(R.mipmap.ic_launcher);
        // 设置通知的title
        if (title.equals("") || title == null)
            title = "车智汇";
        mNotifyBuilder.setContentTitle(title);
        // 设置通知显示内容
        if (contentText.equals("") || contentText == null)
            contentText = "车智汇";
        mNotifyBuilder.setContentText(contentText);
        // 设置ticker
        mNotifyBuilder.setTicker(ticker);
        if (!isOngoing)
            mNotifyBuilder.setAutoCancel(true);
        mNotifyBuilder.setOngoing(isOngoing);

        mNotificationManager.notify(id, mNotifyBuilder.build());

    }

    /**
     * 显示自定义通知
     * @author Guangzhao Cai
     * @createTime 2016-11-21
     * @lastModify 2016-11-21
     * @param
     * @return
     */
    public void showCustomNotification(RemoteViews contentView, String ticker,
                                       Class pendingClass, boolean isOngoing, int id) {
        if (mNotifyBuilder == null || mNotificationManager == null)
            return;
        // 设置点击通知后跳转到的activity
        if (mContext != null && pendingClass != null) {
            Intent intent = new Intent(mContext, pendingClass);
            PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mNotifyBuilder.setContentIntent(contentIntent);
        } else {
            Intent intent = new Intent(mContext, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mNotifyBuilder.setContentIntent(contentIntent);
        }
        // 设置icon
        mNotifyBuilder.setSmallIcon(R.mipmap.ic_launcher);
        // 设置自定义通知样式
        if (contentView != null) {
            mNotifyBuilder.setContent(contentView);
        } else {
            mNotifyBuilder.setContentTitle("车智汇");
            mNotifyBuilder.setContentText("车智汇");
        }
        // 设置ticker
        mNotifyBuilder.setTicker(ticker);

        mNotifyBuilder.setOngoing(isOngoing);

        mNotificationManager.notify(id, mNotifyBuilder.build());
    }

    /**
     * 清除当前通知栏所有通知
     * @author Guangzhao Cai
     * @createTime 2016-11-21
     * @lastModify 2016-11-21
     * @param
     * @return
     */
    public void clearAllNotification() {
        if (mNotificationManager != null)
            mNotificationManager.cancelAll();
    }

    /**
     * 清除指定消息id的通知
     * @author Guangzhao Cai
     * @createTime 2016-11-21
     * @lastModify 2016-11-21
     * @param
     * @return
     */
    public void clearNotificationById(int id) {
        if (mNotificationManager != null)
            mNotificationManager.cancel(id);
    }
}
