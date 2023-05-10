package android.code.editor;

import android.code.editor.files.utils.FileManager;
import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.utils.Setting;
import android.code.editor.utils.ThemeObservable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import editor.tsd.widget.CodeEditorLayout;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CodeEditorActivity extends AppCompatActivity implements Observer {
	private ThemeObservable themeObservable = new ThemeObservable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		((MyApplication)getApplication()).getThemeObservable().addObserver(this);
        MaterialColorHelper.setUpTheme(this);
        setContentView(R.layout.activity_code_editor);
        initActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: Implement this method
		((MyApplication)getApplication()).getThemeObservable().deleteObserver(this);
    }

    public void initActivity() {
        CodeEditorLayout codeEditor = findViewById(R.id.editor);

        codeEditor.setEditor(
                Setting.SaveInFile.getSettingInt(Setting.Key.CodeEditor, Setting.Default.CodeEditor, this));
        codeEditor.setCode(FileManager.readFile(getIntent().getStringExtra("path")));
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

    @Override
    public void update(Observable arg0, Object arg1) {
        if ((String) arg1 == "ThemeUpdated") {
            recreate();
        }
    }
}
