package editor.tsd.editors;

import android.content.Context;
import android.view.ViewGroup;

import android.widget.Toast;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;

import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.Collection;
import org.eclipse.tm4e.core.grammar.IToken;
import org.eclipse.tm4e.core.grammar.ITokenizeLineResult;
import org.eclipse.tm4e.core.grammar.IStateStack;
import java.time.Duration;
import org.eclipse.tm4e.core.grammar.IGrammar;
import org.eclipse.tm4e.core.registry.IGrammarSource;
import org.eclipse.tm4e.core.registry.IThemeSource;
import org.eclipse.tm4e.languageconfiguration.model.LanguageConfiguration;

public class SoraEditor implements Editor {
    public CodeEditor editor;
    public Context context;

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
    public void setLanguageMode(String LanguageMode) {}

    @Override
    public void setTheme(String theme) {
        switch (theme) {
            case Themes.SoraEditorTheme.Darcula:
                try {
                    EditorColorScheme colorScheme = editor.getColorScheme();
                    if (!(colorScheme instanceof TextMateColorScheme)) {
                        IThemeSource themeFromFile =
                                IThemeSource.fromInputStream(context.getAssets().open("Editor/SoraEditor/darcula.json"),"darcula.json",Charset.defaultCharset());
                        colorScheme = TextMateColorScheme.create(themeFromFile);
                        editor.setColorScheme(colorScheme);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
