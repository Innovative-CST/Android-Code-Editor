package android.code.editor;

import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.utils.ThemeObservable;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Observable;
import java.util.Observer;

public class DebugActivity extends AppCompatActivity implements Observer {
	// Debug activity will not work properly if error is occured in a activty with which the app is opened eg. MainActivty
	private TextView error;
	private ThemeObservable themeObservable = new ThemeObservable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		((MyApplication)getApplication()).getThemeObservable().addObserver(this);
		MaterialColorHelper.setUpTheme(this);
		setContentView(R.layout.activity_debug);
		error = findViewById(R.id.error);
		error.setText(getIntent().getStringExtra("error").toString());
    }
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: Implement this method
		((MyApplication)getApplication()).getThemeObservable().deleteObserver(this);
    }
	
	@Override
    public void update(Observable arg0, Object arg1) {
		if ((String)arg1 == "ThemeUpdated") {
			recreate();
		}
	}
}
