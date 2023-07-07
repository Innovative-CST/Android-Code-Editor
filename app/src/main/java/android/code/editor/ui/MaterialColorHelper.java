package android.code.editor.ui;

import android.code.editor.R;
import android.code.editor.utils.Setting;
import android.content.Context;

public class MaterialColorHelper {
    public static void setUpTheme(Context context) {
        context.setTheme(getCurrentTheme(context));
    }

    public static int getCurrentTheme(Context context) {
        int theme;
        switch (Setting.SaveInFile.getSettingString(
                Setting.Key.Theme, Setting.Default.Theme, context)) {
            case "BrownishLight":
                theme = R.style.BrownishLight;
                break;
            case "BrownishDark":
                theme = R.style.BrownishDark;
                break;
            case "LightBlueLight":
                theme = R.style.LightBlueLight;
                break;
            case "LightBlueDark":
                theme = R.style.LightBlueDark;
                break;
            case "LightGreen":
                theme = R.style.GreenLight;
                break;
            case "DarkGreen":
                theme = R.style.GreenDark;
                break;
            default:
                return R.style.GreenLight;
        }
        return theme;
    }
}
