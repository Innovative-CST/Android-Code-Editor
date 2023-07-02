package android.code.editor.files.utils;

import android.code.editor.R;
import android.code.editor.utils.Git;
import android.content.Context;
import android.widget.ImageView;

import java.io.File;

public class FileIcon {
    public static void setUpIcon(Context context, String path, ImageView imageview) {
        if (new File(path).isDirectory()) {
            imageview.setImageResource(R.drawable.ic_folder_black_24dp);
        } else if (new File(path).isFile()) {
            switch (FileManager.getPathFormat(path)) {
                case "java":
                    imageview.setImageResource(R.drawable.ic_language_java);
                    break;
                case "xml":
                    imageview.setImageResource(R.drawable.file_xml_box);
                    break;
                case "html":
                    imageview.setImageResource(R.drawable.language_html);
                    break;
                case "css":
                    imageview.setImageResource(R.drawable.language_css);
                    break;
                case "js":
                    imageview.setImageResource(R.drawable.language_javascript);
                    break;
                case "json":
                    imageview.setImageResource(R.drawable.language_json);
                    break;
                case "md":
                    imageview.setImageResource(R.drawable.language_markdown);
                    break;
                default:
                    imageview.setImageResource(R.drawable.icon_file);
                    break;
            }
        }
    }
}
