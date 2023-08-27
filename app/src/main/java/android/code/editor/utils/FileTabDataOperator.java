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
