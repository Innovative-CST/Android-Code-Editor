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
import android.code.editor.files.utils.FileManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;

public class FileTabAdapter extends RecyclerView.Adapter<FileTabAdapter.ViewHolder> {
  public ArrayList<CodeEditorActivity.FileTabDataItem> fileTabData =
      new ArrayList<CodeEditorActivity.FileTabDataItem>();
  public CodeEditorActivity activity;
  public int activeTab;

  public FileTabAdapter(
      ArrayList<CodeEditorActivity.FileTabDataItem> _arr, CodeEditorActivity activity) {
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
    _view
        .findViewById(R.id.root)
        .setOnClickListener(
            (view) -> {
              activity.save();
              activity.openFileInEditor(new File(fileTabData.get(_position).filePath));
            });
    if (activeTab == _position) {
      ((ImageView) _view.findViewById(R.id.tabIndicator)).setVisibility(View.VISIBLE);
      _view
          .findViewById(R.id.root)
          .setOnClickListener(
              (view) -> {
                PopupMenu popupMenu = new PopupMenu(activity, _view.findViewById(R.id.root));
                Menu menu = popupMenu.getMenu();
                menu.add("Close this");
                menu.add("Close others");
                menu.add("Close All");

                popupMenu.setOnMenuItemClickListener(
                    item -> {
                      switch (item.getTitle().toString()) {
                        case "Close this":
                          int positionOfCloser =
                              CodeEditorActivity.FileTabDataOperator.getPosition(
                                  fileTabData, fileTabData.get(_position).filePath);
                          if (activeTab == positionOfCloser) {
                            activity.save();
                          }
                          fileTabData.remove(positionOfCloser);

                          if (fileTabData.size() != 0) {
                            if (positionOfCloser == 0) {
                              activity.adapter.setActiveTab(positionOfCloser);
                              activity.openFileInEditor(
                                  new File(fileTabData.get(positionOfCloser).filePath));
                            } else {
                              if (positionOfCloser + 1 > fileTabData.size()) {
                                activity.adapter.setActiveTab(positionOfCloser - 1);
                                activity.openFileInEditor(
                                    new File(fileTabData.get(positionOfCloser - 1).filePath));
                              } else {
                                activity.adapter.setActiveTab(positionOfCloser);
                                activity.openFileInEditor(
                                    new File(fileTabData.get(positionOfCloser).filePath));
                              }
                            }
                          } else {
                            activity.fileNotOpenedArea.setVisibility(View.VISIBLE);
                            activity.editorArea.setVisibility(View.GONE);
                          }
                          activity.fileTabData = fileTabData;
                          activity.adapter.notifyDataSetChanged();
                          break;
                        case "Close others":
                          CodeEditorActivity.FileTabDataItem fileTabItem =
                              fileTabData.get(
                                  CodeEditorActivity.FileTabDataOperator.getPosition(
                                      fileTabData, fileTabData.get(_position).filePath));
                          fileTabData.clear();
                          activeTab = 0;
                          fileTabData.add(fileTabItem);
                          activity.adapter.notifyDataSetChanged();
                          activity.fileTabData = fileTabData;
                          break;
                        case "Close All":
                          fileTabData.clear();
                          activeTab = 0;
                          activity.adapter.notifyDataSetChanged();
                          activity.fileTabData = fileTabData;
                          activity.fileNotOpenedArea.setVisibility(View.VISIBLE);
                          activity.editorArea.setVisibility(View.GONE);
                          break;
                      }
                      return true;
                    });
                popupMenu.show();
              });
    } else {
      ((ImageView) _view.findViewById(R.id.tabIndicator)).setVisibility(View.GONE);
    }
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

  public void setActiveTab(int pos) {
    activeTab = pos;
  }
}
