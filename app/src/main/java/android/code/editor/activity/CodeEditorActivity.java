package android.code.editor.activity;

import android.animation.ObjectAnimator;
import android.code.editor.R;
import android.code.editor.files.utils.FileIcon;
import android.code.editor.files.utils.FileManager;
import android.code.editor.files.utils.FileTypeHandler;
import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.utils.LanguageModeHandler;
import android.code.editor.utils.Setting;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import editor.tsd.editors.AceEditor;
import editor.tsd.widget.CodeEditorLayout;

import io.github.rosemoe.sora.util.ArrayList;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;

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
    public String selectPath;
    public DrawerLayout drawer;
    private ObjectAnimator rotate = new ObjectAnimator();
    public File openedFile;
    public CodeEditorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaterialColorHelper.setUpTheme(this);
        setContentView(R.layout.activity_code_editor);
        initActivity();
    }

    public void save() {
        if (openedFile != null) {
            if (codeEditor != null) {
                /*try {
                Thread.sleep(5);*/
                FileManager.writeFile(openedFile.getAbsolutePath(), codeEditor.getCode());
                /*} catch (Exception e) {

                }*/
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    public void initActivity() {
        // Initialize views
        // codeEditor = findViewById(R.id.editor);
        progressbar = findViewById(R.id.progressbar);
        moveLeft = findViewById(R.id.moveLeft);
        moveRight = findViewById(R.id.moveRight);
        moveUp = findViewById(R.id.moveUp);
        moveDown = findViewById(R.id.moveDown);
        editorArea = findViewById(R.id.editorArea);
        fileNotOpenedArea = findViewById(R.id.fileNotOpenedArea);

        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // Assign the layout parameters to codeEditor

        codeEditor = new CodeEditorLayout(this);
        codeEditor.setLayoutParams(layoutParams);
        ((LinearLayout) findViewById(R.id.editorCont)).addView(codeEditor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Code Editor");
        drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.string.app_name, R.string.app_name);
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
        }
        
 // OnTabSelectedListener

  @Override
  public void onTabUnselected(TabLayout.Tab p1) {}

  @Override
  public void onTabReselected(TabLayout.Tab p1) {
    PopupMenu pm = new PopupMenu(this, p1.view);
    pm.getMenu().add(R.string.close);
    pm.getMenu().add(R.string.close_others);
    pm.getMenu().add(R.string.close_all);
    pm.setOnMenuItemClickListener(
        item -> {
          if (item.getTitle() == getString(R.string.close)) {
            closeFile(viewModel.getCurrentPosition());
          } else if (item.getTitle().equals(getString(R.string.close_others))) {
            closeOthers();
          } else if (item.getTitle().equals(getString(R.string.close_all))) {
            closeAllFiles();
          }
          return true;
        });
    pm.show();
  }

  @Override
  public void onTabSelected(TabLayout.Tab p1) {
    viewModel.setCurrentPosition(p1.getPosition());
  }

 // Document closers

  public void closeFile(int index) {
    if (index >= 0 && index < viewModel.getOpenedDocumentCount()) {
      CodeEditorLayout codeEditor = getCodeEditorAtIndex(index);
      if (codeEditor != null) {
        codeEditor.release();
      }

      viewModel.removeDocument(index);
      binding.tabLayout.removeTabAt(index);
      binding.container.removeViewAt(index);
      updateTabs();
    }
  }

  public void closeOthers() {
    DocumentModel document = viewModel.getCurrentDocument();
    if (document == null) return;

    int index = 0;
    while (viewModel.getOpenedDocumentCount() != 1) {
      CodeEditorLayout codeEditor = getCodeEditorAtIndex(index);

      if (codeEditor != null) {
        if (document != codeEditor.getDocument()) {
          closeFile(index);
        } else {
          index = 1;
        }
      }
    }
    viewModel.setCurrentPosition(viewModel.indexOf(document));
  }

  public void closeAllFiles() {
    if (viewModel.getDocuments().isEmpty()) {
      return;
    }
    for (int i = 0; i < viewModel.getOpenedDocumentCount(); i++) {
      CodeEditorLayout codeEditor = getCodeEditorAtIndex(i);
      if (codeEditor != null) {
        codeEditor.release();
      }
          }      
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
                                Setting.Default.AceCodeEditorDarkTheme,
                                this));
            }
        } else if (Setting.SaveInFile.getSettingString(
                        Setting.Key.ThemeType, Setting.Default.ThemeType, this)
                .equals(Setting.Value.Light)) {
            if (codeEditor.getCurrentEditorType() == CodeEditorLayout.AceCodeEditor) {
                codeEditor.setTheme(
                        Setting.SaveInFile.getSettingString(
                                Setting.Key.AceCodeEditorLightTheme,
                                Setting.Default.AceCodeEditorLightTheme,
                                this));
            } else if (codeEditor.getCurrentEditorType() == CodeEditorLayout.SoraCodeEditor) {
                codeEditor.setTheme(
                        Setting.SaveInFile.getSettingString(
                                Setting.Key.SoraCodeEditorLightTheme,
                                Setting.Default.SoraCodeEditorLightTheme,
                                this));
            }
        }

        if (getIntent().hasExtra("path")) {
            if (new File(getIntent().getStringExtra("path")).isFile()) {
                DrawerListDir = new File(getIntent().getStringExtra("path")).getParentFile();
                selectPath = DrawerListDir.getAbsolutePath();
                openedFile = new File(getIntent().getStringExtra("path"));
                openFileInEditor(openedFile);

            } else {
                DrawerListDir = new File(getIntent().getStringExtra("path"));
                selectPath = DrawerListDir.getAbsolutePath();
                if (fileNotOpenedArea.getVisibility() == View.GONE
                        || editorArea.getVisibility() == View.VISIBLE) {
                    fileNotOpenedArea.setVisibility(View.VISIBLE);
                    editorArea.setVisibility(View.GONE);
                }
            }
            fileTree(DrawerListDir, findViewById(R.id.list));
        }
    }

    final class FileComparator implements Comparator<String> {
        public int compare(String f1, String f2) {
            if (f1 == f2) return 0;
            if (new File(f1).isDirectory() && new File(f2).isFile()) return -1;
            if (new File(f1).isFile() && new File(f2).isDirectory()) return 1;
            return f1.compareToIgnoreCase(f2);
        }
    }

    public void fileTree(File file, ViewGroup view) {
        view.removeAllViews();
        ArrayList<String> list = new ArrayList<String>();
        FileManager.listDir(file.getAbsolutePath(), list);

        Collections.sort(list, new FileComparator());
        for (int pos = 0; pos < list.size(); pos++) {
            File fil = new File(list.get(pos));
            LayoutInflater layoutInflator = getLayoutInflater();
            View layout = layoutInflator.inflate(R.layout.filelist, null);
            int left = dpToPx(this, 8);
            int top = dpToPx(this, 8);
            int right = dpToPx(this, 8);
            int bottom = dpToPx(this, 0);
            layout.findViewById(R.id.layout).setPadding(left, top, right, bottom);
            if (fil.isDirectory()) {
                layout.findViewById(R.id.expandCollapse).setVisibility(View.VISIBLE);
                layout.findViewById(R.id.child).setVisibility(View.GONE);
                ((ImageView) layout.findViewById(R.id.expandCollapse))
                        .setImageResource(R.drawable.chevron_right);
                layout.findViewById(R.id.layout)
                        .setOnClickListener(
                                (main) -> {
                                    if (layout.findViewById(R.id.child).getVisibility()
                                            == View.GONE) {
                                        fileTree(
                                                fil,
                                                (LinearLayout) layout.findViewById(R.id.child));
                                        layout.findViewById(R.id.child).setVisibility(View.VISIBLE);
                                        rotate.setTarget(layout.findViewById(R.id.expandCollapse));
                                        rotate.setPropertyName("rotation");
                                        rotate.setFloatValues(0, 90);
                                        rotate.setDuration(200);
                                        rotate.setInterpolator(new LinearInterpolator());
                                        rotate.start();
                                    } else {
                                        layout.findViewById(R.id.child).setVisibility(View.GONE);
                                        ((LinearLayout) layout.findViewById(R.id.child))
                                                .removeAllViews();
                                        rotate.setTarget(layout.findViewById(R.id.expandCollapse));
                                        rotate.setPropertyName("rotation");
                                        rotate.setFloatValues(90, 0);
                                        rotate.setDuration(200);
                                        rotate.setInterpolator(new LinearInterpolator());
                                        rotate.start();
                                    }
                                });
            } else {
                layout.findViewById(R.id.expandCollapse).setVisibility(View.INVISIBLE);
                layout.findViewById(R.id.layout)
                        .setOnClickListener(
                                (main) -> {
                                    switch (FileTypeHandler.getFileFormat(fil.getAbsolutePath())) {
                                        case "java":
                                        case "xml":
                                        case "html":
                                        case "css":
                                        case "js":
                                            save();
                                            openFileInEditor(fil);
                                            drawer.closeDrawer(GravityCompat.END);
                                            break;
                                        default:
                                            break;
                                    }
                                });
            }
            FileIcon.setUpIcon(
                    CodeEditorActivity.this,
                    fil.getAbsolutePath(),
                    (ImageView) layout.findViewById(R.id.icon));
            ((TextView) layout.findViewById(R.id.path))
                    .setText(FileManager.getLatSegmentOfFilePath(fil.getAbsolutePath()));
            view.addView(layout);
        }
    }

    // Method to convert dp to pixels
    private int dpToPx(Context context, int dp) {
        return (int)
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dp,
                        context.getResources().getDisplayMetrics());
    }

    public void openFileInEditor(File file) {
        fileNotOpenedArea.setVisibility(View.GONE);
        editorArea.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.VISIBLE);
        codeEditor.setVisibility(View.GONE);

        openedFile = file;
        codeEditor.setCode(FileManager.readFile(file.getAbsolutePath()));

        progressbar.setVisibility(View.GONE);
        codeEditor.setVisibility(View.VISIBLE);

        codeEditor.setLanguageMode(
                LanguageModeHandler.getLanguageModeForExtension(
                        FileTypeHandler.getFileFormat(file.getAbsolutePath())));
    }

private void updateTabs() {
    TaskExecutor.executeAsyncProvideError(
        () -> getUniqueNames(),
        (result, error) -> {
          if (result == null || error != null) {
            return;
          }

          result.forEach(
              (index, name) -> {
                TabLayout.Tab tab = binding.tabLayout.getTabAt(index);
                if (tab != null) {
                  tab.setText(name);
                }
              });
        });
        }
}
