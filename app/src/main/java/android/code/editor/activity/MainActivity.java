/*
 *  This file is part of Android Code Editor.
 *
 *  Android Code Editor is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Android Code Editor is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package android.code.editor.activity;

import android.Manifest;
import android.app.Activity;
import android.code.editor.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends BaseActivity {

  private boolean isRequested;
  private MaterialAlertDialogBuilder MaterialDialog;
  private TextView info;
  private LinearLayout main;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // Enable logging in Sketchware pro
    // SketchLogger.startLogging();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (isStoagePermissionGranted(this)) {
      startActivtyLogic();
    } else {
      showStoragePermissionDialog(this);
    }
  }

  public void startActivtyLogic() {
    info = findViewById(R.id.info);
    main = findViewById(R.id.main);
    info.setVisibility(View.VISIBLE);
    main.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.putExtra("path", Environment.getExternalStorageDirectory().getAbsolutePath());
            intent.setClass(MainActivity.this, FileManagerActivity.class);
            startActivity(intent);
          }
        });
  }

  public static boolean isStoagePermissionGranted(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      return Environment.isExternalStorageManager();
    } else {
      if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
              == PackageManager.PERMISSION_DENIED
          || ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
              == PackageManager.PERMISSION_DENIED) {
        return false;
      } else {
        return true;
      }
    }
  }

  public static void _requestStoragePermission(Activity activity, int reqCode) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      try {
        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, reqCode);
      } catch (Exception e) {

      }
    } else {
      ActivityCompat.requestPermissions(
          activity,
          new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
          },
          reqCode);
    }
  }

  @Override
  public void onRequestPermissionsResult(int arg0, String[] arg1, int[] arg2) {
    super.onRequestPermissionsResult(arg0, arg1, arg2);
    switch (arg0) {
      case 1:
      case -1:
      case 10:
        int Denied = 0;
        for (int position = 0; position < arg2.length; position++) {
          if (arg2[position] == PackageManager.PERMISSION_DENIED) {
            Denied++;
            if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(arg1[position])) {
              if (Build.VERSION.SDK_INT >= 23) {
                if (shouldShowRequestPermissionRationale(arg1[position])) {
                  showRationaleOfStoragePermissionDialog(this);
                } else {
                  showStoragePermissionDialogForGoToSettings(this);
                }
              }
            }
          }
        }
        if (Denied == 0) {
          startActivtyLogic();
        }
        break;
    }
  }

  @Override
  @CallSuper
  protected void onActivityResult(int arg0, int arg1, Intent arg2) {
    super.onActivityResult(arg0, arg1, arg2);
    if (isStoagePermissionGranted(this)) {
      startActivtyLogic();
    } else {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        showRationaleOfStoragePermissionDialog(this);
      }
    }
  }

  /* Show Material Dialog for Storage Permission */

  public static void showStoragePermissionDialog(Activity activity) {
    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
    dialog.setTitle("Storage permission required");
    dialog.setMessage(
        "Storage permission is required please allow app to use storage in next page.");
    dialog.setPositiveButton(
        "Continue",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface _dialog, int _which) {
            _requestStoragePermission(activity, 1);
          }
        });
    dialog.setNegativeButton(
        "No thanks",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface _dialog, int _which) {
            activity.finishAffinity();
          }
        });
    dialog.create().show();
  }

  public static void showRationaleOfStoragePermissionDialog(Activity activity) {
    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
    dialog.setTitle("Storage permission required");
    dialog.setMessage(
        "Storage permissions is highly recommend for storing and reading files in device.Without this permission you can't use this app.");
    dialog.setPositiveButton(
        "Continue",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface _dialog, int _which) {
            _requestStoragePermission(activity, 1);
          }
        });
    dialog.setNegativeButton(
        "No thanks",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface _dialog, int _which) {
            activity.finishAffinity();
          }
        });
    dialog.create().show();
  }

  public static void showStoragePermissionDialogForGoToSettings(Activity context) {
    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
    dialog.setTitle("Storage permission required");
    dialog.setMessage(
        "Storage permissions is highly recommend for storing and reading files in device.Without this permission you can't use this app.");
    dialog.setPositiveButton(
        "Setting",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface _dialog, int _which) {
            Intent intent = new Intent();
            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
          }
        });
    dialog.setNegativeButton(
        "No thanks",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface _dialog, int _which) {
            context.finishAffinity();
          }
        });
    dialog.create().show();
  }
}
