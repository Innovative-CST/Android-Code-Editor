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

package android.code.editor.ui.bottomsheet.editor;

import android.code.editor.databinding.LayoutFileOperationCodeEditorBinding;
import android.code.editor.interfaces.PathCreationListener;
import android.code.editor.ui.activities.CodeEditorActivity;
import android.code.editor.ui.dialogs.editor.FileCreatorDialog;
import android.code.editor.ui.viewholders.FileTreeViewHolder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.unnamed.b.atv.model.TreeNode;
import java.io.File;

public class FileOperationBottomSheet extends BottomSheetDialogFragment {

  public File path;
  public CodeEditorActivity activity;
  public TreeNode node;
  public LayoutFileOperationCodeEditorBinding binding;

  public FileOperationBottomSheet(File path, CodeEditorActivity activity, TreeNode node) {
    this.path = path;
    this.activity = activity;
    this.node = node;
  }

  @Override
  public View onCreateView(LayoutInflater inflator, ViewGroup layout, Bundle bundle) {
    binding = LayoutFileOperationCodeEditorBinding.inflate(inflator);
    if (path.isFile()) {
      binding.createFile.setVisibility(View.GONE);
    } else {
      binding.createFile.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
              new FileCreatorDialog(
                  activity,
                  path,
                  new PathCreationListener() {
                    @Override
                    public void onPathCreated(File path) {
                      TreeNode child = new TreeNode(path);
                      child.setViewHolder(new FileTreeViewHolder(activity, activity));
                      node.getViewHolder().getTreeView().addNode(node, child);
                    }
                  });
            }
          });
    }

    return binding.getRoot();
  }
}
