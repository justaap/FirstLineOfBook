package com.example.activitylifecycletest;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    //基类中onCreate方法中调用此方法注册该Activity
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    //基类中onDestroy方法中调用此方法注销该Activity
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //要关闭程序的地方调用此方法，即可关闭所有Activity并退出程序，
    public static void finishAllActivities() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
