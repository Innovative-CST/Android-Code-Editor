package android.code.editor.common.utils;

import android.content.Context;
import com.google.android.material.color.MaterialColors;

public class ColorUtils {
  public static String materialIntToHexColor(Context context, int res) {
    return String.format("#%06X", (0xFFFFFF & MaterialColors.getColor(context, res, "#000000")));
  }
}
