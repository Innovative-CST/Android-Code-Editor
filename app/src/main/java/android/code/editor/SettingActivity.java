package android.code.editor;

import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.ui.Utils;
import android.code.editor.utils.Setting;
import android.code.editor.utils.ThemeObservable;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import editor.tsd.widget.CodeEditorLayout;
import io.github.rosemoe.sora.widget.CodeEditor;
import java.util.ArrayList;
import java.util.HashMap;

public class SettingActivity extends AppCompatActivity {
    public Spinner editorChooser;
    ArrayList<HashMap<String, Object>> data = new ArrayList<>();
	private ThemeObservable themeObservable = new ThemeObservable();
    public LinearLayout theme1;
    public LinearLayout theme2;
    public LinearLayout theme3;
    public LinearLayout theme4;
    public LinearLayout theme5;
    public LinearLayout theme6;

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
        toolbar.setTitleTextColor(
                MaterialColorHelper.getMaterialColorInt(
                        this, com.google.android.material.R.attr.colorOnPrimary));
        toolbar.getNavigationIcon()
                .setTint(
                        MaterialColorHelper.getMaterialColorInt(
                                this, com.google.android.material.R.attr.colorOnPrimary));

        editorChooser = findViewById(R.id.editorChooser);

        HashMap map = new HashMap<>();
        map.put("Editor", "Ace Code Editor");
        data.add(map);
        map.clear();
        HashMap map2 = new HashMap<>();
        map2.put("Editor", "Sora Code Editor");
        data.add(map2);
        map2.clear();

        editorChooser.setAdapter(new editorChooserAdapter(data));
        editorChooser.setSelection(
                Setting.SaveInFile.getSettingInt(Setting.Key.CodeEditor, Setting.Default.CodeEditor, this));

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

    public class editorChooserAdapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> data;
        public TextView textView;

        public editorChooserAdapter(ArrayList<HashMap<String, Object>> _arr) {
            data = _arr;
        }

        @Override
        public HashMap<String, Object> getItem(int _index) {
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
            if (arg0 == 0) {
                textView.setText("Ace Code Editor");
            } else if (arg0 == 1) {
                textView.setText("Sora Code Editor");
            }

            textView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // TODO: Implement this method
                            editorChooser.setSelection(arg0);
                        }
                    });

            return _view;
        }
    }

    public void initTheme() {
        theme1 = findViewById(R.id.ThemeContent1);
        theme2 = findViewById(R.id.ThemeContent2);
        theme3 = findViewById(R.id.ThemeContent3);
        theme4 = findViewById(R.id.ThemeContent4);
        theme5 = findViewById(R.id.ThemeContent5);
        theme6 = findViewById(R.id.ThemeContent6);

        theme1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO: Implement this method
                        Snackbar snackbar =
                                Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Theme will be changed when you restart.",
                                        Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Setting.SaveInFile.setSetting(
                                Setting.Key.Theme, "BrownishLight", SettingActivity.this);
						recreate();
						themeObservable.notifyThemeChanged();
                    }
                });

        theme2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO: Implement this method
                        Snackbar snackbar =
                                Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Theme will be changed when you restart.",
                                        Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Setting.SaveInFile.setSetting(Setting.Key.Theme, "BrownishDark", SettingActivity.this);
						recreate();
						themeObservable.notifyThemeChanged();
                    }
                });

        theme3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO: Implement this method
                        Snackbar snackbar =
                                Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Theme will be changed when you restart.",
                                        Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Setting.SaveInFile.setSetting(
                                Setting.Key.Theme, "LightBlueLight", SettingActivity.this);
						recreate();
						themeObservable.notifyThemeChanged();
                    }
                });

        theme4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO: Implement this method
                        Snackbar snackbar =
                                Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Theme will be changed when you restart.",
                                        Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Setting.SaveInFile.setSetting(
                                Setting.Key.Theme, "LightBlueDark", SettingActivity.this);
						recreate();
						themeObservable.notifyThemeChanged();
                    }
                });

        theme5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO: Implement this method
                        Snackbar snackbar =
                                Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Theme will be changed when you restart.",
                                        Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Setting.SaveInFile.setSetting(Setting.Key.Theme, "LightGreen", SettingActivity.this);
						recreate();
						themeObservable.notifyThemeChanged();
                    }
                });

        theme6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO: Implement this method
                        Snackbar snackbar =
                                Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Theme will be changed when you restart.",
                                        Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Setting.SaveInFile.setSetting(Setting.Key.Theme, "DarkGreen", SettingActivity.this);
						recreate();
						themeObservable.notifyThemeChanged();
                    }
                });
    }
}
