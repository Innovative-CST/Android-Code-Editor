package android.code.editor;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.code.editor.utils.ThemeObservable;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

public class MyApplication extends Application {
    private static Context mApplicationContext;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
	public ThemeObservable themeObservable = new ThemeObservable();

    public static Context getContext() {
        return mApplicationContext;
    }
	
	public ThemeObservable getThemeObservable() {
		return themeObservable;
	}

    @Override
    public void onCreate() {
		
        mApplicationContext = getApplicationContext();
		
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
        // TODO: Implement this method
    }
}
