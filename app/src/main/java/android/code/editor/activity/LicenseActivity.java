package android.code.editor.activity;

import android.code.editor.R;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LicenseActivity extends BaseActivity {

  private TextView licenseText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_license);
    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle("Open Source Licenses");
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View arg0) {
            // TODO: Implement this method
            onBackPressed();
          }
        });
    licenseText = findViewById(R.id.licenseText);
    licenseText.setAutoLinkMask(Linkify.WEB_URLS);
    licenseText.setMovementMethod(LinkMovementMethod.getInstance());
    licenseText.setText(readFileFromAssets(getAssets(), "oos.text"));
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
