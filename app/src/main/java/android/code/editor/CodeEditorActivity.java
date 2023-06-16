package android.code.editor;

import android.code.editor.files.utils.FileManager;
import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.utils.Setting;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;

public class CodeEditorActivity extends AppCompatActivity {

    public CodeEditorLayout codeEditor;
    public ProgressBar progressbar;
    public ImageView moveLeft;
    public ImageView moveRight;
    public ImageView moveUp;
    public ImageView moveDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaterialColorHelper.setUpTheme(this);
        setContentView(R.layout.activity_code_editor);
        initActivity();
    }

    public void initActivity() {
        // Initialize views
        codeEditor = findViewById(R.id.editor);
        progressbar = findViewById(R.id.progressbar);
        moveLeft = findViewById(R.id.moveLeft);
        moveRight = findViewById(R.id.moveRight);
        moveUp = findViewById(R.id.moveUp);
        moveDown = findViewById(R.id.moveDown);

        progressbar.setVisibility(View.VISIBLE);
        codeEditor.setVisibility(View.GONE);
        codeEditor.setEditor(
                Setting.SaveInFile.getSettingInt(
                        Setting.Key.CodeEditor, Setting.Default.CodeEditor, this));
        codeEditor.setCode(FileManager.readFile(getIntent().getStringExtra("path")));
        progressbar.setVisibility(View.GONE);
        codeEditor.setVisibility(View.VISIBLE);
        // Set editor theme
        if (Setting.SaveInFile.getSettingString(
                        Setting.Key.ThemeType, Setting.Default.ThemeType, this)
                .equals(Setting.Value.Dark)) {
            if (codeEditor.getCurrentEditorType() == CodeEditorLayout.SoraCodeEditor) {
                codeEditor.setTheme(
                        Setting.SaveInFile.getSettingString(
                                Setting.Key.SoraCodeEditorDarkTheme,
                                Setting.Default.SoraCodeEditorDarkTheme,
                                this));
            } else if (codeEditor.getCurrentEditorType() == CodeEditorLayout.AceCodeEditor) {
                codeEditor.setTheme(
                        Setting.SaveInFile.getSettingString(
                                Setting.Key.AceCodeEditorDarkTheme,
                                Setting.Default.SoraCodeEditorDarkTheme,
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
        moveLeft.setOnClickListener(
                (view) -> {
                    codeEditor.moveCursorHorizontally(-1);
                });
        moveRight.setOnClickListener(
                (view) -> {
                    codeEditor.moveCursorHorizontally(1);
                });
        moveUp.setOnClickListener(
                (view) -> {
                    codeEditor.moveCursorVertically(-1);
                });
        moveDown.setOnClickListener(
                (view) -> {
                    codeEditor.moveCursorVertically(1);
                });
    }
}
