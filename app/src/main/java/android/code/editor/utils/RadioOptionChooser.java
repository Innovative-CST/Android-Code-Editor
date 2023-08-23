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

package android.code.editor.utils;

import android.code.editor.R;
import android.code.editor.ui.activities.SettingActivity;
import android.code.editor.ui.activities.WebViewActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.radiobutton.MaterialRadioButton;
import editor.tsd.widget.CodeEditorLayout;

public class RadioOptionChooser {
  public AlertDialog builder;
  public MaterialAlertDialogBuilder dialog;
  public Context context;
  public int layout;

  public RadioOptionChooser(Context context, final int layout, String title) {
    this.context = context;
    dialog = new MaterialAlertDialogBuilder(context);
    this.layout = layout;

    LayoutInflater inflater =
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    View dialogView = inflater.inflate(layout, null);
    if (layout == R.layout.layout_editor_chooser_radio_group) {
      dialogView
          .findViewById(R.id.radioOption1)
          .setOnClickListener(
              (view) -> {
                radioButtonOnClick(view);
              });
      dialogView
          .findViewById(R.id.radioOption2)
          .setOnClickListener(
              (view) -> {
                radioButtonOnClick(view);
              });

      if (layout == R.layout.layout_editor_chooser_radio_group) {
        if (Setting.SaveInFile.getSettingInt(
                Setting.Key.CodeEditor, Setting.Default.CodeEditor, context)
            == CodeEditorLayout.AceCodeEditor) {
          ((MaterialRadioButton) dialogView.findViewById(R.id.radioOption1)).setChecked(true);
        } else if (Setting.SaveInFile.getSettingInt(
                Setting.Key.CodeEditor, Setting.Default.CodeEditor, context)
            == CodeEditorLayout.SoraCodeEditor) {
          ((MaterialRadioButton) dialogView.findViewById(R.id.radioOption2)).setChecked(true);
        }
      }
    } else if (layout == R.layout.layout_console_chooser_radio_group) {
      dialogView
          .findViewById(R.id.radioOption1)
          .setOnClickListener(
              (view) -> {
                radioButtonOnClick(view);
              });
      dialogView
          .findViewById(R.id.radioOption2)
          .setOnClickListener(
              (view) -> {
                radioButtonOnClick(view);
              });
      dialogView
          .findViewById(R.id.radioOption3)
          .setOnClickListener(
              (view) -> {
                radioButtonOnClick(view);
              });

      if (Setting.SaveInFile.getSettingInt(
              Setting.Key.ConsoleMode, Setting.Default.ConsoleMode, context)
          == WebViewActivity.Console.NONE) {
        ((MaterialRadioButton) dialogView.findViewById(R.id.radioOption1)).setChecked(true);
      } else if (Setting.SaveInFile.getSettingInt(
              Setting.Key.ConsoleMode, Setting.Default.ConsoleMode, context)
          == WebViewActivity.Console.DEFAULT) {
        ((MaterialRadioButton) dialogView.findViewById(R.id.radioOption2)).setChecked(true);
      } else if (Setting.SaveInFile.getSettingInt(
              Setting.Key.ConsoleMode, Setting.Default.ConsoleMode, context)
          == WebViewActivity.Console.ERUDA) {
        ((MaterialRadioButton) dialogView.findViewById(R.id.radioOption3)).setChecked(true);
      }
    } else if (layout == R.layout.layout_language_chooser_radio_group) {
      dialogView
          .findViewById(R.id.radioOption1)
          .setOnClickListener(
              (view) -> {
                radioButtonOnClick(view);
              });
      dialogView
          .findViewById(R.id.radioOption2)
          .setOnClickListener(
              (view) -> {
                radioButtonOnClick(view);
              });
      dialogView
          .findViewById(R.id.radioOption3)
          .setOnClickListener(
              (view) -> {
                radioButtonOnClick(view);
              });
      if (Setting.SaveInFile.getSettingString(Setting.Key.Language, Languages.English, context)
          .equals(Languages.English)) {
        ((MaterialRadioButton) dialogView.findViewById(R.id.radioOption1)).setChecked(true);
      } else if (Setting.SaveInFile.getSettingString(
              Setting.Key.Language, Languages.English, context)
          .equals(Languages.Hindi)) {
        ((MaterialRadioButton) dialogView.findViewById(R.id.radioOption2)).setChecked(true);
      } else if (Setting.SaveInFile.getSettingString(
              Setting.Key.Language, Languages.English, context)
          .equals(Languages.Persian)) {
        ((MaterialRadioButton) dialogView.findViewById(R.id.radioOption3)).setChecked(true);
      }
    }
    dialog.setView(dialogView);

    dialog.setTitle(title);
    builder = dialog.create();
    builder.show();
  }

  public void radioButtonOnClick(View view) {
    if (layout == R.layout.layout_editor_chooser_radio_group) {
      RadioGroup radioGroup = ((RadioGroup) ((MaterialRadioButton) view).getParent());
      int selectedId = view.getId();
      if (selectedId == R.id.radioOption1) {
        Setting.SaveInFile.setSetting(
            Setting.Key.CodeEditor, CodeEditorLayout.AceCodeEditor, context);
      } else if (selectedId == R.id.radioOption2) {
        Setting.SaveInFile.setSetting(
            Setting.Key.CodeEditor, CodeEditorLayout.SoraCodeEditor, context);
      }
    } else if (layout == R.layout.layout_console_chooser_radio_group) {
      RadioGroup radioGroup = ((RadioGroup) ((MaterialRadioButton) view).getParent());
      int selectedId = view.getId();
      if (selectedId == R.id.radioOption1) {
        Setting.SaveInFile.setSetting(
            Setting.Key.ConsoleMode, WebViewActivity.Console.NONE, context);
      } else if (selectedId == R.id.radioOption2) {
        Setting.SaveInFile.setSetting(
            Setting.Key.ConsoleMode, WebViewActivity.Console.DEFAULT, context);
      } else if (selectedId == R.id.radioOption3) {
        Setting.SaveInFile.setSetting(
            Setting.Key.ConsoleMode, WebViewActivity.Console.ERUDA, context);
      }
    } else if (layout == R.layout.layout_language_chooser_radio_group) {
      int selectedId = view.getId();
      if (selectedId == R.id.radioOption1) {
        Setting.SaveInFile.setSetting(Setting.Key.Language, Languages.Default, context);
        ((SettingActivity) context).setLangugeMode();
        ((SettingActivity) context).refreshActivityIfRequired();
      } else if (selectedId == R.id.radioOption2) {
        Setting.SaveInFile.setSetting(Setting.Key.Language, Languages.Hindi, context);
        ((SettingActivity) context).setLangugeMode();
        ((SettingActivity) context).refreshActivityIfRequired();
      } else if (selectedId == R.id.radioOption3) {
        Setting.SaveInFile.setSetting(Setting.Key.Language, Languages.Persian, context);
        ((SettingActivity) context).setLangugeMode();
        ((SettingActivity) context).refreshActivityIfRequired();
      }
    }
    builder.dismiss();
  }
}
