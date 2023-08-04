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

package android.code.editor.utils.FileManagerActivity;

import android.code.editor.activity.FileManagerActivity;
import android.code.editor.files.utils.FileManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import java.io.File;

public class FolderCreatorDialog {
  public FolderCreatorDialog(FileManagerActivity activity) {
    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
    dialog.setTitle("Create new folder");
    dialog.setMessage("Enter folder name to create");
    ViewGroup nameCont =
        (LinearLayout)
            activity
                .getLayoutInflater()
                .inflate(android.code.editor.R.layout.layout_edittext_dialog, null);
    EditText path = nameCont.findViewById(android.code.editor.R.id.edittext1);
    TextInputLayout textInputLayout =
        nameCont.findViewById(android.code.editor.R.id.TextInputLayout1);
    textInputLayout.setHint("Enter folder name");
    path.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void afterTextChanged(Editable arg0) {}

          @Override
          public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            if (path.getText().length() == 0) {
              path.setError("Please enter a folder name");
            } else if (new File(
                    activity.currentDir.concat(File.separator).concat(path.getText().toString()))
                .exists()) {
              path.setError("Please enter a folder that does not exists");
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
            Toast.makeText(activity, "Please enter a folder name", Toast.LENGTH_SHORT).show();
          } else if (!new File(
                  activity.currentDir.concat(File.separator).concat(path.getText().toString()))
              .exists()) {
            activity.listMap.clear();
            activity.listString.clear();
            FileManager.makeDir(
                activity.currentDir.concat(File.separator).concat(path.getText().toString()));
            activity.loadFileList(activity.currentDir);
          } else {
            Toast.makeText(
                    activity, "Please enter a folder that does not exists", Toast.LENGTH_SHORT)
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
