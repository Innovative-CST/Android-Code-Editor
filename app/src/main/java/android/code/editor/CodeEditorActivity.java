package android.code.editor;

import android.code.editor.files.utils.FileIcon;
import android.code.editor.files.utils.FileManager;
import android.code.editor.files.utils.FileTypeHandler;
import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.ui.Utils;
import android.code.editor.utils.LanguageModeHandler;
import android.code.editor.utils.Setting;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import editor.tsd.widget.CodeEditorLayout;

import io.github.rosemoe.sora.util.ArrayList;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CodeEditorActivity extends AppCompatActivity {

    public CodeEditorLayout codeEditor;
    public ProgressBar progressbar;
    public LinearLayout editorArea;
    public LinearLayout fileNotOpenedArea;
    public ImageView moveLeft;
    public ImageView moveRight;
    public ImageView moveUp;
    public ImageView moveDown;
    public File DrawerListDir;
    public RecyclerView list;
    private FileList filelist;
    public ArrayList<String> listString = new ArrayList<>();
    public ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
    public String selectPath;

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
        list = findViewById(R.id.list);

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

        if (getIntent().hasExtra("path")) {
            if (new File(getIntent().getStringExtra("path")).isFile()) {
                DrawerListDir = new File(getIntent().getStringExtra("path")).getParentFile();
                selectPath = DrawerListDir.getAbsolutePath();
                loadFileList(DrawerListDir.getAbsolutePath());
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

                codeEditor.setLanguageMode(
                        LanguageModeHandler.getLanguageModeForExtension(
                                FileTypeHandler.getFileFormat(getIntent().getStringExtra("path"))));

            } else {
                DrawerListDir = new File(getIntent().getStringExtra("path"));
                selectPath = DrawerListDir.getAbsolutePath();
                loadFileList(DrawerListDir.getAbsolutePath());
                if (fileNotOpenedArea.getVisibility() == View.GONE
                        || editorArea.getVisibility() == View.VISIBLE) {
                    fileNotOpenedArea.setVisibility(View.VISIBLE);
                    editorArea.setVisibility(View.GONE);
                }
            }
        }
    }

    public void openFileInEditor(File file) {
        fileNotOpenedArea.setVisibility(View.GONE);
        editorArea.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.VISIBLE);
        codeEditor.setVisibility(View.GONE);

        codeEditor.setCode(FileManager.readFile(file.getAbsolutePath()));

        progressbar.setVisibility(View.GONE);
        codeEditor.setVisibility(View.VISIBLE);

        codeEditor.setLanguageMode(
                LanguageModeHandler.getLanguageModeForExtension(
                        FileTypeHandler.getFileFormat(file.getAbsolutePath())));
    }

    public void loadFileList(String path) {
        listString.clear();
        listMap.clear();
        ExecutorService loadFileList = Executors.newSingleThreadExecutor();

        loadFileList.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        // TODO: Implement this method

                        runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        progressbar.setVisibility(View.VISIBLE);
                                    }
                                });

                        // Get file path from intent and list dir in array
                        FileManager.listDir(path, listString);

                        FileManager.setUpFileList(listMap, listString);
                        // For ..(Go back)
                        if (!DrawerListDir.getAbsolutePath().equals(selectPath)) {
                            HashMap<String, Object> _item = new HashMap<>();
                            _item.put("goBack", "..");
                            listMap.add(0, _item);
                        }

                        runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        // Set Data in list
                                        progressbar.setVisibility(View.GONE);
                                        filelist = new FileList(listMap);
                                        list.setAdapter(filelist);
                                        list.setLayoutManager(
                                                new LinearLayoutManager(CodeEditorActivity.this));
                                    }
                                });
                    }
                });
    }

    // Adapter of Recycler View
    private class FileList extends RecyclerView.Adapter<FileList.ViewHolder> {

        ArrayList<HashMap<String, Object>> _data;
        private ImageView icon;
        private ImageView gitIcon;
        private TextView path;
        private LinearLayout mainlayout;

        public FileList(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.filelist, null);
            RecyclerView.LayoutParams _lp =
                    new RecyclerView.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }

        @Override
        public void onBindViewHolder(ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            mainlayout = _view.findViewById(R.id.layout);
            icon = _view.findViewById(R.id.icon);
            path = _view.findViewById(R.id.path);
            gitIcon = _view.findViewById(R.id.git);
            gitIcon.setVisibility(View.GONE);
            if (_data.get(_position).containsKey("goBack")) {
                icon.setImageResource(R.drawable.ic_folder_black_24dp);
                path.setText("..");
                Utils.applyRippleEffect(
                        mainlayout,
                        MaterialColorHelper.getMaterialColor(
                                CodeEditorActivity.this,
                                com.google.android.material.R.attr.colorSurface),
                        MaterialColorHelper.getMaterialColor(
                                CodeEditorActivity.this,
                                com.google.android.material.R.attr.colorOnSurface));
                mainlayout.setOnClickListener(
                        (view) -> {
                            DrawerListDir = DrawerListDir.getParentFile();
                            loadFileList(DrawerListDir.getAbsolutePath());
                        });
            } else {
                FileIcon.setUpIcon(
                        CodeEditorActivity.this, _data.get(_position).get("path").toString(), icon);
                Utils.applyRippleEffect(
                        mainlayout,
                        MaterialColorHelper.getMaterialColor(
                                CodeEditorActivity.this,
                                com.google.android.material.R.attr.colorSurface),
                        MaterialColorHelper.getMaterialColor(
                                CodeEditorActivity.this,
                                com.google.android.material.R.attr.colorOnSurface));
                path.setText(_data.get(_position).get("lastSegmentOfFilePath").toString());
                String path = _data.get(_position).get("path").toString();
                mainlayout.setOnClickListener(
                        (view) -> {
                            if (new File(path).isDirectory()) {
                                DrawerListDir = new File(path);
                                loadFileList(path);
                            } else {
                                switch (FileTypeHandler.getFileFormat(path)) {
                                    case "java":
                                    case "xml":
                                    case "html":
                                    case "css":
                                    case "js":
                                        openFileInEditor(new File(path));
                                        break;
                                    default:
                                        break;
                                }
                            }
                        });
            }
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }
    }
}
