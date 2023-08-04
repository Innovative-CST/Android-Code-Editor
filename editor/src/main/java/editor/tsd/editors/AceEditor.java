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

import android.content.Context;
import android.view.ScaleGestureDetector;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import editor.tsd.tools.Language;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;

public class AceEditor implements Editor, ScaleGestureDetector.OnScaleGestureListener {
  public Context context;
  public WebView aceEditor;
  public AceJSInterface aceJSInterface;
  public float fontSize = 8;
  private ScaleGestureDetector scaleGestureDetector;

  public AceEditor(Context c) {
    context = c;
    aceEditor = new WebView(context);
    ViewGroup.LayoutParams aceEditor_LayoutParams =
        new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    aceEditor.setLayoutParams(aceEditor_LayoutParams);

    aceEditor.getSettings().setJavaScriptEnabled(true);

    // disable zoom
    // webview.getSettings().setSupportZoom(false);
    aceEditor.getSettings().setSupportZoom(true);

    // allow content access
    aceEditor.getSettings().setAllowContentAccess(true);

    // allow file access
    aceEditor.getSettings().setAllowFileAccess(true);

    // setup web chrome client
    aceEditor.setWebChromeClient(new WebChromeClient());

    // JavaScript contact with Java
    aceJSInterface = new AceJSInterface();
    aceEditor.addJavascriptInterface(aceJSInterface, "aceEditor");

    // load editor file
    aceEditor.loadUrl("file:///android_asset/Editor/Ace-Editor/AceEditor/index.html");

    scaleGestureDetector = new ScaleGestureDetector(context, this);

    aceEditor.setOnTouchListener(
        (view, event) -> {
          scaleGestureDetector.onTouchEvent(event);
          return false;
        });
  }

  @Override
  public boolean onScale(ScaleGestureDetector detector) {
    float scaleFactor = detector.getScaleFactor();
    float newSize = fontSize * scaleFactor;
    fontSize = newSize;
    setFontSize(newSize);
    return true;
  }

  @Override
  public boolean onScaleBegin(ScaleGestureDetector detector) {
    return true;
  }

  @Override
  public void onScaleEnd(ScaleGestureDetector detector) {}

  @Override
  public void setCode(String Code) {
    aceJSInterface.UpdateCode(Code);
    aceEditor.loadUrl("javascript:setCode()");
  }

  public void setFontSize(float size) {
    aceJSInterface.setZoom(size);
    aceEditor.loadUrl("javascript:updateFontSize()");
  }

  @Override
  public String getCode() {
    aceEditor.loadUrl("javascript:putCodeToJava()");
    try {
      Thread.sleep(5);
    } catch (InterruptedException e) {
      System.out.println(e);
    }
    return aceJSInterface.code;
  }

  public WebView getCodeEditor() {
    return aceEditor;
  }

  @Override
  public int getCodeEditorType() {
    return CodeEditorLayout.AceCodeEditor;
  }

  public class AceJSInterface {
    public String languageMode;
    public String code;
    public String theme;
    public float zoom = 8;

    public void UpdateCode(String Code) {
      code = Code;
    }

    public void setTheme(String theme) {
      this.theme = theme;
    }

    @JavascriptInterface
    public String getAceEditorTheme() {
      return this.theme;
    }

    @JavascriptInterface
    public String getLanguageMode() {
      return languageMode;
    }

    @JavascriptInterface
    public void getCode(String Code) {
      code = Code;
    }

    @JavascriptInterface
    public String setCode() {
      return code;
    }

    @JavascriptInterface
    public String getZoom() {
      return String.valueOf(this.zoom).concat("px");
    }

    public void setZoom(float zoom) {
      this.zoom = zoom;
    }
  }

  @Override
  public void setLanguageMode(String LanguageMode) {
    switch (LanguageMode) {
      case Language.Java:
        aceJSInterface.languageMode = "java";
        break;
      case Language.XML:
        aceJSInterface.languageMode = "xml";
        break;
      case Language.HTML:
        aceJSInterface.languageMode = "html";
        break;
      case Language.CSS:
        aceJSInterface.languageMode = "css";
        break;
      case Language.JavaScript:
        aceJSInterface.languageMode = "javascript";
        break;
      case Language.Markdown:
        aceJSInterface.languageMode = "markdown";
        break;
    }
    aceEditor.loadUrl("javascript:setLanguageMode()");
  }

  @Override
  public void setTheme(String theme) {
    // Set theme
    switch (theme) {
      case Themes.AceEditorTheme.Dark.Dracula:
        aceJSInterface.setTheme("dracula");
        break;
      case Themes.AceEditorTheme.Dark.Monokai:
        aceJSInterface.setTheme("monokai");
        break;
      case Themes.AceEditorTheme.Light.Chrome:
        aceJSInterface.setTheme("chrome");
        break;
      case Themes.AceEditorTheme.Light.Clouds:
        aceJSInterface.setTheme("clouds");
        break;
      case Themes.AceEditorTheme.Light.Crimeson_Editor:
        aceJSInterface.setTheme("crimeson_editor");
        break;
      case Themes.AceEditorTheme.Light.Dawn:
        aceJSInterface.setTheme("dawn");
        break;
    }
    // Update theme
    aceEditor.loadUrl("javascript:setLanguageMode()");
  }

  @Override
  public void moveCursorHorizontally(int steps) {
    for (int position = 0; position < Math.abs(steps); position++) {
      if (steps > 0) {
        aceEditor.loadUrl("javascript:editor.navigateRight(1)");
      } else {
        aceEditor.loadUrl("javascript:editor.navigateLeft(1)");
      }
    }
  }

  @Override
  public void moveCursorVertically(int steps) {
    for (int position = 0; position < Math.abs(steps); position++) {
      if (steps > 0) {
        aceEditor.loadUrl("javascript:editor.navigateDown(1)");
      } else {
        aceEditor.loadUrl("javascript:editor.navigateUp(1)");
      }
    }
  }
}
