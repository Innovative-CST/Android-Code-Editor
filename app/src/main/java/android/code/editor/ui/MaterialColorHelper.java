/*
 *  This file is part of Android Code Editor.
 *
 *  Android Code Editor is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Android Code Editor is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package android.code.editor.ui;

import android.code.editor.R;
import android.code.editor.utils.Setting;
import android.content.Context;

public class MaterialColorHelper {
  public static final String AppTheme = "AppTheme";
  public static final String AppTheme2 = "AppTheme2";
  public static final String AppTheme3 = "AppTheme3";
  public static final String AppTheme4 = "AppTheme4";
  public static final String AppTheme5 = "AppTheme5";

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
      case AppTheme5:
        theme = R.style.AppTheme5;
        break;
      default:
        return R.style.AppTheme;
    }
    return theme;
  }
}
