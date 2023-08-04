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
import android.content.res.AssetManager;
import android.view.ViewGroup;
import editor.tsd.tools.Language;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.eclipse.tm4e.core.registry.IGrammarSource;
import org.eclipse.tm4e.core.registry.IThemeSource;

public class SoraEditor implements Editor {
  public CodeEditor editor;
  public Context context;
  public EditorColorScheme colorScheme;

  public SoraEditor(Context context) {
    this.context = context;
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
  public void setLanguageMode(String LanguageMode) {
    String LanguageConfigration;
    String tmLanguage;
    switch (LanguageMode) {
      case Language.Java:
        LanguageConfigration = "Editor/SoraEditor/java/language-configuration.json";
        tmLanguage = "Editor/SoraEditor/java/syntaxes/java.tmLanguage.json";
        initLanguageMode(LanguageConfigration, tmLanguage);
        break;
      case Language.XML:
        LanguageConfigration = "Editor/SoraEditor/xml/language-configuration.json";
        tmLanguage = "Editor/SoraEditor/xml/syntaxes/xml.tmLanguage.json";
        initLanguageMode(LanguageConfigration, tmLanguage);
        break;
      case Language.HTML:
        LanguageConfigration = "Editor/SoraEditor/html/language-configuration.json";
        tmLanguage = "Editor/SoraEditor/html/syntaxes/html.tmLanguage.json";
        initLanguageMode(LanguageConfigration, tmLanguage);
        break;
      case Language.CSS:
        LanguageConfigration = "Editor/SoraEditor/css/language-configuration.json";
        tmLanguage = "Editor/SoraEditor/css/syntaxes/css.tmLanguage.json";
        initLanguageMode(LanguageConfigration, tmLanguage);
        break;
      case Language.JavaScript:
        LanguageConfigration = "Editor/SoraEditor/javascript/language-configuration.json";
        tmLanguage = "Editor/SoraEditor/javascript/syntaxes/JavaScript.tmLanguage.json";
        initLanguageMode(LanguageConfigration, tmLanguage);
        break;
      case Language.Markdown:
        LanguageConfigration = "Editor/SoraEditor/markdown/language-configuration.json";
        tmLanguage = "Editor/SoraEditor/markdown/syntaxes/JavaScript.tmLanguage.json";
        initLanguageMode(LanguageConfigration, tmLanguage);
        break;
    }
  }

  private void initLanguageMode(String LanguageConfigration, String tmLanguage) {
    try {
      AssetManager assetManager = context.getAssets();
      InputStream inputStream = null;
      BufferedReader reader = null;

      try {
        inputStream = assetManager.open(LanguageConfigration);
        reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
          // Do whatever you want with the contents of the file
          System.out.println(line);
        }
      } catch (IOException e) {
        // Handle any exceptions that occur
        e.printStackTrace();
      } finally {
        // Close the reader and the input stream
        if (reader != null) {
          try {
            io.github.rosemoe.sora.lang.Language language =
                TextMateLanguage.create(
                    IGrammarSource.fromInputStream(
                        context.getAssets().open(tmLanguage), tmLanguage, Charset.defaultCharset()),
                    reader,
                    ((TextMateColorScheme) colorScheme).getThemeSource());
            editor.setEditorLanguage(language);
            editor.rerunAnalysis();
            reader.close();
          } catch (IOException e) {
            // Handle any exceptions that occur
            e.printStackTrace();
          }
        }
        if (inputStream != null) {
          try {
            inputStream.close();
          } catch (IOException e) {
            // Handle any exceptions that occur
            e.printStackTrace();
          }
        }
      }
    } catch (Exception e) {

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

  private void initTheme(String theme, String themeName) {
    try {
      colorScheme = editor.getColorScheme();
      if (!(colorScheme instanceof TextMateColorScheme)) {
        IThemeSource themeFromFile =
            IThemeSource.fromInputStream(
                context.getAssets().open(theme), themeName, Charset.defaultCharset());
        colorScheme = TextMateColorScheme.create(themeFromFile);
        editor.setColorScheme(colorScheme);
      }

    } catch (Exception e) {
      e.printStackTrace();
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
