package android.code.editor;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.code.editor.activity.DebugActivity;
import android.code.editor.activity.SettingActivity;
import android.code.editor.utils.Setting;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.color.DynamicColors;

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static Context mApplicationContext;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public static Context getContext() {
        return mApplicationContext;
    }

    @Override
    public void onCreate() {

        mApplicationContext = getApplicationContext();

        DynamicColors.applyToActivitiesIfAvailable(this);

        if (!Setting.SaveInFile.getSettingString(
                        Setting.Key.NewTheme, Setting.Default.NewTheme, this)
                .equals(
                        Setting.SaveInFile.getSettingString(
                                Setting.Key.Theme, Setting.Default.Theme, this))) {
            Setting.SaveInFile.setSetting(
                    Setting.Key.Theme,
                    Setting.SaveInFile.getSettingString(
                            Setting.Key.NewTheme, Setting.Default.NewTheme, this),
                    this);
            Setting.SaveInFile.setSetting(
                    Setting.Key.ThemeType,
                    Setting.SaveInFile.getSettingString(
                            Setting.Key.NewThemeType, Setting.Default.NewThemeType, this),
                    this);
        }

        this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread thread, Throwable throwable) {
                        Intent intent = new Intent(getApplicationContext(), DebugActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("error", Log.getStackTraceString(throwable));
                        PendingIntent pendingIntent =
                                PendingIntent.getActivity(
                                        getApplicationContext(),
                                        11111,
                                        intent,
                                        PendingIntent.FLAG_ONE_SHOT);

                        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, pendingIntent);

                        Process.killProcess(Process.myPid());
                        System.exit(1);

                        uncaughtExceptionHandler.uncaughtException(thread, throwable);
                    }
                });
        super.onCreate();
    }

    @Override
    public void onActivityCreated(Activity arg0, Bundle arg1) {}

    @Override
    public void onActivityStarted(Activity arg0) {}

    @Override
    public void onActivityResumed(Activity arg0) {
        switch (SettingActivity.getThemeTypeInInt(arg0)) {
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    @Override
    public void onActivityPaused(Activity arg0) {
    }

    @Override
    public void onActivityStopped(Activity arg0) {}

    @Override
    public void onActivitySaveInstanceState(Activity arg0, Bundle arg1) {}

    @Override
    public void onActivityDestroyed(Activity arg0) {}
}
