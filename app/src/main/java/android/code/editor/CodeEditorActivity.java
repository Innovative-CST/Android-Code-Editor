package android.code.editor;

import android.code.editor.files.utils.FileManager;
import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.utils.Setting;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;

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

        codeEditor.setEditor(
                Setting.SaveInFile.getSettingInt(Setting.Key.CodeEditor, Setting.Default.CodeEditor, this));
        codeEditor.setCode(FileManager.readFile(getIntent().getStringExtra("path")));
        
        // Set editor theme
        if (Setting.SaveInFile.getSettingString(Setting.Key.ThemeType,Setting.Default.ThemeType,this).equals(Setting.Value.Dark)) {
            if (Setting.SaveInFile.getSettingInt(Setting.Key.CodeEditor, Setting.Default.CodeEditor, this) == CodeEditorLayout.SoraCodeEditor) {
                codeEditor.setTheme(Setting.SaveInFile.getSettingString("SoraCodeEditorDarkTheme",Themes.SoraEditorTheme.Dark.Darcula,this));
            } else if (Setting.SaveInFile.getSettingInt(Setting.Key.CodeEditor, Setting.Default.CodeEditor, this) == CodeEditorLayout.AceCodeEditor) {
                codeEditor.setTheme(Setting.SaveInFile.getSettingString("AceCodeEditorDarkTheme",Themes.AceEditorTheme.Dark.Dracula,this));
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
