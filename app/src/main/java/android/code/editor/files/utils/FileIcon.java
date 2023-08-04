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

package android.code.editor.files.utils;

import android.code.editor.R;
import android.content.Context;
import android.widget.ImageView;
import java.io.File;

public class FileIcon {
  public static void setUpIcon(Context context, String path, ImageView imageview) {
    if (new File(path).isDirectory()) {
      imageview.setImageResource(R.drawable.ic_folder_black_24dp);
    } else if (new File(path).isFile()) {
      switch (FileManager.getPathFormat(path)) {
        case "java":
          imageview.setImageResource(R.drawable.ic_language_java);
          break;
        case "xml":
          imageview.setImageResource(R.drawable.file_xml_box);
          break;
        case "html":
          imageview.setImageResource(R.drawable.language_html);
          break;
        case "css":
          imageview.setImageResource(R.drawable.language_css);
          break;
        case "js":
          imageview.setImageResource(R.drawable.language_javascript);
          break;
        case "json":
          imageview.setImageResource(R.drawable.language_json);
          break;
        case "md":
          imageview.setImageResource(R.drawable.language_markdown);
          break;
        default:
          imageview.setImageResource(R.drawable.icon_file);
          break;
      }
    }
  }
}
