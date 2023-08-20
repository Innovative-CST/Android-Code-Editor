package editor.tsd.editors.sora.lang.textmate.provider;

import android.code.editor.common.utils.FileUtils;
import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry;
import java.util.Map;

public class TextMateProvider {
  public static void loadGrammars() {
    GrammarRegistry.getInstance().loadGrammars("Editor/SoraEditor/languages.json");
  }

  public static String getLanguageScope(Context context, String fileExt) {
    Map<String, String> scopes;
    var type = new TypeToken<Map<String, String>>() {}.getType();
    scopes =
        new Gson()
            .fromJson(
                FileUtils.readFileFromAssets(context.getAssets(), "Editor/SoraEditor/language_scopes.json"),
                type);
    return scopes.get(fileExt);
  }
}
