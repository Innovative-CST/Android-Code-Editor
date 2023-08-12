package editor.tsd.editors.sora.lang.textmate.provider;

import android.content.Context;
import android.content.res.AssetManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
                readFileFromAssets(context.getAssets(), "Editor/SoraEditor/language_scopes.json"),
                type);
    return scopes.get(fileExt);
  }

  public static String readFileFromAssets(AssetManager assetManager, String fileName) {
    StringBuilder stringBuilder = new StringBuilder();
    try {
      InputStream inputStream = assetManager.open(fileName);
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        stringBuilder.append(line);
        stringBuilder.append("\n");
      }

      bufferedReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return stringBuilder.toString();
  }
}
