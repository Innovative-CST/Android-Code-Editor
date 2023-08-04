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

package editor.tsd.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.LinearLayout;
import editor.tsd.R;
import editor.tsd.editors.AceEditor;
import editor.tsd.editors.Editor;
import editor.tsd.editors.SoraEditor;
import io.github.rosemoe.sora.widget.CodeEditor;

public class CodeEditorLayout extends LinearLayout {
  public static final int AceCodeEditor = 0;
  public static final int SoraCodeEditor = 1;

  public AceEditor aceEditor;
  public SoraEditor soraEditor;
  public int editor;
  public String code;
  public Context conText;
  public Editor CurrentCodeEditor;

  public CodeEditorLayout(Context context) {
    super(context);
    this.conText = context;
    try {
      this.editor = AceCodeEditor;
      this.code = "";
    } finally {
      init();
    }
  }

  public CodeEditorLayout(Context context, int editor) {
    super(context);
    this.conText = context;
    try {
      this.editor = editor;
      this.code = "";
    } finally {
      init();
    }
  }

  public CodeEditorLayout(Context context, String code) {
    super(context);
    this.conText = context;
    try {
      this.editor = AceCodeEditor;
      this.code = code;
    } finally {
      init();
    }
  }

  public CodeEditorLayout(Context context, int editor, String code) {
    super(context);
    this.conText = context;
    try {
      this.editor = editor;
      this.code = code;
    } finally {
      init();
    }
  }

  public CodeEditorLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    conText = context;
    TypedArray a =
        context.getTheme().obtainStyledAttributes(attrs, R.styleable.CodeEditorLayout, 0, 0);
    try {
      editor = a.getInt(R.styleable.CodeEditorLayout_codeEditor, 0);
      code = a.getString(R.styleable.CodeEditorLayout_setCode);
    } finally {
      // Set Code Editor as SoraEditor
      init();
      a.recycle();
    }
  }

  private void init() {
    // Set Code Editor as SoraEditor
    if (editor == SoraCodeEditor) {
      soraEditor = new SoraEditor(conText);
      CurrentCodeEditor = soraEditor;
      soraEditor.setCode(code);
      addView(soraEditor.getCodeEditor());
    } else if (editor == AceCodeEditor) {
      aceEditor = new AceEditor(conText);
      CurrentCodeEditor = aceEditor;
      addView(aceEditor.getCodeEditor());
      aceEditor.setCode(code);
    }
  }

  public void setCode(String newCode) {
    code = newCode;
    CurrentCodeEditor.setCode(newCode);
  }

  public String getCode() {
    if (CurrentCodeEditor != null) {
      return CurrentCodeEditor.getCode();
    } else {
      return "";
    }
  }

  public void setEditor(int newEditor) {
    if (newEditor != editor) {
      if (CodeEditorLayout.AceCodeEditor == newEditor) {
        aceEditor = new AceEditor(conText);
        CurrentCodeEditor = aceEditor;
        aceEditor.setCode(getCode());
        removeView(soraEditor.getCodeEditor());
        addView(aceEditor.getCodeEditor());
        editor = AceCodeEditor;
        soraEditor = null;
        requestLayout();
      } else if (CodeEditorLayout.SoraCodeEditor == newEditor) {
        soraEditor = new SoraEditor(conText);
        CurrentCodeEditor = soraEditor;
        soraEditor.setCode(getCode());
        removeView(aceEditor.getCodeEditor());
        addView(soraEditor.getCodeEditor());
        editor = SoraCodeEditor;
        aceEditor = null;
        requestLayout();
      }
    }
  }

  public void setLanguageMode(String LanguageMode) {
    CurrentCodeEditor.setLanguageMode(LanguageMode);
  }

  public void setTheme(String theme) {
    CurrentCodeEditor.setTheme(theme);
  }

  public Editor getEditor() {
    return CurrentCodeEditor;
  }

  public int getCurrentEditorType() {
    return editor;
  }

  public CodeEditor getSoraCodeEditor() {
    return soraEditor.getCodeEditor();
  }

  public SoraEditor getSoraEditor() {
    return soraEditor;
  }

  public WebView getAceCodeEditor() {
    return aceEditor.getCodeEditor();
  }

  public AceEditor getAceEditor() {
    return aceEditor;
  }

  public void moveCursorHorizontally(int steps) {
    CurrentCodeEditor.moveCursorHorizontally(steps);
  }

  public void moveCursorVertically(int steps) {
    CurrentCodeEditor.moveCursorVertically(steps);
  }
}
