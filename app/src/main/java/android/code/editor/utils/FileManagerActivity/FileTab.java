package android.code.editor.utils.FileManagerActivity;

import android.code.editor.files.utils.FileManager;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class FileTab {
  private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
  private File file;

  public FileTab(File file) {
    if (!file.exists()) {
      FileManager.createNewFile(file.getAbsolutePath());
    }
  }

  public void initList() {
    String settingData = FileManager.readFile(file.getAbsolutePath());
    try {
      TypeToken<ArrayList<HashMap<String, Object>>> tokentype = new TypeToken<>() {};
      list = new Gson().fromJson(settingData, tokentype);
      if (list == null) {
        list = new ArrayList<HashMap<String, Object>>();
      }

    } catch (JsonParseException e) {
      list = new ArrayList<HashMap<String, Object>>();
    }
  }

  public void addPathInList(File filePath, Object obj) {
    initList();
    HashMap<String, Object> map = new HashMap<String, Object>();

    map.put("path", filePath.getAbsolutePath());
    map.put("data", obj);

    if (ifPathInListExists(filePath)) {
      list.add(getPosOfPath(filePath), map);
    } else {
      list.add(map);
    }
    FileManager.writeFile(file.getAbsolutePath(), new Gson().toJson(list));
  }

  public void removePathInList(File filePath) {
    initList();
    if (ifPathInListExists(filePath)) {
      list.remove(getPosOfPath(filePath));
      FileManager.writeFile(file.getAbsolutePath(), new Gson().toJson(list));
    }
  }

  public boolean ifPathInListExists(File path) {
    initList();
    for (int pos = 0; pos < list.size(); pos++) {
      if (list.get(pos).containsKey("path")) {
        if (list.get(pos).get("path").equals(path.getAbsolutePath())) {
          return true;
        }
      }
    }
    return false;
  }

  public int getPosOfPath(File path) {
    initList();
    for (int pos = 0; pos < list.size(); pos++) {
      if (list.get(pos).containsKey("path")) {
        if (list.get(pos).get("path").equals(path.getAbsolutePath())) {
          return pos;
        }
      }
    }
    return -1;
  }
}
