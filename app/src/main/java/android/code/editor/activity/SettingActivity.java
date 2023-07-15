package android.code.editor.activity;

import android.code.editor.R;
import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.utils.Setting;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {
    public Spinner editorChooser;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> uiMode = new ArrayList<>();
    ArrayList<String> aceEditorThemes = new ArrayList<>();
    ArrayList<String> soraEditorThemes = new ArrayList<>();

    public Spinner aceEditorThemeChooser;
    public Spinner soraEditorThemeChooser;
    public Spinner themeChooser;

    @Override
    protected void onCreate(Bundle arg0) {
        MaterialColorHelper.setUpTheme(this);
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
        editorChooser = findViewById(R.id.editorChooser);
        aceEditorThemeChooser = findViewById(R.id.aceEditorThemeChooser);
        soraEditorThemeChooser = findViewById(R.id.soraEditorThemeChooser);
        themeChooser = findViewById(R.id.themeChooser);

        uiMode.add("Light");
        uiMode.add("Dark");

        themeChooser.setAdapter(new themeChooserAdapter(uiMode));
        themeChooser.setSelection(getThemeTypeInInt(SettingActivity.this));

        data.add("Ace Code Editor");
        data.add("Sora Code Editor");

        editorChooser.setAdapter(new editorChooserAdapter(data));
        editorChooser.setSelection(
                Setting.SaveInFile.getSettingInt(
                        Setting.Key.CodeEditor, Setting.Default.CodeEditor, this));
        initEditorThemeList();
        editorChooser.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> _param1, View _param2, int _param3, long _param4) {
                        if (_param3 == 0) {
                            Setting.SaveInFile.setSetting(
                                    Setting.Key.CodeEditor,
                                    CodeEditorLayout.AceCodeEditor,
                                    SettingActivity.this);
                        } else if (_param3 == 1) {
                            Setting.SaveInFile.setSetting(
                                    Setting.Key.CodeEditor,
                                    CodeEditorLayout.SoraCodeEditor,
                                    SettingActivity.this);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> _param1) {}
                });

        themeChooser.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> _param1, View _param2, int _param3, long _param4) {
                        if (_param3 == 0) {
                            int theme = getThemeTypeInInt(SettingActivity.this);
                            Setting.SaveInFile.setSetting(
                                    Setting.Key.ThemeType,
                                    Setting.Value.Light,
                                    SettingActivity.this);
                            if (!(theme == _param3)) {
                                AppCompatDelegate.setDefaultNightMode(
                                        AppCompatDelegate.MODE_NIGHT_NO);
                            }
                        } else if (_param3 == 1) {
                            int theme = getThemeTypeInInt(SettingActivity.this);
                            Setting.SaveInFile.setSetting(
                                    Setting.Key.ThemeType,
                                    Setting.Value.Dark,
                                    SettingActivity.this);
                            if (!(theme == _param3)) {
                                AppCompatDelegate.setDefaultNightMode(
                                        AppCompatDelegate.MODE_NIGHT_YES);
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> _param1) {}
                });
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
        }
        return val;
    }

    public void initEditorThemeList() {
        aceEditorThemes.clear();
        soraEditorThemes.clear();
        if (Setting.SaveInFile.getSettingString(
                        Setting.Key.ThemeType, Setting.Default.ThemeType, this)
                .equals(Setting.Value.Dark)) {
            aceEditorThemes.addAll(new Themes().new AceEditorTheme().new Dark().getThemes());

            soraEditorThemes.addAll(new Themes().new SoraEditorTheme().new Dark().getThemes());
        } else if (Setting.SaveInFile.getSettingString(
                        Setting.Key.ThemeType, Setting.Default.ThemeType, this)
                .equals(Setting.Value.Light)) {
            aceEditorThemes.addAll(new Themes().new AceEditorTheme().new Light().getThemes());

            soraEditorThemes.addAll(new Themes().new SoraEditorTheme().new Light().getThemes());
        }
        aceEditorThemeChooser.setAdapter(new editorThemeChooserAdapter(aceEditorThemes));
        soraEditorThemeChooser.setAdapter(new editorThemeChooserAdapter(soraEditorThemes));

        aceEditorThemeChooser.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        if (Setting.SaveInFile.getSettingString(
                                        Setting.Key.ThemeType,
                                        Setting.Default.ThemeType,
                                        SettingActivity.this)
                                .equals(Setting.Value.Dark)) {
                            Setting.SaveInFile.setSetting(
                                    Setting.Key.AceCodeEditorDarkThemeSelectionPosition,
                                    arg2,
                                    SettingActivity.this);
                            Setting.SaveInFile.setSetting(
                                    Setting.Key.AceCodeEditorDarkTheme,
                                    aceEditorThemes.get(arg2),
                                    SettingActivity.this);
                        } else if (Setting.SaveInFile.getSettingString(
                                        Setting.Key.ThemeType,
                                        Setting.Default.ThemeType,
                                        SettingActivity.this)
                                .equals(Setting.Value.Light)) {
                            Setting.SaveInFile.setSetting(
                                    Setting.Key.AceCodeEditorLightThemeSelectionPosition,
                                    arg2,
                                    SettingActivity.this);
                            Setting.SaveInFile.setSetting(
                                    Setting.Key.AceCodeEditorLightTheme,
                                    aceEditorThemes.get(arg2),
                                    SettingActivity.this);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {}
                });

        soraEditorThemeChooser.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        if (Setting.SaveInFile.getSettingString(
                                        Setting.Key.ThemeType,
                                        Setting.Default.ThemeType,
                                        SettingActivity.this)
                                .equals(Setting.Value.Dark)) {
                            Setting.SaveInFile.setSetting(
                                    Setting.Key.SoraCodeEditorDarkThemeSelectionPosition,
                                    arg2,
                                    SettingActivity.this);
                            Setting.SaveInFile.setSetting(
                                    Setting.Key.SoraCodeEditorDarkTheme,
                                    soraEditorThemes.get(arg2).toString(),
                                    SettingActivity.this);
                        } else if (Setting.SaveInFile.getSettingString(
                                        Setting.Key.ThemeType,
                                        Setting.Default.ThemeType,
                                        SettingActivity.this)
                                .equals(Setting.Value.Light)) {
                            Setting.SaveInFile.setSetting(
                                    Setting.Key.SoraCodeEditorLightThemeSelectionPosition,
                                    arg2,
                                    SettingActivity.this);
                            Setting.SaveInFile.setSetting(
                                    Setting.Key.SoraCodeEditorLightTheme,
                                    soraEditorThemes.get(arg2).toString(),
                                    SettingActivity.this);
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

    public class editorChooserAdapter extends BaseAdapter {
        ArrayList<String> data;
        public TextView textView;

        public editorChooserAdapter(ArrayList<String> _arr) {
            data = _arr;
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
