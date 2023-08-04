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

package android.code.editor.adapters.viewholders;

import android.code.editor.R;
import android.code.editor.activity.CodeEditorActivity;
import android.code.editor.files.utils.FileIcon;
import android.code.editor.files.utils.FileManager;
import android.code.editor.files.utils.FileTypeHandler;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.core.view.GravityCompat;
import androidx.transition.ChangeImageTransform;
import androidx.transition.TransitionManager;
import com.unnamed.b.atv.model.TreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileTreeViewHolder extends TreeNode.BaseNodeViewHolder<File> {
  public Context context;
  public View view;
  public ImageView icon;
  public ImageView expandCollapse;
  public TextView path;
  public CodeEditorActivity editorActivity;

  public FileTreeViewHolder(Context context, CodeEditorActivity editorActivity) {
    super(context);
    this.context = context;
    this.editorActivity = editorActivity;
  }

  @Override
  public View createNodeView(TreeNode node, File file) {
    view =
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
            .inflate(R.layout.layout_file_tree_view, null);
    view = applyPadding(node, (LinearLayout) view, CodeEditorActivity.dpToPx(context, 15));
    icon = view.findViewById(R.id.icon);
    expandCollapse = view.findViewById(R.id.expandCollapse);
    path = view.findViewById(R.id.path);
    FileIcon.setUpIcon(context, file.getAbsolutePath(), icon);
    if (file.isDirectory()) {
      expandCollapse.setVisibility(View.VISIBLE);
      updateExpandCollapseIcon(expandCollapse, node.isExpanded());
      view.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
              if (node.isExpanded()) {
                ExecutorService loadFiles = Executors.newSingleThreadExecutor();
                loadFiles.execute(
                    new Runnable() {
                      @Override
                      public void run() {
                        editorActivity.runOnUiThread(
                            new Runnable() {
                              @Override
                              public void run() {
                                ((ViewFlipper) view.findViewById(R.id.viewFlipper))
                                    .setDisplayedChild(1);
                                node.setExpanded(false);
                              }
                            });

                        node.children.clear();
                        editorActivity.runOnUiThread(
                            new Runnable() {
                              @Override
                              public void run() {
                                ((ViewFlipper) view.findViewById(R.id.viewFlipper))
                                    .setDisplayedChild(0);
                                getTreeView().collapseNode(node);
                                updateExpandCollapseIcon(expandCollapse, node.isExpanded());
                              }
                            });
                      }
                    });
              } else {
                ExecutorService loadFiles = Executors.newSingleThreadExecutor();
                loadFiles.execute(
                    new Runnable() {
                      @Override
                      public void run() {
                        editorActivity.runOnUiThread(
                            new Runnable() {
                              @Override
                              public void run() {
                                ((ViewFlipper) view.findViewById(R.id.viewFlipper))
                                    .setDisplayedChild(1);
                              }
                            });

                        listDirInNode(node, file);
                        editorActivity.runOnUiThread(
                            new Runnable() {
                              @Override
                              public void run() {
                                getTreeView().expandNode(node);
                                ((ViewFlipper) view.findViewById(R.id.viewFlipper))
                                    .setDisplayedChild(0);
                                updateExpandCollapseIcon(expandCollapse, node.isExpanded());
                              }
                            });
                      }
                    });
              }
            }
          });
    } else {
      expandCollapse.setVisibility(View.INVISIBLE);
      view.setOnClickListener(
          (view) -> {
            switch (FileTypeHandler.getFileFormat(file.getAbsolutePath())) {
              case "java":
              case "xml":
              case "html":
              case "css":
              case "js":
              case "md":
                editorActivity.save();
                editorActivity.openFileInEditor(file);
                editorActivity.drawer.closeDrawer(GravityCompat.END);
                break;
              default:
                break;
            }
          });
    }
    path.setText(FileManager.getLatSegmentOfFilePath(file.getAbsolutePath()));
    return view;
  }

  public void listDirInNode(TreeNode node, File file) {
    node.children.clear();
    ArrayList<File> list = new ArrayList<File>();
    for (File dir : file.listFiles()) {
      list.add(dir);
    }
    Collections.sort(list, new FileComparator());
    for (int pos = 0; pos < list.size(); pos++) {
      TreeNode child = new TreeNode(list.get(pos));
      child.setViewHolder(new FileTreeViewHolder(context, editorActivity));
      node.addChild(child);
    }
  }

  final class FileComparator implements Comparator<File> {
    public int compare(File f1, File f2) {
      if (f1 == f2) return 0;
      if (f1.isDirectory() && f2.isFile()) return -1;
      if (f1.isFile() && f2.isDirectory()) return 1;
      return f1.getAbsolutePath().compareToIgnoreCase(f2.getAbsolutePath());
    }
  }

  public LinearLayout applyPadding(
      final TreeNode node, final LinearLayout root, final int padding) {
    root.setPaddingRelative(
        root.getPaddingLeft() + (padding * (node.getLevel() - 1)),
        root.getPaddingTop(),
        root.getPaddingRight(),
        root.getPaddingBottom());
    return root;
  }

  public void updateExpandCollapseIcon(ImageView imageview, boolean isExpanded) {
    final int icon;
    if (isExpanded) {
      icon = R.drawable.chevron_down;
    } else {
      icon = R.drawable.chevron_right;
    }
    TransitionManager.beginDelayedTransition((ViewGroup) view, new ChangeImageTransform());
    expandCollapse.setImageResource(icon);
  }
}
