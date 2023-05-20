package editor.tsd.editors;

import android.content.Context;
import android.view.ViewGroup;

import editor.tsd.widget.CodeEditorLayout;

import io.github.rosemoe.sora.widget.CodeEditor;


public class SoraEditor implements Editor {
    public CodeEditor editor;

    public SoraEditor(Context context) {

        editor = new CodeEditor(context);
        ViewGroup.LayoutParams codeEditor_LayoutParams =
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        
        editor.setLayoutParams(codeEditor_LayoutParams);
    }

    @Override
    public void setCode(String Code) {
        editor.setText(Code);
    }

    @Override
    public String getCode() {
        return editor.getText().toString();
    }

    public CodeEditor getCodeEditor() {
        return editor;
    }
    
    @Override
    public int getCodeEditorType() {
        return CodeEditorLayout.SoraCodeEditor;
    }

    @Override
    public void setLanguageMode(String LqnguageMode) {
    }
    
}
