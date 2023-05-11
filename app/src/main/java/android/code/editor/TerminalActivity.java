package android.code.editor;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.termux.view.TerminalView;

public class TerminalActivity extends AppCompatActivity {
	public TerminalView terminal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.terminal_view);
		terminal = findViewById(R.id.terminal_view);
		
    }
}
