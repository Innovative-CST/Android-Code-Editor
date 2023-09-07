/*
 *  This file is part of Android Code Editor.
 *
 *  Android Code Editor is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Android Code Editor is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package android.code.editor.ui.activities;

import android.code.editor.R;
import android.code.editor.common.utils.FileUtils;
import android.code.editor.databinding.ActivityCodeEditorBinding;
import android.code.editor.handlers.FileTypeHandler;
import android.code.editor.interfaces.TaskListener;
import android.code.editor.ui.adapters.FileTabAdapter;
import android.code.editor.ui.viewholders.FileTreeViewHolder;
import android.code.editor.utils.FileTabDataOperator;
import android.code.editor.utils.LanguageModeHandler;
import android.code.editor.utils.Setting;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.MainThread;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.unnamed.b.atv.view.TreeNodeWrapperView;
import editor.tsd.tools.EditorListeners;
import editor.tsd.widget.CodeEditorLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CodeEditorActivity extends BaseActivity {

  public CodeEditorLayout codeEditor;
  public File DrawerListDir;
  public String selectPath;
  public DrawerLayout drawer;
  public File openedFile;
  public Menu menu;
  public MenuItem preview;
  public TreeNode root;
  public RecyclerView fileTab;
  public FileTabAdapter adapter;
  public ArrayList<FileTabDataItem> fileTabData = new ArrayList<FileTabDataItem>();

  public ActivityCodeEditorBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityCodeEditorBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    initActivity();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    binding = null;
  }

  @Override
  protected void onPause() {
    save();
    super.onPause();
  }

  @Override
  @MainThread
  public void onBackPressed() {
    showLoadingDialog();
    save(
        new TaskListener() {
          @Override
          public void onTaskComplete() {
            dismissLoading();
            finish();
          }
        });
  }

  public void save() {
    if (openedFile != null) {
      if (codeEditor != null) {
        EditorListeners listener =
            new EditorListeners() {
              @Override
              public void onReceviedCode(String code) {
                FileUtils.writeFile(openedFile.getAbsolutePath(), code);
              }
            };
        codeEditor.getCode(listener);
      }
    }
  }

  public void save(TaskListener taskCompleteListener) {
    if (openedFile != null) {
      if (codeEditor != null) {
        EditorListeners listener =
            new EditorListeners() {
              @Override
              public void onReceviedCode(String code) {
                ExecutorService backgroundTask = Executors.newSingleThreadExecutor();
                backgroundTask.execute(
                    new Runnable() {
                      @Override
                      public void run() {
                        FileUtils.writeFile(openedFile.getAbsolutePath(), code);
                        runOnUiThread(
                            new Runnable() {
                              @Override
                              public void run() {
                                taskCompleteListener.onTaskComplete();
                              }
                            });
                      }
                    });
              }
            };
        codeEditor.getCode(listener);
      } else {
        taskCompleteListener.onTaskComplete();
      }
    } else {
      taskCompleteListener.onTaskComplete();
    }
  }

  @Override
  public void onConfigurationChanged(Configuration arg0) {
    super.onConfigurationChanged(arg0);
    save();
    recreate();
  }

  public void initActivity() {
    fileTab = binding.fileTab;
    Toolbar toolbar = binding.toolbar;
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setTitle(R.string.code_editor);
    drawer = binding.drawer;
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
    toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View arg0) {
            if (drawer.isDrawerVisible(GravityCompat.END)) {
              drawer.closeDrawer(GravityCompat.END);
            } else {
              drawer.openDrawer(GravityCompat.END);
            }
          }
        });
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    binding.editorController.moveLeft.setOnClickListener(
        (view) -> {
          codeEditor.moveCursorHorizontally(-1);
        });
    binding.editorController.moveLeft.setOnTouchListener(
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
    binding.editorController.moveRight.setOnClickListener(
        (view) -> {
          codeEditor.moveCursorHorizontally(1);
        });
    binding.editorController.moveRight.setOnTouchListener(
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
    binding.editorController.moveUp.setOnClickListener(
        (view) -> {
          codeEditor.moveCursorVertically(-1);
        });
    binding.editorController.moveUp.setOnTouchListener(
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
    binding.editorController.moveDown.setOnClickListener(
        (view) -> {
          codeEditor.moveCursorVertically(1);
        });
    binding.editorController.moveDown.setOnTouchListener(
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
        if (binding.fileNotOpenedArea.getVisibility() == View.GONE
            || binding.editorArea.getVisibility() == View.VISIBLE) {
          binding.fileNotOpenedArea.setVisibility(View.VISIBLE);
          binding.editorArea.setVisibility(View.GONE);
        }
      }
      root = TreeNode.root();
      fileTree(DrawerListDir);
      AndroidTreeView tView = new AndroidTreeView(this, root);
      TreeNodeWrapperView treeView =
          new TreeNodeWrapperView(this, com.unnamed.b.atv.R.style.TreeNodeStyle);
      tView.setDefaultAnimation(true);
      treeView.getNodeContainer().addView(tView.getView());
      binding.list.addView(treeView);
    }
    if (preview != null && openedFile != null) {
      if (FileUtils.getPathFormat(openedFile.getAbsolutePath()).equals("md")
          || FileUtils.getPathFormat(openedFile.getAbsolutePath()).equals("html")) {
        preview.setVisible(true);
      } else {
        preview.setVisible(false);
      }
    }

    if (codeEditor != null) {
      initEditor();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu arg0) {
    super.onCreateOptionsMenu(arg0);
    getMenuInflater().inflate(R.menu.activity_code_editor, arg0);
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu arg0) {
    menu = arg0;
    preview = arg0.findItem(R.id.preview);
    if (openedFile != null) {
      if (FileUtils.getPathFormat(openedFile.getAbsolutePath()).equals("md")
          || FileUtils.getPathFormat(openedFile.getAbsolutePath()).equals("html")) {
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
    if (arg0.getItemId() == R.id.preview) {
      save();
      if (openedFile != null) {
        if (codeEditor != null) {
          if (FileUtils.getPathFormat(openedFile.getAbsolutePath()).equals("md")) {
            Intent i = new Intent();
            i.setClass(CodeEditorActivity.this, MarkdownViewer.class);
            i.putExtra("type", "file");
            i.putExtra("style", "github");
            i.putExtra("title", FileUtils.getLatSegmentOfFilePath(openedFile.getAbsolutePath()));
            i.putExtra("data", openedFile.getAbsolutePath());
            startActivity(i);
          } else {
            Intent i = new Intent();
            i.setClass(CodeEditorActivity.this, WebViewActivity.class);
            i.putExtra("type", "file");
            i.putExtra("root", selectPath);
            i.putExtra("data", openedFile.getAbsolutePath());
            startActivity(i);
          }
        }
      }
    }

    return super.onOptionsItemSelected(arg0);
  }

  public void fileTree(File file) {
    if (file.isDirectory()) {
      TreeNode child = new TreeNode(file);
      child.setViewHolder(new FileTreeViewHolder(this, this));
      root.addChild(child);
    }
  }

  public void openFileInEditor(File file) {
    showLoadingDialog();
    save(
        new TaskListener() {
          @Override
          public void onTaskComplete() {
            if (!FileTabDataOperator.isContainsPath(fileTabData, file.getAbsolutePath())) {
              FileTabDataItem obj = new FileTabDataItem();
              obj.filePath = file.getAbsolutePath();
              binding.editorCont.removeAllViews();
              ViewGroup.LayoutParams layoutParams =
                  new ViewGroup.LayoutParams(
                      ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
              codeEditor = new CodeEditorLayout(CodeEditorActivity.this);
              codeEditor.setLayoutParams(layoutParams);
              obj.editor = codeEditor;
              binding.editorCont.addView(codeEditor);
              FileTabDataOperator.addPath(fileTabData, obj);
              initEditor();
            } else {
              CodeEditorActivity.this.codeEditor = null;
              binding.editorCont.removeAllViews();
              CodeEditorActivity.this.codeEditor =
                  fileTabData.get(
                          FileTabDataOperator.getPosition(fileTabData, file.getAbsolutePath()))
                      .editor;
              binding.editorCont.addView(codeEditor);
            }
            adapter.fileTabData = fileTabData;
            adapter.setActiveTab(
                FileTabDataOperator.getPosition(fileTabData, file.getAbsolutePath()));
            adapter.notifyDataSetChanged();

            if (preview != null) {
              if (FileUtils.getPathFormat(file.getAbsolutePath()).equals("md")
                  || FileUtils.getPathFormat(file.getAbsolutePath()).equals("html")) {
                preview.setVisible(true);
              } else {
                preview.setVisible(false);
              }
            }

            binding.fileNotOpenedArea.setVisibility(View.GONE);
            binding.editorArea.setVisibility(View.VISIBLE);
            binding.progressbar.setVisibility(View.VISIBLE);
            codeEditor.setVisibility(View.GONE);

            openedFile = file;
            codeEditor.setCode(FileUtils.readFile(file.getAbsolutePath()));

            binding.progressbar.setVisibility(View.GONE);
            codeEditor.setVisibility(View.VISIBLE);

            codeEditor.setLanguageMode(
                LanguageModeHandler.getLanguageModeForExtension(
                    FileTypeHandler.getFileFormat(file.getAbsolutePath())));
            dismissLoading();
          }
        });
  }

  public void initEditor() {
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
  }

  public class FileTabDataItem {
    public String filePath = "";
    public CodeEditorLayout editor;
  }

  private AlertDialog loading;

  public void showLoadingDialog() {
    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    dialog.setView(getLayoutInflater().inflate(R.layout.layout_loading_progress, null));
    dialog.setCancelable(false);
    loading = dialog.create();
    loading.show();
  }

  public void dismissLoading() {
    loading.dismiss();
  }
}
