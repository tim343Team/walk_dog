package com.wallet.walkthedog.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActivityLifecycleManager implements Application.ActivityLifecycleCallbacks {


    private static final ActivityLifecycleManager activityLifecycleManager = new ActivityLifecycleManager();


    static void init(Application application) {
        application.registerActivityLifecycleCallbacks(get());
    }

    public static ActivityLifecycleManager get() {
        return activityLifecycleManager;
    }

    private final List<Activity> activitieRefrece = new ArrayList<>();

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            activitieRefrece.remove(activity);
        }
        activitieRefrece.add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        activitieRefrece.remove(activity);
    }

    public <T extends Activity> boolean hasActivity(Class<T> clazz) {
        for (int i = 0; i < activitieRefrece.size(); i++) {
            if (activitieRefrece.get(i).getClass() == clazz) {
                return true;
            }
        }
        return false;
    }

    public void finishs(Class<?>... activitiesclass) {
        for (Class<?> clazz : activitiesclass) {
            for (int i1 = 0; i1 < activitieRefrece.size(); i1++) {
                Activity activity = activitieRefrece.get(i1);
                if (Objects.equals(activity.getClass().getCanonicalName(), clazz.getCanonicalName())) {
                    activity.finish();
                }
            }
        }
    }


}
