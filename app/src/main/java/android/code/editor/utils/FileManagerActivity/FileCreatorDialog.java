package android.code.editor.utils.FileManagerActivity;

import android.code.editor.activity.FileManagerActivity;
import android.code.editor.activity.LicenseActivity;
import android.code.editor.files.utils.FileManager;
import android.code.editor.utils.FileManagerActivity.ProjectCreatorDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import java.io.File;

public class FileCreatorDialog {
  public FileCreatorDialog(FileManagerActivity activity) {
    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
    dialog.setTitle("Create new file");
    dialog.setMessage("Enter file name to create");
    ViewGroup nameCont =
        (LinearLayout)
            activity
                .getLayoutInflater()
                .inflate(android.code.editor.R.layout.layout_edittext_dialog, null);
    EditText path = nameCont.findViewById(android.code.editor.R.id.edittext1);
    TextInputLayout textInputLayout =
        nameCont.findViewById(android.code.editor.R.id.TextInputLayout1);
    textInputLayout.setHint("Enter file name");
    path.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void afterTextChanged(Editable arg0) {}

          @Override
          public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            if (path.getText().length() == 0) {
              path.setError("Please enter a file name");
            } else if (path.getText()
                .toString()
                .substring(path.getText().toString().length() - 1)
                .equals(".")) {
              path.setError("Please enter a valid name");
            } else if (new File(
                    activity.currentDir.concat(File.separator).concat(path.getText().toString()))
                .exists()) {
              path.setError("Please enter a file name that does not exists");
            } else {
              path.setError(null);
            }
          }

          @Override
          public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
        });
    dialog.setView(nameCont);
    dialog.setPositiveButton(
        "Create",
        (param0, param1) -> {
          if (path.getText().length() == 0) {
            Toast.makeText(activity, "Please enter a file name", Toast.LENGTH_SHORT).show();
          } else if (path.getText()
              .toString()
              .substring(path.getText().toString().length() - 1)
              .equals(".")) {
            Toast.makeText(activity, "Please enter a valid name", Toast.LENGTH_SHORT).show();
          } else if (!new File(
                  activity.currentDir.concat(File.separator).concat(path.getText().toString()))
              .exists()) {
            activity.listMap.clear();
            activity.listString.clear();
            FileManager.writeFile(
                activity.currentDir.concat(File.separator).concat(path.getText().toString()),
                getFileTemplate(
                    new File(
                        activity
                            .currentDir
                            .concat(File.separator)
                            .concat(path.getText().toString())),
                    activity));
            activity.loadFileList(activity.currentDir);
          } else if (new File(
                  activity.currentDir.concat(File.separator).concat(path.getText().toString()))
              .exists()) {
            Toast.makeText(
                    activity, "Please enter a file name that does not exists", Toast.LENGTH_SHORT)
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

  public static String getFileTemplate(File path, Context context) {
    String content = "";
    String pathToCopyText = "";
    switch (FileManager.getPathFormat(path.getAbsolutePath())) {
      case "html":
        pathToCopyText = "Templates/NewFiles/template_01.html";
        content = LicenseActivity.readFileFromAssets(context.getAssets(), pathToCopyText);
        content =
            content.replace(
                "${Project_Name}",
                FileManager.getLatSegmentOfFilePath(path.getParentFile().getAbsolutePath()));
        break;
      case "css":
        pathToCopyText = "Templates/NewFiles/template_02.css";
        content = LicenseActivity.readFileFromAssets(context.getAssets(), pathToCopyText);
        break;
      case "js":
        pathToCopyText = "Templates/NewFiles/template_03.js";
        content = LicenseActivity.readFileFromAssets(context.getAssets(), pathToCopyText);
        break;
    }
    return content;
  }
}
