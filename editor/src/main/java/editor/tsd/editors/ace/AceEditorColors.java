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
    apply(context, "Editor/Ace-Editor/AceEditor/css/themes/clouds.css", "/css/themes/clouds.css");
    apply(
        context,
        "Editor/Ace-Editor/AceEditor/css/themes/crimson-editor.css",
        "/css/themes/crimson-editor.css");
    apply(context, "Editor/Ace-Editor/AceEditor/css/themes/dawn.css", "/css/themes/dawn.css");
    AceEditor.reinstallIndexFile(context);
  }

  public void apply(Context context, String cssPath, String finalPath) {
    String cssText = FileUtils.readFileFromAssets(context.getAssets(), cssPath);
    if (editorBackground != null) {
      cssText = cssText.replaceAll("ace_background", editorBackground);
    }
    if (gutterBackground != null) {
      cssText = cssText.replaceAll("ace_gutter_background", gutterBackground);
    }
    if (gutterTextColor != null) {
      cssText = cssText.replaceAll("ace_gutter_text_color", gutterTextColor);
    }
    if (activeLineColor != null) {
      cssText = cssText.replaceAll("ace_active_line", activeLineColor);
    }
    if (gutterActiveLineColor != null) {
      cssText = cssText.replaceAll("ace_gutter_active_line", gutterActiveLineColor);
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
