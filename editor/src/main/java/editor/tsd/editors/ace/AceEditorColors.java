package editor.tsd.editors.ace;

import android.code.editor.common.utils.FileUtils;
import android.content.Context;
import editor.tsd.editors.AceEditor;

public class AceEditorColors {
  String activeLineColor;
  String editorBackground;
  String gutterActiveLineColor;
  String gutterBackground;
  String gutterTextColor;

  public void apply(Context context) {
    apply(context, "Editor/Ace-Editor/AceEditor/css/themes/monokai.css", "/css/themes/monokai.css");
    apply(context, "Editor/Ace-Editor/AceEditor/css/themes/dracula.css", "/css/themes/dracula.css");
    apply(context, "Editor/Ace-Editor/AceEditor/css/themes/chrome.css", "/css/themes/chrome.css");
    AceEditor.reinstallIndexFile(context);
  }

  public void apply(Context context, String cssPath, String finalPath) {
    String cssText = FileUtils.readFileFromAssets(context.getAssets(), cssPath);
    if (editorBackground != null) {
      cssText = cssText.replaceAll("ace_background", editorBackground);
    } else {
      cssText = cssText.replaceAll("ace_background", "#272822");
    }
    if (gutterBackground != null) {
      cssText = cssText.replaceAll("ace_gutter_background", gutterBackground);
    } else {
      cssText = cssText.replaceAll("ace_gutter_background", "#2F3129");
    }
    if (gutterTextColor != null) {
      cssText = cssText.replaceAll("ace_gutter_text_color", gutterTextColor);
    } else {
      cssText = cssText.replaceAll("ace_gutter_text_color", "#8F908A");
    }
    if (activeLineColor != null) {
      cssText = cssText.replaceAll("ace_active_line", activeLineColor);
    } else {
      cssText = cssText.replaceAll("ace_active_line", "#202020");
    }
    if (gutterActiveLineColor != null) {
      cssText = cssText.replaceAll("ace_gutter_active_line", gutterActiveLineColor);
    } else {
      cssText = cssText.replaceAll("ace_gutter_active_line", "#272727");
    }
    FileUtils.writeFile(
        FileUtils.getDataDir(context).concat(AceEditor.AceEditorPath).concat(finalPath), cssText);
  }

  public void setActiveLineColor(String activeLineColor) {
    this.activeLineColor = activeLineColor;
  }

  public void setGutterActiveLineColor(String gutterActiveLineColor) {
    this.gutterActiveLineColor = gutterActiveLineColor;
  }

  public void setGutterTextColor(String gutterTextColor) {
    this.gutterTextColor = gutterTextColor;
  }

  public void setEditorBackground(String editorBackground) {
    this.editorBackground = editorBackground;
  }

  public void setGutterBackground(String gutterBackground) {
    this.gutterBackground = gutterBackground;
  }
}
