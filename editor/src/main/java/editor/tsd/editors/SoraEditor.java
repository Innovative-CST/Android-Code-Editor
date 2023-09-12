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
import android.graphics.Typeface;
import android.view.ViewGroup;
import editor.tsd.editors.sora.lang.textmate.AndroidCodeEditorTMLanguage;
import editor.tsd.editors.sora.lang.textmate.provider.TextMateProvider;
import editor.tsd.tools.EditorListeners;
import editor.tsd.tools.Language;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import org.eclipse.tm4e.core.registry.IThemeSource;

public class SoraEditor implements Editor {
  public CodeEditor editor;
  public Context context;
  public EditorColorScheme colorScheme;
  public EditorListeners listener;

  public SoraEditor(Context context) {
    this.context = context;
    editor = new CodeEditor(context);
    ViewGroup.LayoutParams codeEditor_LayoutParams =
        new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    Typeface font =
        Typeface.createFromAsset(context.getAssets(), "Editor/SoraEditor/jetbrains.ttf");
    editor.setTypefaceText(font);
    editor.setLayoutParams(codeEditor_LayoutParams);
  }

  @Override
  public void setCode(String Code) {
    editor.setText(Code);
  }

  @Override
  public void getCode(EditorListeners listener) {
    this.listener = listener;
    if (listener != null) {
      listener.onReceviedCode(editor.getText().toString());
    }
  }

  public CodeEditor getCodeEditor() {
    return editor;
  }

  @Override
  public int getCodeEditorType() {
    return CodeEditorLayout.SoraCodeEditor;
  }

  @Override
  public void setLanguageMode(String LanguageMode) {
    if (!LanguageMode.equals(Language.UNKNOWN)) {
      editor.setEditorLanguage(
          AndroidCodeEditorTMLanguage.create(
              TextMateProvider.getLanguageScope(context, LanguageMode)));
    }
  }

  @Override
  public void setTheme(String theme) {
    String ThemeFile;
    String ThemeFileName;
    switch (theme) {
      case Themes.SoraEditorTheme.Dark.Darcula:
        ThemeFile = "Editor/SoraEditor/darcula.json";
        ThemeFileName = "darcula.json";
        initTheme(ThemeFile, ThemeFileName);
        break;
      case Themes.SoraEditorTheme.Dark.Monokai:
        ThemeFile = "Editor/SoraEditor/monokai-color-theme.json";
        ThemeFileName = "monokai-color-theme.json";
        initTheme(ThemeFile, ThemeFileName);
        break;
      case Themes.SoraEditorTheme.Dark.Solarized_Drak:
        ThemeFile = "Editor/SoraEditor/solarized_drak.json";
        ThemeFileName = "solarized_drak.json";
        initTheme(ThemeFile, ThemeFileName);
        break;
      case Themes.SoraEditorTheme.Light.Quietlight:
        ThemeFile = "Editor/SoraEditor/quietlight.json";
        ThemeFileName = "quietlight.json";
        initTheme(ThemeFile, ThemeFileName);
        break;
      case Themes.SoraEditorTheme.Light.Solarized_Light:
        ThemeFile = "Editor/SoraEditor/solarized-light-color-theme.json";
        ThemeFileName = "solarized-light-color-theme.json";
        initTheme(ThemeFile, ThemeFileName);
        break;
    }
  }

  public void initTheme(String p1, String p2) {
    try {
      editor.setColorScheme(
          new TextMateColorScheme(
              ThemeRegistry.getInstance(),
              new ThemeModel(
                  IThemeSource.fromInputStream(
                      FileProviderRegistry.getInstance().tryGetInputStream(p1), p1, null),
                  p1)));
    } catch (Exception e) {
    }
  }

  @Override
  public void moveCursorHorizontally(int steps) {
    for (int position = 0; position < Math.abs(steps); position++) {
      if (steps > 0) {
        editor.moveSelectionRight();
      } else {
        editor.moveSelectionLeft();
      }
    }
  }

  @Override
  public void moveCursorVertically(int steps) {
    for (int position = 0; position < Math.abs(steps); position++) {
      if (steps > 0) {
        editor.moveSelectionDown();
      } else {
        editor.moveSelectionUp();
      }
    }
  }
}
