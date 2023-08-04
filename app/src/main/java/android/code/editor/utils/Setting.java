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

package android.code.editor.utils;

import android.code.editor.files.utils.FileManager;
import android.code.editor.ui.MaterialColorHelper;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;
import java.io.File;
import java.util.HashMap;

public class Setting {
  public static String SettingFilePath;
  public static String settingData;
  private static HashMap<String, Object> setting_map = new HashMap<>();

  public class Key {
    public static final String CodeEditor = "CodeEditor";
    public static final String Theme = "Theme";
    public static final String NewTheme = "NewTheme";
    public static final String ThemeType = "ThemeType";
    public static final String NewThemeType = "NewThemeType";
    public static final String AceCodeEditorLightTheme = "AceCodeEditorLightTheme";
    public static final String AceCodeEditorDarkTheme = "AceCodeEditorDarkTheme";
    public static final String SoraCodeEditorLightTheme = "SoraCodeEditorLightTheme";
    public static final String SoraCodeEditorDarkTheme = "SoraCodeEditorDarkTheme";
    public static final String AceCodeEditorLightThemeSelectionPosition =
        "AceCodeEditorLightThemeSelectionPosition";
    public static final String AceCodeEditorDarkThemeSelectionPosition =
        "AceCodeEditorDarkThemeSelectionPosition";
    public static final String SoraCodeEditorLightThemeSelectionPosition =
        "SoraCodeEditorLightThemeSelectionPosition";
    public static final String SoraCodeEditorDarkThemeSelectionPosition =
        "SoraCodeEditorDarkThemeSelectionPosition";
  }

  public class Default {
    public static final int CodeEditor = CodeEditorLayout.AceCodeEditor;
    public static final String Theme = MaterialColorHelper.AppTheme;
    public static final String ThemeType = Value.Light;
    public static final String NewThemeType = ThemeType;
    public static final String AceCodeEditorLightTheme = Themes.AceEditorTheme.Light.Default;
    public static final String AceCodeEditorDarkTheme = Themes.AceEditorTheme.Dark.Default;
    public static final String SoraCodeEditorLightTheme = Themes.SoraEditorTheme.Light.Default;
    public static final String SoraCodeEditorDarkTheme = Themes.SoraEditorTheme.Dark.Default;
    public static final int AceCodeEditorLightThemeSelectionPosition = 0;
    public static final int AceCodeEditorDarkThemeSelectionPosition = 1;
    public static final int SoraCodeEditorLightThemeSelectionPosition = 0;
    public static final int SoraCodeEditorDarkThemeSelectionPosition = 1;
  }

  public class Value {
    public static final String Dark = "Dark";
    public static final String Light = "Light";
    public static final String System = "System";
  }

  public class SaveInFile {
    public static void setSetting(String key, Object value, Context context) {
      SettingFilePath = getDataDir(context).concat(File.separator).concat("Settings.json");
      initSettingFile(SettingFilePath);
      settingData = FileManager.readFile(SettingFilePath);
      try {
        TypeToken<HashMap<String, Object>> tokentype = new TypeToken<>() {};
        setting_map = new Gson().fromJson(settingData, tokentype);
        if (setting_map != null) {
          setting_map.put(key, value);
          FileManager.writeFile(SettingFilePath, new Gson().toJson(setting_map));
        } else {
          setting_map = new HashMap<>();
          setting_map.put(key, value);
          FileManager.writeFile(SettingFilePath, new Gson().toJson(setting_map));
        }
      } catch (JsonParseException e) {

      }
    }

    public static void setSetting(String key, int value, Context context) {
      SettingFilePath = getDataDir(context).concat(File.separator).concat("Settings.json");
      initSettingFile(SettingFilePath);
      settingData = FileManager.readFile(SettingFilePath);
      try {
        TypeToken<HashMap<String, Object>> tokentype = new TypeToken<>() {};
        setting_map = new Gson().fromJson(settingData, tokentype);
        if (setting_map == null) {
          setting_map = new HashMap<>();
        }
        setting_map.put(key, String.valueOf(value));
        FileManager.writeFile(SettingFilePath, new Gson().toJson(setting_map));
      } catch (JsonParseException e) {

      }
    }

    public static String getSettingString(String key, String defaultValue, Context context) {
      SettingFilePath = getDataDir(context).concat(File.separator).concat("Settings.json");
      initSettingFile(SettingFilePath);
      settingData = FileManager.readFile(SettingFilePath);
      try {
        TypeToken<HashMap<String, Object>> tokentype = new TypeToken<>() {};
        setting_map = new Gson().fromJson(settingData, tokentype);
        if (setting_map != null) {
          if (setting_map.containsKey(key)) {
            return setting_map.get(key).toString();
          } else {
            return defaultValue;
          }
        } else {
          return defaultValue;
        }
      } catch (JsonParseException e) {
        return defaultValue;
      }
    }

    public static int getSettingInt(String key, int defaultValue, Context context) {
      SettingFilePath = getDataDir(context).concat(File.separator).concat("Settings.json");
      initSettingFile(SettingFilePath);
      settingData = FileManager.readFile(SettingFilePath);
      try {
        TypeToken<HashMap<String, Object>> tokentype = new TypeToken<>() {};
        setting_map = new Gson().fromJson(settingData, tokentype);
        if (setting_map != null) {
          if (setting_map.containsKey(key)) {
            return Integer.parseInt(setting_map.get(key).toString());
          } else {
            return defaultValue;
          }
        } else {
          return defaultValue;
        }
      } catch (JsonParseException e) {
        return defaultValue;
      }
    }

    public static String getDataDir(Context context) {
      PackageManager pm = context.getPackageManager();
      String packageName = context.getPackageName();
      PackageInfo packageInfo;
      try {
        packageInfo = pm.getPackageInfo(packageName, 0);
        return packageInfo.applicationInfo.dataDir;
      } catch (PackageManager.NameNotFoundException e) {
        return "";
      }
    }

    public static void initSettingFile(String path) {
      if (!new File(path).exists()) {
        FileManager.createNewFile(path);
      }
    }
  }

  public class SaveInSharedPreferences {}
}
