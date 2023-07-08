package android.code.editor.utils;

import android.code.editor.activity.FileManagerActivity;
import android.code.editor.files.utils.FileManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

public class FolderCreatorDialog {
    public FolderCreatorDialog(FileManagerActivity activity) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
        dialog.setTitle("Create new folder");
        dialog.setMessage("Enter folder name to create");
        TextInputLayout nameCont = new TextInputLayout(activity);
        TextInputEditText path = new TextInputEditText(activity);
        path.setHint("Enter folder name");
        nameCont.addView(path);
        path.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO: Implement this method
                    }

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO: Implement this method
                        if (path.getText().length() == 0) {
                            path.setError("Please enter a folder name");
                        } else if (new File(
                                        activity.getIntent()
                                                .getStringExtra("path")
                                                .concat(File.separator)
                                                .concat(path.getText().toString()))
                                .exists()) {
                            path.setError("Please enter a folder that does not exists");
                        } else {
                            path.setError(null);
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO: Implement this method
                    }
                });
        dialog.setView(nameCont);
        dialog.setPositiveButton(
                "Create",
                (param0, param1) -> {
                    if (path.getText().length() == 0) {
                        Toast.makeText(activity, "Please enter a folder name", Toast.LENGTH_SHORT)
                                .show();
                    } else if (!new File(
                                    activity.getIntent()
                                            .getStringExtra("path")
                                            .concat(File.separator)
                                            .concat(path.getText().toString()))
                            .exists()) {
                        activity.listMap.clear();
                        activity.listString.clear();
                        FileManager.makeDir(
                                activity.getIntent()
                                        .getStringExtra("path")
                                        .concat(File.separator)
                                        .concat(path.getText().toString()));
                        activity.loadFileList(activity.getIntent().getStringExtra("path"));
                    } else {
                        Toast.makeText(
                                        activity,
                                        "Please enter a folder that does not exists",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        dialog.setNegativeButton(
                "Cancel",
                (param0, param1) -> {
                    dialog.create().dismiss();
                });
        dialog.create().show();
    }
}
