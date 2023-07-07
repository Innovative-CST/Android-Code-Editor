package android.code.editor.ui;

import android.code.editor.R;
import android.code.editor.utils.Setting;
import android.content.Context;

public class MaterialColorHelper {
    public static void setUpTheme(Context context) {
        switch (Setting.SaveInFile.getSettingString(Setting.Key.Theme, Setting.Default.Theme, context)) {
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
