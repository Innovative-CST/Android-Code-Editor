package android.code.editor.activity;

import android.code.editor.R;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DebugActivity extends BaseActivity {
  // Debug activity will not work properly if error is occured in a activty with which the app is
  // opened eg. MainActivty
  private TextView error;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_debug);
    error = findViewById(R.id.error);
    error.setText(getIntent().getStringExtra("error").toString());
    error.setOnLongClickListener(
        new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View arg0) {
            ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE))
                .setPrimaryClip(ClipData.newPlainText("clipboard", error.getText().toString()));
            return true;
          }
        });
  }
}
