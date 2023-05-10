package android.code.editor.ui;

import android.code.editor.R;
import android.code.editor.utils.Setting;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.material.color.MaterialColors;

public class MaterialColorHelper {
    public static String getMaterialColor(Context context, int res) {
        return String.format(
                "#%08X",
                (0xFFFFFFFF
                        & MaterialColors.getColor(
                                context, res, "Passed color in parameter doesn't exists.")));
    }

    public static int getMaterialColorInt(Context context, int res) {
        return MaterialColors.getColor(context, res, "Passed color in parameter doesn't exists.");
    }

    public static String setColorTransparency(String hex, String transparency) {
        return "#".concat(transparency).concat(hex.substring(3, hex.length()));
    }

    public static String setColorTransparency(int hex, String transparency) {
        return "#"
                .concat(transparency)
                .concat(intColorToString(hex).substring(3, intColorToString(hex).length()));
    }

    public static String intColorToString(int color) {
        return String.format("#%08X", (0xFFFFFFFF & color));
    }

    public static void setUpTheme(Context context) {
        switch (Setting.SaveInFile.getSettingString(Setting.Key.Theme, "BrownishLight", context)) {
            case "BrownishLight":
                context.setTheme(R.style.BrownishLight);
                break;
            case "BrownishDark":
                context.setTheme(R.style.BrownishDark);
                break;
            case "LightBlueLight":
                context.setTheme(R.style.LightBlueLight);
                break;
            case "LightBlueDark":
                context.setTheme(R.style.LightBlueDark);
                break;
            case "LightGreen":
                context.setTheme(R.style.GreenLight);
                break;
            case "DarkGreen":
                context.setTheme(R.style.GreenDark);
                break;
        }
    }
}
