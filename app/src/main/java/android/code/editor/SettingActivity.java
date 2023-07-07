package android.code.editor;

import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.utils.Setting;
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
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {
    public Spinner editorChooser;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> aceEditorThemes = new ArrayList<>();
    ArrayList<String> soraEditorThemes = new ArrayList<>();

    public LinearLayout theme1;
    public LinearLayout theme2;
    public LinearLayout theme3;
    public LinearLayout theme4;
    public LinearLayout theme5;
    public LinearLayout theme6;
    public Spinner aceEditorThemeChooser;
    public Spinner soraEditorThemeChooser;

    @Override
    protected void onCreate(Bundle arg0) {
        MaterialColorHelper.setUpTheme(this);
        super.onCreate(arg0);
        // TODO: Implement this method
        setContentView(R.layout.activity_setting);
        initActivity();
    }

    public void initActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO: Implement this method
                        onBackPressed();
                    }
                });
        editorChooser = findViewById(R.id.editorChooser);
        aceEditorThemeChooser = findViewById(R.id.aceEditorThemeChooser);
        soraEditorThemeChooser = findViewById(R.id.soraEditorThemeChooser);

        data.add("Ace Code Editor");
        data.add("Sora Code Editor");

        editorChooser.setAdapter(new editorChooserAdapter(data));
        editorChooser.setSelection(
                Setting.SaveInFile.getSettingInt(
                        Setting.Key.CodeEditor, Setting.Default.CodeEditor, this));
        initEditorThemeList();
        initTheme();
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
    }

    public void initTheme() {
        theme1 = findViewById(R.id.ThemeContent1);
        theme2 = findViewById(R.id.ThemeContent2);
        theme3 = findViewById(R.id.ThemeContent3);
        theme4 = findViewById(R.id.ThemeContent4);
        theme5 = findViewById(R.id.ThemeContent5);
        theme6 = findViewById(R.id.ThemeContent6);

        theme1.setOnClickListener(
                (view) -> {
                    Snackbar snackbar =
                            Snackbar.make(
                                    findViewById(android.R.id.content),
                                    "Theme will be changed when you restart.",
                                    Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Setting.SaveInFile.setSetting(
                            Setting.Key.NewTheme, "BrownishLight", SettingActivity.this);
                    Setting.SaveInFile.setSetting(
                            Setting.Key.NewThemeType, Setting.Value.Light, SettingActivity.this);
                });

        theme2.setOnClickListener(
                (view) -> {
                    Snackbar snackbar =
                            Snackbar.make(
                                    findViewById(android.R.id.content),
                                    "Theme will be changed when you restart.",
                                    Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Setting.SaveInFile.setSetting(
                            Setting.Key.NewTheme, "BrownishDark", SettingActivity.this);
                    Setting.SaveInFile.setSetting(
                            Setting.Key.NewThemeType, Setting.Value.Dark, SettingActivity.this);
                });

        theme3.setOnClickListener(
                (view) -> {
                    Snackbar snackbar =
                            Snackbar.make(
                                    findViewById(android.R.id.content),
                                    "Theme will be changed when you restart.",
                                    Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Setting.SaveInFile.setSetting(
                            Setting.Key.NewTheme, "LightBlueLight", SettingActivity.this);
                    Setting.SaveInFile.setSetting(
                            Setting.Key.NewThemeType, Setting.Value.Light, SettingActivity.this);
                });

        theme4.setOnClickListener(
                (view) -> {
                    Snackbar snackbar =
                            Snackbar.make(
                                    findViewById(android.R.id.content),
                                    "Theme will be changed when you restart.",
                                    Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Setting.SaveInFile.setSetting(
                            Setting.Key.NewTheme, "LightBlueDark", SettingActivity.this);
                    Setting.SaveInFile.setSetting(
                            Setting.Key.NewThemeType, Setting.Value.Dark, SettingActivity.this);
                });

        theme5.setOnClickListener(
                (view) -> {
                    Snackbar snackbar =
                            Snackbar.make(
                                    findViewById(android.R.id.content),
                                    "Theme will be changed when you restart.",
                                    Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Setting.SaveInFile.setSetting(
                            Setting.Key.NewTheme, "LightGreen", SettingActivity.this);
                    Setting.SaveInFile.setSetting(
                            Setting.Key.NewThemeType, Setting.Value.Light, SettingActivity.this);
                });

        theme6.setOnClickListener(
                (view) -> {
                    Snackbar snackbar =
                            Snackbar.make(
                                    findViewById(android.R.id.content),
                                    "Theme will be changed when you restart.",
                                    Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Setting.SaveInFile.setSetting(
                            Setting.Key.NewTheme, "DarkGreen", SettingActivity.this);
                    Setting.SaveInFile.setSetting(
                            Setting.Key.NewThemeType, Setting.Value.Dark, SettingActivity.this);
                });
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
        }
        aceEditorThemeChooser.setAdapter(new editorThemeChooserAdapter(aceEditorThemes));
        soraEditorThemeChooser.setAdapter(new editorThemeChooserAdapter(soraEditorThemes));

        aceEditorThemeChooser.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        // TODO: Implement this method
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
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO: Implement this method
                    }
                });

        soraEditorThemeChooser.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        // TODO: Implement this method
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
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO: Implement this method
                    }
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

        soraEditorThemeChooser.setSelection(
                Setting.SaveInFile.getSettingInt(
                        Setting.Key.SoraCodeEditorDarkThemeSelectionPosition,
                        Setting.Default.SoraCodeEditorDarkThemeSelectionPosition,
                        this));
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
            // TODO: Implement this method
            return data.size();
        }

        @Override
        public long getItemId(int arg0) {
            // TODO: Implement this method
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            // TODO: Implement this method
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
            // TODO: Implement this method
            return data.size();
        }

        @Override
        public long getItemId(int arg0) {
            // TODO: Implement this method
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            // TODO: Implement this method
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
