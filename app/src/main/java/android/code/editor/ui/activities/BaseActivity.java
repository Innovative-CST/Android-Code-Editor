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

package android.code.editor.ui.activities;

import android.code.editor.utils.Languages;
import android.code.editor.utils.MaterialColorHelper;
import android.code.editor.utils.Setting;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.color.DynamicColors;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
  public int theme;
  public String language;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!DynamicColors.isDynamicColorAvailable()) {
      theme = MaterialColorHelper.getCurrentTheme(this);
      MaterialColorHelper.setUpTheme(this);
    }
    language = getAppliedLanguage();
    Log.e("BaseActivity", language);
    refreshThemeStatusIfRequired();
    setLangugeMode();
    if (language.equals(Languages.Persian)) {
      getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    refreshThemeStatusIfRequired();
    setLangugeMode();
    refreshActivityIfRequired();
  }

  public void refreshThemeStatusIfRequired() {
    switch (SettingActivity.getThemeTypeInInt(this)) {
      case 0:
        if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO) {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        break;
      case 1:
        if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES) {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        break;
      case 2:
        if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        break;
    }
  }

  public void refreshActivityIfRequired() {
    if (!(language.equals(getAppliedLanguage()))
        || (!DynamicColors.isDynamicColorAvailable()
            && MaterialColorHelper.getCurrentTheme(this) != theme)) {
      recreate();
    }
  }

  public void setLangugeMode() {
    updateAppLanguage(
        Setting.SaveInFile.getSettingString(Setting.Key.Language, Setting.Default.Language, this));
  }

  public void updateAppLanguage(String languageCode) {
    Locale locale = new Locale(languageCode);
    Resources resources = getResources();
    Configuration configuration = resources.getConfiguration();
    configuration.setLocale(locale);
    resources.updateConfiguration(configuration, resources.getDisplayMetrics());
  }

  public String getAppliedLanguage() {
    Configuration config = getResources().getConfiguration();
    Locale currentLocale = config.locale;
    String currentLanguage = currentLocale.getLanguage();
    return currentLanguage;
  }
}
