package android.code.editor.activity;

import android.animation.ObjectAnimator;
import android.code.editor.R;
import android.code.editor.adapter.FileTabAdapter;
import android.code.editor.adapters.viewholders.FileTreeViewHolder;
import android.code.editor.files.utils.FileManager;
import android.code.editor.files.utils.FileTypeHandler;
import android.code.editor.utils.LanguageModeHandler;
import android.code.editor.utils.Setting;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.unnamed.b.atv.view.TreeNodeWrapperView;
import editor.tsd.widget.CodeEditorLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CodeEditorActivity extends BaseActivity {

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
  public Menu menu;
  public MenuItem preview;
  public TreeNode root;
  public RecyclerView fileTab;
  public FileTabAdapter adapter;
  public ArrayList<FileTabDataItem> fileTabData = new ArrayList<FileTabDataItem>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_code_editor);
    initActivity();
  }

  public void save() {
    if (openedFile != null) {
      if (codeEditor != null) {
        FileManager.writeFile(openedFile.getAbsolutePath(), codeEditor.getCode());
      }
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    save();
  }

  @Override
  public void onConfigurationChanged(Configuration arg0) {
    super.onConfigurationChanged(arg0);
    save();
    recreate();
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
    fileTab = findViewById(R.id.fileTab);

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
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
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

    // Set Editor Type eg. AceEditor, SoraEditor
    codeEditor.setEditor(
        Setting.SaveInFile.getSettingInt(Setting.Key.CodeEditor, Setting.Default.CodeEditor, this));
    // Set Editor Theme
    if (Setting.SaveInFile.getSettingString(Setting.Key.ThemeType, Setting.Default.ThemeType, this)
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
                Setting.Key.AceCodeEditorDarkTheme, Setting.Default.AceCodeEditorDarkTheme, this));
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
    } else if (Setting.SaveInFile.getSettingString(
            Setting.Key.ThemeType, Setting.Default.ThemeType, this)
        .equals(Setting.Value.System)) {
      int nightModeFlags =
          this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
      switch (nightModeFlags) {
        case Configuration.UI_MODE_NIGHT_YES:
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
          break;

        case Configuration.UI_MODE_NIGHT_NO:
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
          break;
      }
    }

    adapter = new FileTabAdapter(fileTabData, CodeEditorActivity.this);
    fileTab.setAdapter(adapter);
    fileTab.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

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
      root = TreeNode.root();
      fileTree(DrawerListDir);
      AndroidTreeView tView = new AndroidTreeView(this, root);
      TreeNodeWrapperView treeView =
          new TreeNodeWrapperView(this, com.unnamed.b.atv.R.style.TreeNodeStyle);
      tView.setDefaultAnimation(true);
      treeView.getNodeContainer().addView(tView.getView());
      ((LinearLayout) findViewById(R.id.list)).addView(treeView);
    }
    if (preview != null && openedFile != null) {
      if (FileManager.getPathFormat(openedFile.getAbsolutePath()).equals("md")) {
        preview.setVisible(true);
      } else {
        preview.setVisible(false);
      }
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

  @Override
  public boolean onCreateOptionsMenu(Menu arg0) {
    super.onCreateOptionsMenu(arg0);
    // TODO: Implement this method
    getMenuInflater().inflate(R.menu.activity_code_editor, arg0);
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu arg0) {
    // TODO: Implement this method
    menu = arg0;
    preview = arg0.findItem(R.id.preview);
    if (openedFile != null) {
      if (FileManager.getPathFormat(openedFile.getAbsolutePath()).equals("md")) {
        preview.setVisible(true);
      } else {
        preview.setVisible(false);
      }
    } else {
      preview.setVisible(false);
    }
    return super.onPrepareOptionsMenu(arg0);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem arg0) {
    // TODO: Implement this method
    if (arg0.getItemId() == R.id.preview) {
      save();
      if (openedFile != null) {
        if (codeEditor != null) {
          Intent i = new Intent();
          i.setClass(CodeEditorActivity.this, MarkdownViewer.class);
          i.putExtra("type", "file");
          i.putExtra("style", "github");
          i.putExtra("title", FileManager.getLatSegmentOfFilePath(openedFile.getAbsolutePath()));
          i.putExtra("data", openedFile.getAbsolutePath());
          startActivity(i);
        }
      }
    }

    return super.onOptionsItemSelected(arg0);
  }

  public void fileTree(File file) {
    if (file.isDirectory()) {
      ArrayList<File> list = new ArrayList<File>();
      for (File dir : file.listFiles()) {
        list.add(dir);
      }
      Collections.sort(list, new FileComparator2());
      for (int pos = 0; pos < list.size(); pos++) {
        TreeNode child = new TreeNode(list.get(pos));
        child.setViewHolder(new FileTreeViewHolder(this, this));
        root.addChild(child);
      }
    }

    /* if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (File dir : files) {
        TreeNode child = new TreeNode(dir);
        child.setViewHolder(new FileTreeViewHolder(this, CodeEditorActivity.this));
        root.addChild(child);
      }
    }*/
  }

  final class FileComparator2 implements Comparator<File> {
    public int compare(File f1, File f2) {
      if (f1 == f2) return 0;
      if (f1.isDirectory() && f2.isFile()) return -1;
      if (f1.isFile() && f2.isDirectory()) return 1;
      return f1.getAbsolutePath().compareToIgnoreCase(f2.getAbsolutePath());
    }
  }

  /*
  public void fileTree(File file, ViewGroup view) {
      if (view.getId() == R.id.list) {
          view.removeAllViews();
          LayoutInflater layoutInflator = getLayoutInflater();
          View layout = layoutInflator.inflate(R.layout.filelist, null);
          if (file.isDirectory()) {
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
                                              file,
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
          }
          FileIcon.setUpIcon(
                  CodeEditorActivity.this,
                  file.getAbsolutePath(),
                  (ImageView) layout.findViewById(R.id.icon));
          ((TextView) layout.findViewById(R.id.path))
                  .setText(FileManager.getLatSegmentOfFilePath(file.getAbsolutePath()));
          view.addView(layout);
      } else {
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
                                          layout.findViewById(R.id.child)
                                                  .setVisibility(View.VISIBLE);
                                          rotate.setTarget(
                                                  layout.findViewById(R.id.expandCollapse));
                                          rotate.setPropertyName("rotation");
                                          rotate.setFloatValues(0, 90);
                                          rotate.setDuration(200);
                                          rotate.setInterpolator(new LinearInterpolator());
                                          rotate.start();
                                      } else {
                                          layout.findViewById(R.id.child)
                                                  .setVisibility(View.GONE);
                                          ((LinearLayout) layout.findViewById(R.id.child))
                                                  .removeAllViews();
                                          rotate.setTarget(
                                                  layout.findViewById(R.id.expandCollapse));
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
                                      switch (FileTypeHandler.getFileFormat(
                                              fil.getAbsolutePath())) {
                                          case "java":
                                          case "xml":
                                          case "html":
                                          case "css":
                                          case "js":
                                          case "md":
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
  }
  */

  // Method to convert dp to pixels
  public static int dpToPx(Context context, int dp) {
    return (int)
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
  }

  public void openFileInEditor(File file) {
    if (!FileTabDataOperator.isContainsPath(fileTabData, file.getAbsolutePath())) {
      FileTabDataItem obj = new FileTabDataItem();
      obj.filePath = file.getAbsolutePath();
      FileTabDataOperator.addPath(fileTabData, obj);
    }
    adapter.fileTabData = fileTabData;
    adapter.setActiveTab(FileTabDataOperator.getPosition(fileTabData, file.getAbsolutePath()));
    adapter.notifyDataSetChanged();

    if (preview != null) {
      if (FileManager.getPathFormat(file.getAbsolutePath()).equals("md")) {
        preview.setVisible(true);
      } else {
        preview.setVisible(false);
      }
    }

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

  public class FileTabDataItem {
    public String filePath = "";
  }

  public class FileTabDataOperator {
    public static boolean isContainsPath(ArrayList<FileTabDataItem> data, String path) {
      for (int position = 0; position < data.size(); position++) {
        if (data.get(position).filePath.equals(path)) {
          return true;
        }
      }
      return false;
    }

    public static void removePath(ArrayList<FileTabDataItem> data, String path) {
      for (int position = 0; position < data.size(); position++) {
        if (data.get(position).filePath.equals(path)) {
          data.remove(position);
        }
      }
    }

    public static int getPosition(ArrayList<FileTabDataItem> data, String path) {
      for (int position = 0; position < data.size(); position++) {
        if (data.get(position).filePath.equals(path)) {
          return position;
        }
      }
      return -1;
    }

    public static void addPath(ArrayList<FileTabDataItem> data, FileTabDataItem obj) {
      if (!isContainsPath(data, obj.filePath)) {
        data.add(obj);
      }
    }
  }
}
