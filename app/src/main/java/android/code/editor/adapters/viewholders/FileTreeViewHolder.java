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
import androidx.core.view.GravityCompat;
import androidx.transition.ChangeImageTransform;
import androidx.transition.TransitionManager;
import com.unnamed.b.atv.model.TreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
      updateExpandCollapseIcon(expandCollapse, node.isExpanded());
      expandCollapse.setVisibility(View.VISIBLE);
      view.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
              if (node.isExpanded()) {
                node.setExpanded(false);
                node.children.clear();
                getTreeView().collapseNode(node);
                updateExpandCollapseIcon(expandCollapse, node.isExpanded());
              } else {
                listDirInNode(node, file);
                getTreeView().expandNode(node);
                updateExpandCollapseIcon(expandCollapse, node.isExpanded());
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
