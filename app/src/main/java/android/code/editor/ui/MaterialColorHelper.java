package android.code.editor.ui;

import android.content.Context;
import com.google.android.material.color.MaterialColors;

public class MaterialColorHelper {
	public static String getMaterialColor(Context context,int res){
		return String.format("#%08X", (0xFFFFFFFF & MaterialColors.getColor(context,res,"Passed color in parameter doesn't exists.")));
	}
	public static int getMaterialColorInt(Context context,int res){
		return MaterialColors.getColor(context,res,"Passed color in parameter doesn't exists.");
	}
	
	public static String setColorTransparency(String hex,String transparency){
		return "#".concat(transparency).concat(hex.substring((int)(3), (int)(hex.length())));
	}
	
}
