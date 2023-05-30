package android.code.editor;

import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.utils.Setting;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;

import ninja.corex.file.NinjaMacroFileUtil;

public class CodeEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaterialColorHelper.setUpTheme(this);
        setContentView(R.layout.activity_code_editor);
        initActivity();
    }

    public void initActivity() {
        CodeEditorLayout codeEditor = findViewById(R.id.editor);
        findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
        codeEditor.setVisibility(View.GONE);
        codeEditor.setEditor(
                Setting.SaveInFile.getSettingInt(
                        Setting.Key.CodeEditor, Setting.Default.CodeEditor, this));
        NinjaMacroFileUtil.readFile(
                getIntent().getStringExtra("path"),
                new NinjaMacroFileUtil.OnFileOperationListener() {
                    @Override
                    public void onSuccess(String arg0) {
                        codeEditor.setCode(arg0);
                        findViewById(R.id.progressbar).setVisibility(View.GONE);
                        codeEditor.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(String error) {
                        codeEditor.setCode(error);
                        findViewById(R.id.progressbar).setVisibility(View.GONE);
                        codeEditor.setVisibility(View.VISIBLE);
                    }
                });
        // Set editor theme
        if (Setting.SaveInFile.getSettingString(
                        Setting.Key.ThemeType, Setting.Default.ThemeType, this)
                .equals(Setting.Value.Dark)) {
            if (codeEditor.getCurrentEditorType() == CodeEditorLayout.SoraCodeEditor) {
                codeEditor.setTheme(
                        Setting.SaveInFile.getSettingString(
                                "SoraCodeEditorDarkTheme",
                                Themes.SoraEditorTheme.Dark.Darcula,
                                this));
            } else if (codeEditor.getCurrentEditorType() == CodeEditorLayout.AceCodeEditor) {
                codeEditor.setTheme(
                        Setting.SaveInFile.getSettingString(
                                "AceCodeEditorDarkTheme",
                                Themes.AceEditorTheme.Dark.Dracula,
                                this));
            }
        }

        if (getIntent().hasExtra("LanguageMode")) {
            codeEditor.setLanguageMode(getIntent().getStringExtra("LanguageMode"));
        }

        findViewById(R.id.toast)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                // TODO: Implement this method
                                Toast.makeText(
                                                CodeEditorActivity.this,
                                                codeEditor.getCode(),
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
    }
}
