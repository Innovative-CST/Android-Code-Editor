package android.code.editor;

import android.code.editor.files.utils.FileIcon;
import android.code.editor.files.utils.FileManager;
import android.code.editor.files.utils.FileTypeHandler;
import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.ui.Utils;
import android.code.editor.utils.FolderCreatorDialog;
import android.code.editor.utils.Git;
import android.code.editor.utils.ProjectCreatorDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileManagerActivity extends AppCompatActivity {
    private ProgressBar progressbar;
    private RecyclerView list;
    private FileList filelist;

    public ArrayList<String> listString = new ArrayList<>();
    public ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaterialColorHelper.setUpTheme(this);
        // Set Layout in Activity
        setContentView(R.layout.activity_file_manager);
        initActivity();
        loadFileList(getIntent().getStringExtra("path"));
    }

    public void initActivity() {
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu arg0) {
        super.onCreateOptionsMenu(arg0);
        // TODO: Implement this method
        getMenuInflater().inflate(R.menu.filemanager_activity_menu, arg0);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu arg0) {
        // TODO: Implement this method
        MenuItem item = arg0.findItem(R.id.menu_main_setting);
        Drawable icon =
                getResources()
                        .getDrawable(
                                R.drawable.more_vert_fill0_wght400_grad0_opsz48, this.getTheme());
        icon.setColorFilter(
                MaterialColorHelper.getMaterialColor(
                        this, com.google.android.material.R.attr.colorOnPrimary),
                PorterDuff.Mode.SRC_IN);

        item.setIcon(icon);
        return super.onPrepareOptionsMenu(arg0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem arg0) {
        // TODO: Implement this method
        if (arg0.getItemId() == R.id.menu_main_setting) {
            PopupMenu popupMenu =
                    new PopupMenu(FileManagerActivity.this, findViewById(R.id.menu_main_setting));
            Menu menu = popupMenu.getMenu();
            menu.add("New folder");
            menu.add("New Project");
            menu.add("Contributors");
            menu.add("Settings");

            popupMenu.setOnMenuItemClickListener(
                    item -> {
                        switch (item.getTitle().toString()) {
                            case "New Project":
                                ProjectCreatorDialog projectDialog =
                                        new ProjectCreatorDialog(
                                                this,
                                                getIntent().getStringExtra("path"),
                                                new ProjectCreatorDialog.onProjectListUpdate() {
                                                    @Override
                                                    public void onRefresh() {
                                                        listMap.clear();
                                                        listString.clear();
                                                        loadFileList(
                                                                getIntent().getStringExtra("path"));
                                                    }
                                                });
                                projectDialog.show();
                                break;
                            case "New folder":
                                new FolderCreatorDialog(this);
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
                        }
                        return true;
                    });
            popupMenu.show();
        }
        return super.onOptionsItemSelected(arg0);
    }

    private static void onProjectListUpdate() {
        // TODO: Implement this method

    }

    private void initViews() {
        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitleTextColor(
                MaterialColorHelper.getMaterialColor(
                        this, com.google.android.material.R.attr.colorOnPrimary));
        toolbar.getNavigationIcon()
                .setTint(
                        MaterialColorHelper.getMaterialColor(
                                this, com.google.android.material.R.attr.colorOnPrimary));
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO: Implement this method
                        onBackPressed();
                    }
                });
        // Define view
        list = findViewById(R.id.list);
        progressbar = findViewById(R.id.progressbar);
    }

    public void loadFileList(String path) {
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
        super.onBackPressed();
        // TODO: Implement this method
        if (Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                .equals(getIntent().getStringExtra("path"))) {
            finishAffinity();
        } else {
            finish();
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
            FileIcon.setUpIcon(
                    FileManagerActivity.this, _data.get(_position).get("path").toString(), icon);
            Utils.applyRippleEffect(
                    mainlayout,
                    MaterialColorHelper.getMaterialColor(
                            FileManagerActivity.this,
                            com.google.android.material.R.attr.colorSurface),
                    MaterialColorHelper.getMaterialColor(
                            FileManagerActivity.this,
                            com.google.android.material.R.attr.colorOnSurface));
            path.setText(_data.get(_position).get("lastSegmentOfFilePath").toString());
            String path = _data.get(_position).get("path").toString();
            FileTypeHandler fileTypeHandler = new FileTypeHandler(FileManagerActivity.this);
            fileTypeHandler.handleFile(new File(path));
            fileTypeHandler.setTargetView(mainlayout);
            fileTypeHandler.startHandling();
            if (new File(_data.get(_position).get("path").toString()).isDirectory()) {
                if (Git.isGitRepository(new File(new File(_data.get(_position).get("path").toString()), ".git"))) {
                    gitIcon.setVisibility(View.VISIBLE);
                }
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
