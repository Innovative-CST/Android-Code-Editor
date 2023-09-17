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
 *   along with Android Code Editor.  If not, see <https://www.gnu.org/licenses/>.
 */

package android.code.editor.ui.dialogs;

import android.code.editor.R;
import android.code.editor.common.utils.FileUtils;
import android.code.editor.ui.activities.FileManagerActivity;
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
    dialog.setTitle(R.string.create_new_folder);
    dialog.setMessage(R.string.to_create_folder_enter_name);
    ViewGroup nameCont =
        (LinearLayout)
            activity
                .getLayoutInflater()
                .inflate(android.code.editor.R.layout.layout_edittext_dialog, null);
    EditText path = nameCont.findViewById(android.code.editor.R.id.edittext1);
    TextInputLayout textInputLayout =
        nameCont.findViewById(android.code.editor.R.id.TextInputLayout1);
    textInputLayout.setHint(R.string.enter_folder_name);
    path.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void afterTextChanged(Editable arg0) {}

          @Override
          public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            if (path.getText().length() == 0) {
              path.setError(activity.getString(R.string.please_enter_a_folder_name));
            } else if (new File(
                    activity.currentDir.concat(File.separator).concat(path.getText().toString()))
                .exists()) {
              path.setError(
                  activity.getString(R.string.please_enter_a_folder_name_that_does_not_exists));
            } else {
              path.setError(null);
            }
          }

          @Override
          public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
        });
    dialog.setView(nameCont);
    dialog.setPositiveButton(
        R.string.create,
        (param0, param1) -> {
          if (path.getText().length() == 0) {
            Toast.makeText(activity, R.string.please_enter_a_folder_name, Toast.LENGTH_SHORT)
                .show();
          } else if (!new File(
                  activity.currentDir.concat(File.separator).concat(path.getText().toString()))
              .exists()) {
            activity.listMap.clear();
            activity.listString.clear();
            FileUtils.makeDir(
                activity.currentDir.concat(File.separator).concat(path.getText().toString()));
            activity.loadFileList(activity.currentDir);
          } else {
            Toast.makeText(
                    activity,
                    R.string.please_enter_a_folder_name_that_does_not_exists,
                    Toast.LENGTH_SHORT)
                .show();
          }
        });
    dialog.setNegativeButton(
        R.string.cancel,
        (param0, param1) -> {
          dialog.create().dismiss();
        });
    dialog.create().show();
  }
}
