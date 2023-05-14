package editor.tsd.editors;

import io.github.rosemoe.sora.widget.CodeEditor;

public interface Editor {
    public void setCode(String Code);
    public String getCode();
    public int getCodeEditorType();
}
