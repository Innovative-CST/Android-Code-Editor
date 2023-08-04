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

package android.code.editor.activity;

import android.code.editor.R;
import android.code.editor.files.utils.FileIcon;
import android.code.editor.files.utils.FileManager;
import android.code.editor.files.utils.FileTypeHandler;
import android.code.editor.utils.FileManagerActivity.FileCreatorDialog;
import android.code.editor.utils.FileManagerActivity.FolderCreatorDialog;
import android.code.editor.utils.FileManagerActivity.ProjectCreatorDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileManagerActivity extends BaseActivity {
    private ProgressBar progressbar;
    private RecyclerView list;
    private FileList filelist;
    private String initialDir;
    public String currentDir;

    public ArrayList<String> listString = new ArrayList<>();
    public ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set Layout in Activity
        setContentView(R.layout.activity_file_manager);
        initialDir = getIntent().getStringExtra("path");
        currentDir = getIntent().getStringExtra("path");
        initActivity();
        loadFileList(initialDir);
    }

    public void initActivity() {
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu arg0) {
        super.onCreateOptionsMenu(arg0);
        getMenuInflater().inflate(R.menu.filemanager_activity_menu, arg0);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu arg0) {
        MenuItem item = arg0.findItem(R.id.menu_main_setting);
        Drawable icon =
                getResources()
                        .getDrawable(
                                R.drawable.more_vert_fill0_wght400_grad0_opsz48, this.getTheme());
        item.setIcon(icon);
        return super.onPrepareOptionsMenu(arg0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem arg0) {
        if (arg0.getItemId() == R.id.menu_main_setting) {
            PopupMenu popupMenu =
                    new PopupMenu(FileManagerActivity.this, findViewById(R.id.menu_main_setting));
            Menu menu = popupMenu.getMenu();
            menu.add("New folder");
            menu.add("New file");
            menu.add("New Project");
            menu.add("Open Source Licenses");
            menu.add("Contributors");
            menu.add("Settings");

            popupMenu.setOnMenuItemClickListener(
                    item -> {
                        switch (item.getTitle().toString()) {
                            case "New Project":
                                ProjectCreatorDialog projectDialog =
                                        new ProjectCreatorDialog(
                                                this,
                                                currentDir,
                                                new ProjectCreatorDialog.onProjectListUpdate() {
                                                    @Override
                                                    public void onRefresh() {
                                                        listMap.clear();
                                                        listString.clear();
                                                        loadFileList(currentDir);
                                                    }
                                                });
                                projectDialog.show();
                                break;
                            case "New folder":
                                new FolderCreatorDialog(this);
                                break;
                            case "New file":
                                new FileCreatorDialog(this);
                                break;
                            case "Contributors":
                                Intent intent = new Intent();
                                intent.setClass(
                                        FileManagerActivity.this, ContributorsActivity.class);
                                startActivity(intent);
                                break;
                            case "Settings":
                                Intent setting = new Intent();
                                setting.setClass(FileManagerActivity.this, SettingActivity.class);
                                startActivity(setting);
                                break;
                            case "Open Source Licenses":
                                Intent license = new Intent();
                                license.setClass(FileManagerActivity.this, LicenseActivity.class);
                                startActivity(license);
                                break;
                        }
                        return true;
                    });
            popupMenu.show();
        }
        return super.onOptionsItemSelected(arg0);
    }

    private void initViews() {
        // Setup toolbar
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(
                (view) -> {
                    Intent intent = new Intent();
                    intent.putExtra("path", currentDir);
                    intent.setClass(FileManagerActivity.this, CodeEditorActivity.class);
                    startActivity(intent);
                });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        onBackPressed();
                    }
                });
        // Define view
        list = findViewById(R.id.list);
        progressbar = findViewById(R.id.progressbar);
    }

    public void loadFileList(String path) {
        listString.clear();
        listMap.clear();
        currentDir = path;
        ExecutorService loadFileList = Executors.newSingleThreadExecutor();

        loadFileList.execute(
                new Runnable() {
                    @Override
                    public void run() {
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

                        runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        // Set Data in list
                                        progressbar.setVisibility(View.GONE);
                                        filelist = new FileList(listMap);
                                        list.setAdapter(filelist);
                                        list.setLayoutManager(
                                                new LinearLayoutManager(FileManagerActivity.this));
                                    }
                                });
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (initialDir.equals(currentDir)) {
            finishAffinity();
        } else {
            loadFileList(new File(currentDir).getParent());
        }
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
            if (viewType == 1) {
                LayoutInflater _inflater = getLayoutInflater();
                View _v = _inflater.inflate(R.layout.report_issues, null);
                RecyclerView.LayoutParams _lp =
                        new RecyclerView.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                _v.setLayoutParams(_lp);
                return new ViewHolder(_v);
            }
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
            if (_position != 0) {
                View _view = _holder.itemView;
                mainlayout = _view.findViewById(R.id.layout);
                icon = _view.findViewById(R.id.icon);
                path = _view.findViewById(R.id.path);
                FileIcon.setUpIcon(
                        FileManagerActivity.this,
                        _data.get(_position - 1).get("path").toString(),
                        icon);
                path.setText(_data.get(_position - 1).get("lastSegmentOfFilePath").toString());
                String path = _data.get(_position - 1).get("path").toString();
                FileTypeHandler fileTypeHandler =
                        new FileTypeHandler(FileManagerActivity.this, FileManagerActivity.this);
                fileTypeHandler.handleFile(new File(path));
                fileTypeHandler.setTargetView(mainlayout);
                fileTypeHandler.startHandling();
            } else {
                View _view = _holder.itemView;
                ((TextView) _view.findViewById(R.id.text)).setAutoLinkMask(Linkify.WEB_URLS);
                ((TextView) _view.findViewById(R.id.text))
                        .setMovementMethod(LinkMovementMethod.getInstance());
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 1;
            }
            return 0;
        }

        @Override
        public int getItemCount() {
            return _data.size() + 1;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }
    }
}
