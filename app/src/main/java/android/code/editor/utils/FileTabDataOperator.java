package android.code.editor.utils;

import android.code.editor.ui.activities.CodeEditorActivity;
import java.util.ArrayList;

public class FileTabDataOperator {
  public static boolean isContainsPath(
      ArrayList<CodeEditorActivity.FileTabDataItem> data, String path) {
    for (int position = 0; position < data.size(); position++) {
      if (data.get(position).filePath.equals(path)) {
        return true;
      }
    }
    return false;
  }

  public static void removePath(ArrayList<CodeEditorActivity.FileTabDataItem> data, String path) {
    for (int position = 0; position < data.size(); position++) {
      if (data.get(position).filePath.equals(path)) {
        data.remove(position);
      }
    }
  }

  public static int getPosition(ArrayList<CodeEditorActivity.FileTabDataItem> data, String path) {
    for (int position = 0; position < data.size(); position++) {
      if (data.get(position).filePath.equals(path)) {
        return position;
      }
    }
    return -1;
  }

  public static void addPath(
      ArrayList<CodeEditorActivity.FileTabDataItem> data, CodeEditorActivity.FileTabDataItem obj) {
    if (!isContainsPath(data, obj.filePath)) {
      data.add(obj);
    }
  }
}
