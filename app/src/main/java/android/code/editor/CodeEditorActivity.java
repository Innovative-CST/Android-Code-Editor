package android.code.editor;

import android.code.editor.files.utils.FileManager;
import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.utils.Setting;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import editor.tsd.widget.CodeEditorLayout;

import java.io.File;

public class CodeEditorActivity extends AppCompatActivity {

    public CodeEditorLayout codeEditor;
    public ProgressBar progressbar;
    public LinearLayout editorArea;
    public LinearLayout fileNotOpenedArea;
    public ImageView moveLeft;
    public ImageView moveRight;
    public ImageView moveUp;
    public ImageView moveDown;
    public File ParentDir;

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
        editorArea = findViewById(R.id.editorArea);
        fileNotOpenedArea = findViewById(R.id.fileNotOpenedArea);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Code Editor");
        DrawerLayout drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.string.app_name, R.string.app_name);
        toolbar.setTitleTextColor(
                MaterialColorHelper.getMaterialColor(
                        this, com.google.android.material.R.attr.colorOnPrimary));
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO: Implement this method
                        if (drawer.isDrawerVisible(GravityCompat.END)) {
                            drawer.closeDrawer(GravityCompat.END);
                        } else {
                            drawer.openDrawer(GravityCompat.END);
                        }
                    }
                });
        toggle.getDrawerArrowDrawable()
                .setColor(
                        MaterialColorHelper.getMaterialColor(
                                this, com.google.android.material.R.attr.colorOnPrimary));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        moveLeft.setOnClickListener(
                (view) -> {
                    codeEditor.moveCursorHorizontally(-1);
                });
        moveLeft.setOnTouchListener(
                new View.OnTouchListener() {
                    private Handler mHandler;
                    private boolean isMoveLeftButtonPressed = false;
                    private int holdedSince;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (!isMoveLeftButtonPressed) {
                                    mHandler = new Handler(Looper.getMainLooper());
                                    mHandler.postDelayed(mRunnable, 500);
                                    isMoveLeftButtonPressed = true;
                                    holdedSince = 0;
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                // Stop the action
                                mHandler.removeCallbacks(mRunnable);
                                isMoveLeftButtonPressed = false;
                                break;
                        }
                        return false;
                    }

                    private Runnable mRunnable =
                            new Runnable() {
                                @Override
                                public void run() {
                                    codeEditor.moveCursorHorizontally(-1);
                                    if (holdedSince < 1500) {
                                        mHandler.postDelayed(this, 100);
                                        holdedSince = holdedSince + 100;
                                    } else {
                                        mHandler.postDelayed(this, 50);
                                    }
                                }
                            };
                });
        moveRight.setOnClickListener(
                (view) -> {
                    codeEditor.moveCursorHorizontally(1);
                });
        moveRight.setOnTouchListener(
                new View.OnTouchListener() {
                    private Handler mHandler;
                    private boolean isMoveRightButtonPressed = false;
                    private int holdedSince;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (!isMoveRightButtonPressed) {
                                    mHandler = new Handler(Looper.getMainLooper());
                                    mHandler.postDelayed(mRunnable, 500);
                                    isMoveRightButtonPressed = true;
                                    holdedSince = 0;
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                mHandler.removeCallbacks(mRunnable);
                                isMoveRightButtonPressed = false;
                                break;
                        }
                        return false;
                    }

                    private Runnable mRunnable =
                            new Runnable() {
                                @Override
                                public void run() {
                                    codeEditor.moveCursorHorizontally(1);
                                    if (holdedSince < 1500) {
                                        mHandler.postDelayed(this, 100);
                                        holdedSince = holdedSince + 100;
                                    } else {
                                        mHandler.postDelayed(this, 50);
                                    }
                                }
                            };
                });
        moveUp.setOnClickListener(
                (view) -> {
                    codeEditor.moveCursorVertically(-1);
                });
        moveUp.setOnTouchListener(
                new View.OnTouchListener() {
                    private Handler mHandler;
                    private boolean isMoveUpButtonPressed = false;
                    private int holdedSince;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (!isMoveUpButtonPressed) {
                                    mHandler = new Handler(Looper.getMainLooper());
                                    mHandler.postDelayed(mRunnable, 500);
                                    isMoveUpButtonPressed = true;
                                    holdedSince = 0;
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                mHandler.removeCallbacks(mRunnable);
                                isMoveUpButtonPressed = false;
                                break;
                        }
                        return false;
                    }

                    private Runnable mRunnable =
                            new Runnable() {
                                @Override
                                public void run() {
                                    codeEditor.moveCursorVertically(-1);
                                    if (holdedSince < 1500) {
                                        mHandler.postDelayed(this, 100);
                                        holdedSince = holdedSince + 100;
                                    } else {
                                        mHandler.postDelayed(this, 50);
                                    }
                                }
                            };
                });
        moveDown.setOnClickListener(
                (view) -> {
                    codeEditor.moveCursorVertically(1);
                });
        moveDown.setOnTouchListener(
                new View.OnTouchListener() {
                    private Handler mHandler;
                    private boolean isMoveDownButtonPressed = false;
                    private int holdedSince;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (!isMoveDownButtonPressed) {
                                    mHandler = new Handler(Looper.getMainLooper());
                                    mHandler.postDelayed(mRunnable, 500);
                                    isMoveDownButtonPressed = true;
                                    holdedSince = 0;
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                mHandler.removeCallbacks(mRunnable);
                                isMoveDownButtonPressed = false;
                                break;
                        }
                        return false;
                    }

                    private Runnable mRunnable =
                            new Runnable() {
                                @Override
                                public void run() {
                                    codeEditor.moveCursorVertically(1);
                                    if (holdedSince < 1500) {
                                        mHandler.postDelayed(this, 100);
                                        holdedSince = holdedSince + 100;
                                    } else {
                                        mHandler.postDelayed(this, 50);
                                    }
                                }
                            };
                });

        // Set Editor Type eg. AceEditor, SoraEditor
        codeEditor.setEditor(
                Setting.SaveInFile.getSettingInt(
                        Setting.Key.CodeEditor, Setting.Default.CodeEditor, this));
        // Set Editor Theme
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
        // Set Language Mode
        if (getIntent().hasExtra("LanguageMode")) {
            codeEditor.setLanguageMode(getIntent().getStringExtra("LanguageMode"));
        }

        if (getIntent().hasExtra("path")) {
            if (new File(getIntent().getStringExtra("path")).isFile()) {
                ParentDir = new File(getIntent().getStringExtra("path")).getParentFile();
                if (fileNotOpenedArea.getVisibility() == View.VISIBLE
                        || editorArea.getVisibility() == View.GONE) {
                    fileNotOpenedArea.setVisibility(View.GONE);
                    editorArea.setVisibility(View.VISIBLE);
                }
                progressbar.setVisibility(View.VISIBLE);
                codeEditor.setVisibility(View.GONE);

                codeEditor.setCode(FileManager.readFile(getIntent().getStringExtra("path")));

                progressbar.setVisibility(View.GONE);
                codeEditor.setVisibility(View.VISIBLE);

            } else {
                ParentDir = new File(getIntent().getStringExtra("path"));
                if (fileNotOpenedArea.getVisibility() == View.GONE
                        || editorArea.getVisibility() == View.VISIBLE) {
                    fileNotOpenedArea.setVisibility(View.VISIBLE);
                    editorArea.setVisibility(View.GONE);
                }
            }
        }
    }
}
