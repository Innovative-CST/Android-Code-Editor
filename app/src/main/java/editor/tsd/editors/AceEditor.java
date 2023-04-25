package editor.tsd.editors;

import android.code.editor.R;
import android.content.Context;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
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
        setHighlighterLanguage("java");
    }
    
    public void setHighlighterLanguage(String language) {
        aceJSInterface.highlighter = language;
        aceEditor.loadUrl("javascript:setHighlighter()");
    }

    @Override
    public void setCode(String Code) {
        aceJSInterface.UpdateCode(Code);
        aceEditor.loadUrl("javascript:setCode()");
    }

    @Override
    public String getCode() {
        aceEditor.loadUrl("javascript:putCodeToJava()");
        return aceJSInterface.code;
    }
    
    public WebView getCodeEditor() {
        return aceEditor;
    }

    @Override
    public int getCodeEditorType() {
        return CodeEditorLayout.AceCodeEditor;
    }
    
    public class AceJSInterface{
        public String highlighter;
        public String code;
        public void UpdateCode(String Code) {
            code = Code;
        }
        
        public void setHighlighter(String Highlighter) {
            highlighter= Highlighter;
        }
		@JavascriptInterface
		public String getAceEditorTheme(){
			return "monokai";
		}
        @JavascriptInterface
		public String getHighlighter(){
			return highlighter;
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
}
