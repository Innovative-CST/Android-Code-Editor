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

import editor.tsd.tools.Language;

public class LanguageModeHandler {
  public static String getLanguageModeForExtension(String ext) {
    if (ext.equals("java")) {
      return Language.Java;
    } else if (ext.equals("xml")) {
      return Language.XML;
    } else if (ext.equals("html")) {
      return Language.HTML;
    } else if (ext.equals("css")) {
      return Language.CSS;
    } else if (ext.equals("md")) {
      return Language.JavaScript;
    } else {
      return Language.Markdown;
    }
  }
}
