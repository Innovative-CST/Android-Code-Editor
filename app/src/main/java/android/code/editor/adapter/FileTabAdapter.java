package android.code.editor.adapter;

import android.app.Activity;
import android.code.editor.R;
import android.code.editor.activity.CodeEditorActivity;
import android.code.editor.files.utils.FileManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FileTabAdapter extends RecyclerView.Adapter<FileTabAdapter.ViewHolder> {
    public ArrayList<CodeEditorActivity.FileTabDataItem> fileTabData =
            new ArrayList<CodeEditorActivity.FileTabDataItem>();
    public Activity activity;

    public FileTabAdapter(ArrayList<CodeEditorActivity.FileTabDataItem> _arr, Activity activity) {
        this.activity = activity;
        fileTabData = _arr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater _inflater = activity.getLayoutInflater();
        View _v = _inflater.inflate(R.layout.layout_file_tab, null);
        RecyclerView.LayoutParams _lp =
                new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        _v.setLayoutParams(_lp);
        return new ViewHolder(_v);
    }

    @Override
    public void onBindViewHolder(ViewHolder _holder, final int _position) {
        View _view = _holder.itemView;
        ((TextView) _view.findViewById(R.id.fileName))
                .setText(FileManager.getLatSegmentOfFilePath(fileTabData.get(_position).filePath));
    }

    @Override
    public int getItemCount() {
        return fileTabData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }
}
