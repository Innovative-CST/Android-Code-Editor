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
 *   along with Android Code Editor.  If not, see <https://www.gnu.org/licenses/>.
 */

package editor.tsd.tools;

import java.util.ArrayList;

public class Themes {
  public interface ThemeSet {
    public ArrayList<String> getThemes();
  }

  public class AceEditorTheme implements ThemeSet {
    public class Dark implements ThemeSet {
      @Deprecated public static final String Dracula = "Dracula";
      public static final String Monokai = "Monokai";
      public static final String Default = Monokai;

      public ArrayList<String> getThemes() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(Dracula);
        arr.add(Monokai);
        return arr;
      }
    }

    public class Light implements ThemeSet {
      public static final String Chrome = "Chrome";
      public static final String Clouds = "Clouds";
      public static final String CrimsonEditor = "Crimson Editor";
      public static final String Dawn = "Dawn";
      public static final String Default = Chrome;

      public ArrayList<String> getThemes() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(Chrome);
        arr.add(Clouds);
        arr.add(CrimsonEditor);
        arr.add(Dawn);
        return arr;
      }
    }

    @Override
    public ArrayList<String> getThemes() {
      ArrayList<String> arr = new ArrayList<String>();
      arr.addAll(new Light().getThemes());
      arr.addAll(new Dark().getThemes());
      ArrayList<String> darkThemes = new Dark().getThemes();
      return arr;
    }
  }

  public class SoraEditorTheme implements ThemeSet {
    public class Dark implements ThemeSet {
      @Deprecated public static final String Darcula = "Darcula";
      public static final String Monokai = "Monokai";
      public static final String Solarized_Drak = "Solarized_Drak";
      public static final String Default = Monokai;

      @Override
      public ArrayList<String> getThemes() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(Darcula);
        arr.add(Monokai);
        arr.add(Solarized_Drak);
        return arr;
      }
    }

    public class Light implements ThemeSet {
      public static final String Quietlight = "Quietlight";
      public static final String Solarized_Light = "Solarized_Light";
      public static final String Default = Quietlight;

      @Override
      public ArrayList<String> getThemes() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(Quietlight);
        arr.add(Solarized_Light);
        return arr;
      }
    }

    @Override
    public ArrayList<String> getThemes() {
      ArrayList<String> arr = new ArrayList<String>();
      arr.addAll(new Light().getThemes());
      arr.addAll(new Dark().getThemes());
      return arr;
    }
  }
}
