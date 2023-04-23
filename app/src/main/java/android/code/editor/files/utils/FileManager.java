package android.code.editor.files.utils;

import android.net.Uri;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Stream;

public class FileManager {
	public static void setUpFileList(ArrayList<HashMap<String, Object>> listMap,ArrayList<String> listString) {
		final class FileComparator implements Comparator<String> {
			public int compare(String f1, String f2) {
					if(f1 == f2) return 0;
					if(new File(f1).isDirectory() && new File(f2).isFile())
					return -1;
					if(new File(f1).isFile() && new File(f2).isDirectory())
					return 1;
					return f1.compareToIgnoreCase(f2);
			}
		}
		Collections.sort(listString, new FileComparator());
		int pos = 0;
		for(int _repeat13 = 0; _repeat13 < (int)(listString.size()); _repeat13++) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("path", listString.get((int)(pos)));
				_item.put("lastSegmentOfFilePath",getLatSegmentOfFilePath(listString.get(pos)));
				listMap.add((int)pos, _item);
			}
		
			pos++;
		}
	}
	
	public static void listDir(String path, ArrayList<String> list) {
        File dir = new File(path);
        if (!dir.exists() || dir.isFile()) return;

        File[] listFiles = dir.listFiles();
        if (listFiles == null || listFiles.length <= 0) return;

        if (list == null) return;
        list.clear();
        for (File file : listFiles) {
            list.add(file.getAbsolutePath());
        }
    }
	
	public static String getLatSegmentOfFilePath(String path) {
		return Uri.parse(path).getLastPathSegment();
	}
	
	public static boolean ifFileFormatIsEqualTo(String path,String format){
		try {
			return Uri.parse(path).getLastPathSegment().substring(Uri.parse(path).getLastPathSegment().length() - ".".concat(format).length(), Uri.parse(path).getLastPathSegment().length()).equals(".".concat(format));
		} catch(Exception e) {
			return false;
		}
	}
}
