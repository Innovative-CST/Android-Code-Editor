package android.code.editor;

import android.Manifest;
import android.code.editor.FileManagerActivity;
import android.code.editor.tsd.StoragePermission;
import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.utils.ThemeObservable;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements StoragePermission,Observer {

    private boolean isRequested;
    private MaterialAlertDialogBuilder MaterialDialog;
    private TextView info;
    private LinearLayout main;
	private ThemeObservable themeObservable = new ThemeObservable();

    @Override
	@SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
		// Enable logging in Sketchware pro
        // SketchLogger.startLogging(); 
		
        super.onCreate(savedInstanceState);
		MaterialColorHelper.setUpTheme(this);
        setContentView(R.layout.activity_main);
        // Navigation
        // modified by ashishtechnozone
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.setNavigationBarColor(Color.parseColor("#000000"));
        }
        // StatusBar
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window w = this.getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor("#000000"));
        }

        MaterialDialog = new MaterialAlertDialogBuilder(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(
                                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {

            MaterialDialog.setTitle("Storage Permission required");
            MaterialDialog.setMessage(
                    "Storage permission is required please allow app to use storage in next page.");
            MaterialDialog.setPositiveButton(
                    "Continue",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface _dialog, int _which) {
                            if (shouldShowRequestPermissionRationale(
                                    "Manifest.permission.READ_EXTERNAL_STORAGE")) {
                                MaterialDialog.setTitle("Storage permission required");
                                MaterialDialog.setMessage(
                                        "Storage permissions is highly recommend for storing and reading files in device.Without this permission you can't use this app.");
                                MaterialDialog.setPositiveButton(
                                        "Setting",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface _dialog, int _which) {
                                                isRequested = true;
                                                Intent intent = new Intent();
                                                intent.setAction(
                                                        android.provider.Settings
                                                                .ACTION_APPLICATION_DETAILS_SETTINGS);
                                                Uri uri =
                                                        Uri.fromParts(
                                                                "package",
                                                                MainActivity.this.getPackageName(),
                                                                null);
                                                intent.setData(uri);
                                                MainActivity.this.startActivity(intent);
                                            }
                                        });
                                MaterialDialog.setNegativeButton(
                                        "No thanks",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface _dialog, int _which) {
                                                _showRationale(
                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                        "Storage permissions is highly recommend for storing and reading files in device.Without this permission you can't use this app");
                                            }
                                        });
                                MaterialDialog.setNeutralButton(
                                        "Exit",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface _dialog, int _which) {
                                                finishAffinity();
                                            }
                                        });
                                MaterialDialog.create().show();
                            } else {
                                _requestStoragePermission();
                            }
                        }
                    });
            MaterialDialog.setNegativeButton(
                    "No thanks",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface _dialog, int _which) {
                            _showRationale(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    "Storage permissions is highly recommend for storing and reading files in device.Without this permission you can't use this app");
                        }
                    });
            MaterialDialog.setNeutralButton(
                    "Exit",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface _dialog, int _which) {
                            finishAffinity();
                        }
                    });
            MaterialDialog.create().show();
        } else {
            startActivtyLogic();
        }
    }
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: Implement this method
		((MyApplication)getApplication()).getThemeObservable().deleteObserver(this);
    }

    public void _requestStoragePermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                2000);
    }

    public void _showRationale(final String _permission, final String _text) {
        if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(_permission)) {
            MaterialDialog.setTitle("Storage Permission required");
            MaterialDialog.setMessage(_text);
            MaterialDialog.setPositiveButton(
                    "Continue",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface _dialog, int _which) {
                            if (ContextCompat.checkSelfPermission(
                                                    MainActivity.this,
                                                    Manifest.permission.READ_EXTERNAL_STORAGE)
                                            == PackageManager.PERMISSION_DENIED
                                    || ContextCompat.checkSelfPermission(
                                                    MainActivity.this,
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                            == PackageManager.PERMISSION_DENIED) {
                                ActivityCompat.requestPermissions(
                                        MainActivity.this,
                                        new String[] {
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        },
                                        1000);
                            } else {
                                startActivtyLogic();
                            }
                        }
                    });
            MaterialDialog.setNegativeButton(
                    "No thanks",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface _dialog, int _which) {
                            finishAffinity();
                        }
                    });
            MaterialDialog.create().show();
        }
    }

    @Override
    public void startActivtyLogic() {
        info = findViewById(R.id.info);
        main = findViewById(R.id.main);
        info.setVisibility(View.VISIBLE);
        main.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View arg0) {
            	// TODO: Implement this method
				Intent intent = new Intent();
				intent.putExtra("path",Environment.getExternalStorageDirectory().getAbsolutePath());
				intent.setClass(MainActivity.this,FileManagerActivity.class);
				startActivity(intent);
        	}
        });
    }
	
	@Override
    public void update(Observable arg0, Object arg1) {
		if ((String)arg1 == "ThemeUpdated") {
			recreate();
		}
	}
}
