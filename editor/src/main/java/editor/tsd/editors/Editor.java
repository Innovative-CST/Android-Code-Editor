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

package editor.tsd.editors;

public interface Editor {
  public void setCode(String Code);

  public String getCode();

  public int getCodeEditorType();

  public void setLanguageMode(String LqnguageMode);

  public void setTheme(String theme);

  public void moveCursorHorizontally(int steps);

  public void moveCursorVertically(int steps);
}
