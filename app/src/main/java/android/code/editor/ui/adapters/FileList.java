package android.code.editor.ui.adapters;

import android.code.editor.R;
import android.code.editor.handlers.FileTypeHandler;
import android.code.editor.ui.activities.FileManagerActivity;
import android.code.editor.utils.FileIcon;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class FileList extends RecyclerView.Adapter<FileList.ViewHolder> {

  ArrayList<HashMap<String, Object>> _data;
  private ImageView icon;
  private ImageView gitIcon;
  private TextView path;
  private LinearLayout mainlayout;
  private Context context;

  public FileList(ArrayList<HashMap<String, Object>> _arr, Context context) {
    _data = _arr;
    this.context = context;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == 1) {
      LayoutInflater _inflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View _v = _inflater.inflate(R.layout.report_issues, null);
      RecyclerView.LayoutParams _lp =
          new RecyclerView.LayoutParams(
              ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      _v.setLayoutParams(_lp);
      return new ViewHolder(_v);
    }
    LayoutInflater _inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View _v = _inflater.inflate(R.layout.filelist, null);
    RecyclerView.LayoutParams _lp =
        new RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    _v.setLayoutParams(_lp);
    return new ViewHolder(_v);
  }

  @Override
  public void onBindViewHolder(ViewHolder _holder, final int _position) {
    if (_position != 0) {
      View _view = _holder.itemView;
      mainlayout = _view.findViewById(R.id.layout);
      icon = _view.findViewById(R.id.icon);
      path = _view.findViewById(R.id.path);
      FileIcon.setUpIcon(context, _data.get(_position - 1).get("path").toString(), icon);
      path.setText(_data.get(_position - 1).get("lastSegmentOfFilePath").toString());
      String path = _data.get(_position - 1).get("path").toString();
      FileTypeHandler fileTypeHandler = new FileTypeHandler(context, (FileManagerActivity) context);
      fileTypeHandler.handleFile(new File(path));
      fileTypeHandler.setTargetView(mainlayout);
      fileTypeHandler.startHandling();
    } else {
      View _view = _holder.itemView;
      ((TextView) _view.findViewById(R.id.text)).setAutoLinkMask(Linkify.WEB_URLS);
      ((TextView) _view.findViewById(R.id.text))
          .setMovementMethod(LinkMovementMethod.getInstance());
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return 1;
    }
    return 0;
  }

  @Override
  public int getItemCount() {
    return _data.size() + 1;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View v) {
      super(v);
    }
  }
}
