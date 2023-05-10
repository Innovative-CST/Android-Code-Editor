package android.code.editor.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Utils {
	public static void applyRippleEffect(View view,String focus,String ripple) {
		GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor(focus));
        view.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor(ripple)}), gradientDrawable, null));
	}
	
	public static void applyRippleEffect(View view,int focus,int ripple) {
		GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(focus);
        view.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{ripple}), gradientDrawable, null));
	}
	public static void StatusBarColor(final int _color,final Activity activity) {
		//stutus bar
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			Window w = activity.getWindow();
			w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); 
			w.setStatusBarColor(_color);
		}
	}
}
