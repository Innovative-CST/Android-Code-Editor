package editor.tsd.editors.sora.lang.textmate;

import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import org.eclipse.tm4e.core.grammar.IGrammar;
import org.eclipse.tm4e.languageconfiguration.model.LanguageConfiguration;

public class AndroidCodeEditorTMLanguage extends TextMateLanguage {
  public final String languageScope;

  public AndroidCodeEditorTMLanguage(
      IGrammar iGrammar,
      LanguageConfiguration languageConfiguration,
      ThemeRegistry themeRegistry,
      String scope) {
    super(iGrammar, languageConfiguration, null, themeRegistry, false);
    this.languageScope = scope;
  }

  public static AndroidCodeEditorTMLanguage create(String scope) {
    final GrammarRegistry grammarRegistry = GrammarRegistry.getInstance();
    IGrammar grammar = grammarRegistry.findGrammar(scope);

    if (grammar == null) {
      throw new IllegalArgumentException(
          "Language with scope name not found.Scope : ".concat(scope));
    }

    var languageConfiguration = grammarRegistry.findLanguageConfiguration(grammar.getScopeName());

    return new AndroidCodeEditorTMLanguage(
        grammar, languageConfiguration, ThemeRegistry.getInstance(), scope);
  }
}
