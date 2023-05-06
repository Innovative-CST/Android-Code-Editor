package android.code.editor.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.view.View;

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
}
