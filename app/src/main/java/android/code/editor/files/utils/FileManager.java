package android.code.editor.files.utils;

import android.net.Uri;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
	
	public static void writeFile(String path, String str) {
        createNewFile(path);
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(new File(path), false);
            fileWriter.write(str);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    
    public static String readFile(String path) {
        createNewFile(path);

        StringBuilder sb = new StringBuilder();
        FileReader fr = null;
        try {
            fr = new FileReader(new File(path));

            char[] buff = new char[1024];
            int length = 0;

            while ((length = fr.read(buff)) > 0) {
                sb.append(new String(buff, 0, length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
    
    public static void createNewFile(String path) {
        int lastSep = path.lastIndexOf(File.separator);
        if (lastSep > 0) {
            String dirPath = path.substring(0, lastSep);
            makeDir(dirPath);
        }

        File file = new File(path);

        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void makeDir(String path) {
        if (new File(path).exists()) {
            File file = new File(path);
            file.mkdirs();
        }
    }

}
