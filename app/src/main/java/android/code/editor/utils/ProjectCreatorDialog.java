package android.code.editor.utils;

import android.code.editor.R;
import android.content.Context;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ProjectCreatorDialog {
    private Context context;
    
    private BottomSheetDialog projectTemplateBottomSheetDialog;
    
    public ProjectCreatorDialog(Context context) {
        this.context = context;
        projectTemplateBottomSheetDialog = new BottomSheetDialog(context);
        projectTemplateBottomSheetDialog.setContentView(R.layout.layout_project_template_bottomsheet);
        projectTemplateBottomSheetDialog.create();
    }
    
    public void show() {
        projectTemplateBottomSheetDialog.show();
    }
}
