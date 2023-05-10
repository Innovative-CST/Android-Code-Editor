package android.code.editor.utils;

import android.app.Activity;
import android.app.Application;
import android.code.editor.files.utils.FileManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import editor.tsd.widget.CodeEditorLayout;
import java.io.File;
import java.util.HashMap;

public class Setting {
    public static String SettingFilePath;
    public static String settingData;
    private static HashMap<String, Object> setting_map = new HashMap<>();

    public class Key {
        public static String CodeEditor = "CodeEditor";
        public static String Theme = "Theme";
    }

    public class Default {
        public static int CodeEditor = CodeEditorLayout.SoraCodeEditor;
        public static String Theme = "LightGreen";
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
	
	public class SaveInSharedPreferences {
		
	}
}
