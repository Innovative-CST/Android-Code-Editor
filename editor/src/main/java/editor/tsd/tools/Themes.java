package editor.tsd.tools;

import java.util.ArrayList;
import java.util.HashMap;

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

        @Override
        public ArrayList<String> getThemes() {
            ArrayList<String> arr = new ArrayList<String>();
            arr.addAll(new Dark().getThemes());
            ArrayList<String> darkThemes = new Dark().getThemes();
            return arr;
        }
    }

    public class SoraEditorTheme implements ThemeSet {
        public class Dark implements ThemeSet {
            @Deprecated public static final String Darcula = "Darcula";
            public static final String Monokai = "Monokai";
            public static final String Default = Monokai;

            @Override
            public ArrayList<String> getThemes() {
                ArrayList<String> arr = new ArrayList<>();
                arr.add(Darcula);
                arr.add(Monokai);
                return arr;
            }
        }

        @Override
        public ArrayList<String> getThemes() {
            ArrayList<String> arr = new ArrayList<String>();
            arr.addAll(new Dark().getThemes());
            return arr;
        }
    }
}
