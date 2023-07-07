package editor.tsd.editors;

import android.content.Context;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import editor.tsd.tools.Language;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;

public class AceEditor implements Editor {
    public Context context;
    public WebView aceEditor;
    public AceJSInterface aceJSInterface;

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
    }

    @Override
    public void setCode(String Code) {
        aceJSInterface.UpdateCode(Code);
        aceEditor.loadUrl("javascript:setCode()");
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
