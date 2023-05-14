package editor.tsd.widget;

import editor.tsd.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import editor.tsd.editors.AceEditor;
import editor.tsd.editors.Editor;
import editor.tsd.editors.SoraEditor;
import io.github.rosemoe.sora.widget.CodeEditor;

public class CodeEditorLayout extends LinearLayout {
    public static int AceCodeEditor = 0;
    public static int SoraCodeEditor = 1;
    
    public AceEditor aceEditor;
    public SoraEditor soraEditor;
    public int editor;
    public String code;
    public Context conText;

    public CodeEditorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        conText = context;
        TypedArray a =
                context.getTheme()
                        .obtainStyledAttributes(attrs, R.styleable.CodeEditorLayout, 0, 0);
        try {
            editor = a.getInt(R.styleable.CodeEditorLayout_codeEditor, 0);
            code = a.getString(R.styleable.CodeEditorLayout_setCode);
        } finally {
            // Set Code Editor as SoraEditor
            if (editor == SoraCodeEditor) {
                soraEditor = new SoraEditor(context);
                soraEditor.setCode(code);
                addView(soraEditor.getCodeEditor());
            } else if (editor == AceCodeEditor) {
                aceEditor = new AceEditor(context);
                addView(aceEditor.getCodeEditor());
                aceEditor.setCode(code);
            }
            a.recycle();
        }
    }

    public void setCode(String newCode) {
        code = newCode;
        if (editor == AceCodeEditor) {
            aceEditor.setCode(newCode);
        }
        if (editor == SoraCodeEditor) {
            soraEditor.setCode(newCode);
        }
    }
    
    public String getCode() {
        if (editor == AceCodeEditor) {
            return aceEditor.getCode();
        } else if (editor == SoraCodeEditor) {
            return soraEditor.getCode();
        }
        else {
            return "";
        }
    }

    public void setEditor(int newEditor) {
        if (newEditor != editor) {
            if (CodeEditorLayout.AceCodeEditor == newEditor) {
                aceEditor = new AceEditor(conText);
                aceEditor.setCode(getCode());
                aceEditor.setLanguageMode("java");
                removeView(soraEditor.getCodeEditor());
                addView(aceEditor.getCodeEditor());
                editor = AceCodeEditor;
                soraEditor = null;
                requestLayout();
            } else if (CodeEditorLayout.SoraCodeEditor == newEditor) {
                soraEditor = new SoraEditor(conText);
                soraEditor.setCode(getCode());
                removeView(aceEditor.getCodeEditor());
                addView(soraEditor.getCodeEditor());
                editor = SoraCodeEditor;
                aceEditor = null;
                requestLayout();
            }
        }
    }

    public Editor getEditor() {
        if (editor == AceCodeEditor) {
            return aceEditor;
        } else if (editor == SoraCodeEditor) {
            return soraEditor;
        } else {
            return null;
        }
    }
    
    public int getCurrentEditorType() {
        return editor;
    }
    
    public CodeEditor getSoraEditor() {
        return soraEditor.getCodeEditor();
    }
    
    public WebView getAceEditor() {
        return aceEditor.getCodeEditor();
    }

    @Override
    public void onViewRemoved(View arg0) {
        super.onViewRemoved(arg0);
    }

    @Override
    public void onViewAdded(View arg0) {
        super.onViewAdded(arg0);
    }
}
