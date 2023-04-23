package android.code.editor.files.utils;

import android.code.editor.R;
import android.code.editor.ui.MaterialColorHelper;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.ImageView;
import java.io.File;

public class FileIcon {
	public static void setUpIcon(Context context,String path,ImageView imageview) {
		if(new File(path).isDirectory()) {
			imageview.setImageResource(R.drawable.ic_folder_black_24dp);
			/**
			imageview.setImageTintList(new ColorStateList(
				new int[][]{new int[]{0}},
            	new int[]{Color.parseColor(MaterialColorHelper.getMaterialColor(context,R.attr.colorPrimary))}
			));
			*/
		} else if (new File(path).isFile()) {
			imageview.setImageResource(R.drawable.java_logo);
			/**
			imageview.setImageTintList(new ColorStateList(
				new int[][]{new int[]{0}},
            	new int[]{Color.parseColor("#64b5f6")}
			));*/
		}
	}
}
