package android.code.editor.editor;

import android.code.editor.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import editor.tsd.widget.CodeEditorLayout;
import java.util.TimerTask;

public class CodeEditorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_editor);
        CodeEditorLayout codeEditor = findViewById(R.id.editor);
        codeEditor.setCode("Test Code");
        findViewById(R.id.toast).setOnClickListener(
            new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO: Implement this method
                        Toast.makeText(CodeEditorActivity.this,codeEditor.getCode(),Toast.LENGTH_SHORT).show();
                    }
                }
            );
    }
}
