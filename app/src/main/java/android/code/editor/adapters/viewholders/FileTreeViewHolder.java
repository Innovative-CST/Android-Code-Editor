package android.code.editor.adapters.viewholders;

import android.code.editor.R;
import android.code.editor.files.utils.FileIcon;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.transition.ChangeImageTransform;
import androidx.transition.TransitionManager;
import com.unnamed.b.atv.model.TreeNode;
import java.io.File;

public class FileTreeViewHolder extends TreeNode.BaseNodeViewHolder<File> {
    public Context context;
    public View view;
    public ImageView icon;
    public ImageView expandCollapse;
    public TextView path;

    public FileTreeViewHolder(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View createNodeView(TreeNode node, File file) {
        view =
                ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.layout_file_tree_view, null);
        icon = view.findViewById(R.id.icon);
        expandCollapse = view.findViewById(R.id.expandCollapse);
        path = view.findViewById(R.id.path);
        FileIcon.setUpIcon(context, file.getAbsolutePath(), icon);
        if (file.isDirectory()) {
            updateExpandCollapseIcon(expandCollapse, node.isExpanded());
            expandCollapse.setVisibility(View.VISIBLE);
        } else {
            expandCollapse.setVisibility(View.INVISIBLE);
        }
        return view;
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
