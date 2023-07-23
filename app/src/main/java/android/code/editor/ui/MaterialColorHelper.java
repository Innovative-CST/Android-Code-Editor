package android.code.editor.ui;

import android.code.editor.R;
import android.code.editor.utils.Setting;
import android.content.Context;

public class MaterialColorHelper {
  public static final String AppTheme = "AppTheme";
  public static final String AppTheme2 = "AppTheme2";
  public static final String AppTheme3 = "AppTheme3";
  public static final String AppTheme4 = "AppTheme4";

  public static void setUpTheme(Context context) {
    context.setTheme(getCurrentTheme(context));
  }

  public static int getCurrentTheme(Context context) {
    int theme;
    switch (Setting.SaveInFile.getSettingString(
        Setting.Key.Theme, Setting.Default.Theme, context)) {
      case AppTheme:
        theme = R.style.AppTheme;
        break;
      case AppTheme2:
        theme = R.style.AppTheme2;
        break;
      case AppTheme3:
        theme = R.style.AppTheme3;
        break;
      case AppTheme4:
        theme = R.style.AppTheme4;
        break;
      default:
        return R.style.AppTheme;
    }
    return theme;
  }
}
