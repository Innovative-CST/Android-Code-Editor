package android.code.editor.files.utils;

import android.code.editor.CodeEditorActivity;
import android.code.editor.FileManagerActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import editor.tsd.tools.Language;

import java.io.File;

public class FileTypeHandler {
    private File file;
    private View view;
    private Context context;

    public FileTypeHandler(Context context) {
        this.context = context;
    }

    public void handleFile(File file) {
        this.file = file;
    }

    public void setTargetView(View view) {
        this.view = view;
    }

    public void startHandling() {

        if (file.isDirectory()) {
            view.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra("path", file.getAbsolutePath());
                            intent.setClass(context, FileManagerActivity.class);
                            context.startActivity(intent);
                        }
                    });
        } else {
            final File FinalFile = file;
            switch (getFileFormat(file.getAbsolutePath())) {
                case "java":
                    view.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.putExtra("path", file.getAbsolutePath());
                                    intent.putExtra("LanguageMode", Language.Java);
                                    intent.setClass(context, CodeEditorActivity.class);
                                    context.startActivity(intent);
                                }
                            });
                    break;
                case "html":
                    view.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.putExtra("path", file.getAbsolutePath());
                                    intent.putExtra("LanguageMode", Language.HTML);
                                    intent.setClass(context, CodeEditorActivity.class);
                                    context.startActivity(intent);
                                }
                            });
                    break;
                default:
                    view.setOnClickListener(null);
                    break;
            }
        }
    }

    private String getFileFormat(String path) {
        int index = path.lastIndexOf('.');
        if (index > 0) {
            String extension = path.substring(index + 1);
            return extension;
        } else {
            return "";
        }
    }
}
