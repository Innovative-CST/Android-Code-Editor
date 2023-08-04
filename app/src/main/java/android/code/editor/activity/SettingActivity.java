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

package android.code.editor.activity;

import android.code.editor.R;
import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.utils.RadioOptionChooser;
import android.code.editor.utils.Setting;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.color.DynamicColors;
import editor.tsd.tools.Themes;
import java.util.ArrayList;

public class SettingActivity extends BaseActivity {
  ArrayList<String> data = new ArrayList<>();
  ArrayList<String> uiMode = new ArrayList<>();
  ArrayList<String> aceEditorThemes = new ArrayList<>();
  ArrayList<String> soraEditorThemes = new ArrayList<>();

  public Spinner aceEditorThemeChooser;
  public Spinner soraEditorThemeChooser;
  public Spinner themeChooser;

  @Override
  protected void onCreate(Bundle arg0) {
    super.onCreate(arg0);
    setContentView(R.layout.activity_setting);
    initActivity();
  }

  public void initActivity() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle("Settings");
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View arg0) {
            onBackPressed();
          }
        });

    initThemeList();

    aceEditorThemeChooser = findViewById(R.id.aceEditorThemeChooser);
    soraEditorThemeChooser = findViewById(R.id.soraEditorThemeChooser);
    themeChooser = findViewById(R.id.themeChooser);

    findViewById(R.id.editorChooserContainer)
        .setOnClickListener(
            (view) -> {
              new RadioOptionChooser(
                  this, R.layout.layout_editor_chooser_radio_group, "Choose an editor");
            });

    uiMode.add("Light");
    uiMode.add("Dark");
    uiMode.add("Follow System");

    themeChooser.setAdapter(new themeChooserAdapter(uiMode));
    themeChooser.setSelection(getThemeTypeInInt(SettingActivity.this));

    data.add("Ace Code Editor");
    data.add("Sora Code Editor");

    initEditorThemeList();

    themeChooser.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(
              AdapterView<?> _param1, View _param2, int _param3, long _param4) {
            if (_param3 == 0) {
              int theme = getThemeTypeInInt(SettingActivity.this);
              Setting.SaveInFile.setSetting(
                  Setting.Key.ThemeType, Setting.Value.Light, SettingActivity.this);
              if (!(theme == _param3)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
              }
            } else if (_param3 == 1) {
              int theme = getThemeTypeInInt(SettingActivity.this);
              Setting.SaveInFile.setSetting(
                  Setting.Key.ThemeType, Setting.Value.Dark, SettingActivity.this);
              if (!(theme == _param3)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
              }
            } else if (_param3 == 2) {
              int theme = getThemeTypeInInt(SettingActivity.this);
              Setting.SaveInFile.setSetting(
                  Setting.Key.ThemeType, Setting.Value.System, SettingActivity.this);
              if (!(theme == _param3)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
              }
            }
          }

          @Override
          public void onNothingSelected(AdapterView<?> _param1) {}
        });
  }

  public void initThemeList() {
    if (!DynamicColors.isDynamicColorAvailable()) {
      View theme1 = getLayoutInflater().inflate(R.layout.theme_chooser_item, null);
      theme1.findViewById(R.id.color).setBackgroundResource(R.color.theme_1_md_theme_light_primary);
      ((ViewGroup) findViewById(R.id.themes)).addView(theme1);
      theme1
          .findViewById(R.id.color)
          .setOnClickListener(
              (view) -> {
                Setting.SaveInFile.setSetting(
                    Setting.Key.Theme, MaterialColorHelper.AppTheme, this);
                refreshThemeColorStatusIfRequired();
              });

      View theme2 = getLayoutInflater().inflate(R.layout.theme_chooser_item, null);
      theme2.findViewById(R.id.color).setBackgroundResource(R.color.theme_2_md_theme_light_primary);
      ((ViewGroup) findViewById(R.id.themes)).addView(theme2);
      theme2
          .findViewById(R.id.color)
          .setOnClickListener(
              (view) -> {
                Setting.SaveInFile.setSetting(
                    Setting.Key.Theme, MaterialColorHelper.AppTheme2, this);
                refreshThemeColorStatusIfRequired();
              });

      View theme3 = getLayoutInflater().inflate(R.layout.theme_chooser_item, null);
      theme3.findViewById(R.id.color).setBackgroundResource(R.color.theme_3_md_theme_light_primary);
      ((ViewGroup) findViewById(R.id.themes)).addView(theme3);
      theme3
          .findViewById(R.id.color)
          .setOnClickListener(
              (view) -> {
                Setting.SaveInFile.setSetting(
                    Setting.Key.Theme, MaterialColorHelper.AppTheme3, this);
                refreshThemeColorStatusIfRequired();
              });

      View theme4 = getLayoutInflater().inflate(R.layout.theme_chooser_item, null);
      theme4.findViewById(R.id.color).setBackgroundResource(R.color.theme_4_md_theme_light_primary);
      ((ViewGroup) findViewById(R.id.themes)).addView(theme4);
      theme4
          .findViewById(R.id.color)
          .setOnClickListener(
              (view) -> {
                Setting.SaveInFile.setSetting(
                    Setting.Key.Theme, MaterialColorHelper.AppTheme4, this);
                refreshThemeColorStatusIfRequired();
              });

      View theme5 = getLayoutInflater().inflate(R.layout.theme_chooser_item, null);
      theme5.findViewById(R.id.color).setBackgroundResource(R.color.theme_5_md_theme_light_primary);
      ((ViewGroup) findViewById(R.id.themes)).addView(theme5);
      theme5
          .findViewById(R.id.color)
          .setOnClickListener(
              (view) -> {
                Setting.SaveInFile.setSetting(
                    Setting.Key.Theme, MaterialColorHelper.AppTheme5, this);
                refreshThemeColorStatusIfRequired();
              });
    }
  }

  public static int getThemeTypeInInt(Context context) {
    int val = 0;
    switch (Setting.SaveInFile.getSettingString(
        Setting.Key.ThemeType, Setting.Default.ThemeType, context)) {
      case Setting.Value.Light:
        val = 0;
        break;
      case Setting.Value.Dark:
        val = 1;
        break;
      case Setting.Value.System:
        val = 2;
        break;
    }
    return val;
  }

  public void initEditorThemeList() {
    aceEditorThemes.clear();
    soraEditorThemes.clear();
    if (Setting.SaveInFile.getSettingString(Setting.Key.ThemeType, Setting.Default.ThemeType, this)
        .equals(Setting.Value.Dark)) {
      aceEditorThemes.addAll(new Themes().new AceEditorTheme().new Dark().getThemes());

      soraEditorThemes.addAll(new Themes().new SoraEditorTheme().new Dark().getThemes());
    } else if (Setting.SaveInFile.getSettingString(
            Setting.Key.ThemeType, Setting.Default.ThemeType, this)
        .equals(Setting.Value.Light)) {
      aceEditorThemes.addAll(new Themes().new AceEditorTheme().new Light().getThemes());

      soraEditorThemes.addAll(new Themes().new SoraEditorTheme().new Light().getThemes());
    } else if (Setting.SaveInFile.getSettingString(
            Setting.Key.ThemeType, Setting.Default.ThemeType, this)
        .equals(Setting.Value.System)) {
      int nightModeFlags =
          this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
      switch (nightModeFlags) {
        case Configuration.UI_MODE_NIGHT_YES:
          aceEditorThemes.addAll(new Themes().new AceEditorTheme().new Dark().getThemes());

          soraEditorThemes.addAll(new Themes().new SoraEditorTheme().new Dark().getThemes());
          break;
        case Configuration.UI_MODE_NIGHT_NO:
          aceEditorThemes.addAll(new Themes().new AceEditorTheme().new Light().getThemes());

          soraEditorThemes.addAll(new Themes().new SoraEditorTheme().new Light().getThemes());
          break;
      }
    }
    aceEditorThemeChooser.setAdapter(new editorThemeChooserAdapter(aceEditorThemes));
    soraEditorThemeChooser.setAdapter(new editorThemeChooserAdapter(soraEditorThemes));

    aceEditorThemeChooser.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            if (Setting.SaveInFile.getSettingString(
                    Setting.Key.ThemeType, Setting.Default.ThemeType, SettingActivity.this)
                .equals(Setting.Value.Dark)) {
              Setting.SaveInFile.setSetting(
                  Setting.Key.AceCodeEditorDarkThemeSelectionPosition, arg2, SettingActivity.this);
              Setting.SaveInFile.setSetting(
                  Setting.Key.AceCodeEditorDarkTheme,
                  aceEditorThemes.get(arg2),
                  SettingActivity.this);
            } else if (Setting.SaveInFile.getSettingString(
                    Setting.Key.ThemeType, Setting.Default.ThemeType, SettingActivity.this)
                .equals(Setting.Value.Light)) {
              Setting.SaveInFile.setSetting(
                  Setting.Key.AceCodeEditorLightThemeSelectionPosition, arg2, SettingActivity.this);
              Setting.SaveInFile.setSetting(
                  Setting.Key.AceCodeEditorLightTheme,
                  aceEditorThemes.get(arg2),
                  SettingActivity.this);
            } else if (Setting.SaveInFile.getSettingString(
                    Setting.Key.ThemeType, Setting.Default.ThemeType, SettingActivity.this)
                .equals(Setting.Value.System)) {
              int nightModeFlags =
                  SettingActivity.this.getResources().getConfiguration().uiMode
                      & Configuration.UI_MODE_NIGHT_MASK;
              switch (nightModeFlags) {
                case Configuration.UI_MODE_NIGHT_YES:
                  Setting.SaveInFile.setSetting(
                      Setting.Key.AceCodeEditorDarkThemeSelectionPosition,
                      arg2,
                      SettingActivity.this);
                  Setting.SaveInFile.setSetting(
                      Setting.Key.AceCodeEditorDarkTheme,
                      aceEditorThemes.get(arg2),
                      SettingActivity.this);
                  break;
                case Configuration.UI_MODE_NIGHT_NO:
                  Setting.SaveInFile.setSetting(
                      Setting.Key.AceCodeEditorLightThemeSelectionPosition,
                      arg2,
                      SettingActivity.this);
                  Setting.SaveInFile.setSetting(
                      Setting.Key.AceCodeEditorLightTheme,
                      aceEditorThemes.get(arg2),
                      SettingActivity.this);
                  break;
              }
            }
          }

          @Override
          public void onNothingSelected(AdapterView<?> arg0) {}
        });

    soraEditorThemeChooser.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            if (Setting.SaveInFile.getSettingString(
                    Setting.Key.ThemeType, Setting.Default.ThemeType, SettingActivity.this)
                .equals(Setting.Value.Dark)) {
              Setting.SaveInFile.setSetting(
                  Setting.Key.SoraCodeEditorDarkThemeSelectionPosition, arg2, SettingActivity.this);
              Setting.SaveInFile.setSetting(
                  Setting.Key.SoraCodeEditorDarkTheme,
                  soraEditorThemes.get(arg2).toString(),
                  SettingActivity.this);
            } else if (Setting.SaveInFile.getSettingString(
                    Setting.Key.ThemeType, Setting.Default.ThemeType, SettingActivity.this)
                .equals(Setting.Value.Light)) {
              Setting.SaveInFile.setSetting(
                  Setting.Key.SoraCodeEditorLightThemeSelectionPosition,
                  arg2,
                  SettingActivity.this);
              Setting.SaveInFile.setSetting(
                  Setting.Key.SoraCodeEditorLightTheme,
                  soraEditorThemes.get(arg2).toString(),
                  SettingActivity.this);
            } else if (Setting.SaveInFile.getSettingString(
                    Setting.Key.ThemeType, Setting.Default.ThemeType, SettingActivity.this)
                .equals(Setting.Value.System)) {
              int nightModeFlags =
                  SettingActivity.this.getResources().getConfiguration().uiMode
                      & Configuration.UI_MODE_NIGHT_MASK;
              switch (nightModeFlags) {
                case Configuration.UI_MODE_NIGHT_YES:
                  Setting.SaveInFile.setSetting(
                      Setting.Key.SoraCodeEditorDarkThemeSelectionPosition,
                      arg2,
                      SettingActivity.this);
                  Setting.SaveInFile.setSetting(
                      Setting.Key.SoraCodeEditorDarkTheme,
                      soraEditorThemes.get(arg2).toString(),
                      SettingActivity.this);
                  break;
                case Configuration.UI_MODE_NIGHT_NO:
                  Setting.SaveInFile.setSetting(
                      Setting.Key.SoraCodeEditorLightThemeSelectionPosition,
                      arg2,
                      SettingActivity.this);
                  Setting.SaveInFile.setSetting(
                      Setting.Key.SoraCodeEditorLightTheme,
                      soraEditorThemes.get(arg2).toString(),
                      SettingActivity.this);
                  break;
              }
            }
          }

          @Override
          public void onNothingSelected(AdapterView<?> arg0) {}
        });
    if (Setting.SaveInFile.getSettingString(
            Setting.Key.ThemeType, Setting.Default.ThemeType, SettingActivity.this)
        .equals(Setting.Value.Dark)) {
      aceEditorThemeChooser.setSelection(
          Setting.SaveInFile.getSettingInt(
              Setting.Key.AceCodeEditorDarkThemeSelectionPosition,
              Setting.Default.AceCodeEditorDarkThemeSelectionPosition,
              this));
    } else if (Setting.SaveInFile.getSettingString(
            Setting.Key.ThemeType, Setting.Default.ThemeType, SettingActivity.this)
        .equals(Setting.Value.Light)) {
      aceEditorThemeChooser.setSelection(
          Setting.SaveInFile.getSettingInt(
              Setting.Key.AceCodeEditorLightThemeSelectionPosition,
              Setting.Default.AceCodeEditorLightThemeSelectionPosition,
              this));
    } else if (Setting.SaveInFile.getSettingString(
            Setting.Key.ThemeType, Setting.Default.ThemeType, SettingActivity.this)
        .equals(Setting.Value.System)) {
      int nightModeFlags =
          SettingActivity.this.getResources().getConfiguration().uiMode
              & Configuration.UI_MODE_NIGHT_MASK;
      switch (nightModeFlags) {
        case Configuration.UI_MODE_NIGHT_YES:
          aceEditorThemeChooser.setSelection(
              Setting.SaveInFile.getSettingInt(
                  Setting.Key.AceCodeEditorDarkThemeSelectionPosition,
                  Setting.Default.AceCodeEditorDarkThemeSelectionPosition,
                  this));
          break;
        case Configuration.UI_MODE_NIGHT_NO:
          aceEditorThemeChooser.setSelection(
              Setting.SaveInFile.getSettingInt(
                  Setting.Key.AceCodeEditorLightThemeSelectionPosition,
                  Setting.Default.AceCodeEditorLightThemeSelectionPosition,
                  this));
          break;
      }
    }

    if (Setting.SaveInFile.getSettingString(
            Setting.Key.ThemeType, Setting.Default.ThemeType, SettingActivity.this)
        .equals(Setting.Value.Dark)) {
      soraEditorThemeChooser.setSelection(
          Setting.SaveInFile.getSettingInt(
              Setting.Key.SoraCodeEditorDarkThemeSelectionPosition,
              Setting.Default.SoraCodeEditorDarkThemeSelectionPosition,
              this));
    } else if (Setting.SaveInFile.getSettingString(
            Setting.Key.ThemeType, Setting.Default.ThemeType, SettingActivity.this)
        .equals(Setting.Value.Light)) {
      soraEditorThemeChooser.setSelection(
          Setting.SaveInFile.getSettingInt(
              Setting.Key.SoraCodeEditorLightThemeSelectionPosition,
              Setting.Default.SoraCodeEditorLightThemeSelectionPosition,
              this));
    } else if (Setting.SaveInFile.getSettingString(
            Setting.Key.ThemeType, Setting.Default.ThemeType, SettingActivity.this)
        .equals(Setting.Value.System)) {
      int nightModeFlags =
          SettingActivity.this.getResources().getConfiguration().uiMode
              & Configuration.UI_MODE_NIGHT_MASK;
      switch (nightModeFlags) {
        case Configuration.UI_MODE_NIGHT_YES:
          soraEditorThemeChooser.setSelection(
              Setting.SaveInFile.getSettingInt(
                  Setting.Key.SoraCodeEditorDarkThemeSelectionPosition,
                  Setting.Default.SoraCodeEditorDarkThemeSelectionPosition,
                  this));
          break;
        case Configuration.UI_MODE_NIGHT_NO:
          soraEditorThemeChooser.setSelection(
              Setting.SaveInFile.getSettingInt(
                  Setting.Key.SoraCodeEditorLightThemeSelectionPosition,
                  Setting.Default.SoraCodeEditorLightThemeSelectionPosition,
                  this));
          break;
      }
    }
  }

  public class themeChooserAdapter extends BaseAdapter {
    public ArrayList<String> data;
    public TextView textView;

    public themeChooserAdapter(ArrayList<String> data) {
      this.data = data;
    }

    @Override
    public String getItem(int _index) {
      return data.get(_index);
    }

    @Override
    public int getCount() {
      return data.size();
    }

    @Override
    public long getItemId(int arg0) {
      return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
      LayoutInflater _inflater = getLayoutInflater();
      View _view = arg1;
      if (_view == null) {
        _view = _inflater.inflate(R.layout.one_line_item, null);
      }
      textView = _view.findViewById(R.id.item);
      textView.setText(data.get(arg0));

      return _view;
    }
  }

  public class editorThemeChooserAdapter extends BaseAdapter {
    public ArrayList<String> data;
    public TextView textView;

    public editorThemeChooserAdapter(ArrayList<String> data) {
      this.data = data;
    }

    @Override
    public String getItem(int _index) {
      return data.get(_index);
    }

    @Override
    public int getCount() {
      return data.size();
    }

    @Override
    public long getItemId(int arg0) {
      return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
      LayoutInflater _inflater = getLayoutInflater();
      View _view = arg1;
      if (_view == null) {
        _view = _inflater.inflate(R.layout.one_line_item, null);
      }
      textView = _view.findViewById(R.id.item);
      textView.setText(data.get(arg0));

      return _view;
    }
  }
}
