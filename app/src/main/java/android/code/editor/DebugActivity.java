package android.code.editor;

import android.code.editor.ui.MaterialColorHelper;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DebugActivity extends AppCompatActivity {
    // Debug activity will not work properly if error is occured in a activty with which the app is
    // opened eg. MainActivty
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaterialColorHelper.setUpTheme(this);
        setContentView(R.layout.activity_debug);
        error = findViewById(R.id.error);
        error.setText(getIntent().getStringExtra("error").toString());
        error.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View arg0) {
                        // TODO: Implement this method
                        ((ClipboardManager)
                                        getSystemService(getApplicationContext().CLIPBOARD_SERVICE))
                                .setPrimaryClip(
                                        ClipData.newPlainText(
                                                "clipboard", error.getText().toString()));
                        return true;
                    }
                });
    }
}
