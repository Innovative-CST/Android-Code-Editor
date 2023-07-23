package android.code.editor.utils;

import android.code.editor.R;
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
    }
    builder.dismiss();
  }
}
