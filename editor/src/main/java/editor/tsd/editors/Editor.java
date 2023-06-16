package editor.tsd.editors;

import io.github.rosemoe.sora.widget.CodeEditor;

public interface Editor {
    public void setCode(String Code);

    public String getCode();

    public int getCodeEditorType();

    public void setLanguageMode(String LqnguageMode);

    public void setTheme(String theme);

    public void moveCursorHorizontally(int steps);

    public void moveCursorVertically(int steps);
}
