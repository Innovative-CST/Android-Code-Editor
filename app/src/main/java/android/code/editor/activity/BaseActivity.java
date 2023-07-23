package android.code.editor.activity;

import android.code.editor.ui.MaterialColorHelper;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.color.DynamicColors;

public class BaseActivity extends AppCompatActivity {
  public int theme;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!DynamicColors.isDynamicColorAvailable()) {
      theme = MaterialColorHelper.getCurrentTheme(this);
      MaterialColorHelper.setUpTheme(this);
    }
    refreshThemeStatusIfRequired();
  }

  @Override
  protected void onResume() {
    super.onResume();
    refreshThemeColorStatusIfRequired();
    refreshThemeStatusIfRequired();
  }

  public void refreshThemeColorStatusIfRequired() {
    if (!DynamicColors.isDynamicColorAvailable()) {
      if (MaterialColorHelper.getCurrentTheme(this) != theme) {
        recreate();
      }
    }
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
}
