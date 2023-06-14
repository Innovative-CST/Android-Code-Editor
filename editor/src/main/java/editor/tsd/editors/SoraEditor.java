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

import org.eclipse.tm4e.core.registry.IGrammarSource;
import org.eclipse.tm4e.core.registry.IThemeSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

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
        String tmLanguageLastName;
        switch (LanguageMode) {
            case Language.Java:
                LanguageConfigration = "Editor/SoraEditor/java/language-configuration.json";
                tmLanguage = "Editor/SoraEditor/java/syntaxes/java.tmLanguage.json";
                tmLanguageLastName = "java.tmLanguage.json";
                initLanguageMode(LanguageConfigration, tmLanguage, tmLanguageLastName);
                break;
        }
    }

    private void initLanguageMode(
            String LanguageConfigration, String tmLanguage, String tmLanguageLastName) {
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
                                                context.getAssets().open(tmLanguage),
                                                tmLanguageLastName,
                                                Charset.defaultCharset()),
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
        }
    }

    private void initTheme(String theme, String themeName) {
        try {
            colorScheme = editor.getColorScheme();
            if (!(colorScheme instanceof TextMateColorScheme)) {
                IThemeSource themeFromFile =
                        IThemeSource.fromInputStream(
                                context.getAssets().open(theme),
                                themeName,
                                Charset.defaultCharset());
                colorScheme = TextMateColorScheme.create(themeFromFile);
                editor.setColorScheme(colorScheme);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
