package android.code.editor.utils;

import editor.tsd.tools.Language;

public class LanguageModeHandler {
  public static String getLanguageModeForExtension(String ext) {
    if (ext.equals("java")) {
      return Language.Java;
    } else if (ext.equals("xml")) {
      return Language.XML;
    } else if (ext.equals("html")) {
      return Language.HTML;
    } else if (ext.equals("css")) {
      return Language.CSS;
    } else if (ext.equals("md")) {
      return Language.JavaScript;
    } else {
      return Language.Markdown;
    }
  }
}
