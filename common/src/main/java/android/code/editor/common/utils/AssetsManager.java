package android.code.editor.common.utils;

import android.content.Context;
import android.content.res.AssetManager;

public class AssetsManager {
  private Context myContext;

  public AssetsManager(Context c) {
    myContext = c;
  }

  public void saveFile(String path, String pathTo) {
    copyFile(path, pathTo);
  }

  public void saveFolder(String path, String pathTo) {
    copyAssets(path, pathTo);
  }

  private void copyAssets(final String _folder, final String _to) {
    AssetManager assetManager = myContext.getAssets();
    String[] files = null;
    try {
      files = assetManager.list(_folder);
    } catch (java.io.IOException e) {
    }
    if (files != null)
      for (String filename : files) {
        java.io.InputStream in = null;
        java.io.OutputStream out = null;
        try {
          in = assetManager.open(_folder + "/" + filename);
          if (!new java.io.File(_to).exists()) {
            new java.io.File(_to).mkdir();

            java.io.File outFile = new java.io.File(_to, filename);
            if (!(outFile.exists())) {
              out = new java.io.FileOutputStream(outFile);
              copyFile(in, out);
            }

          } else {

            java.io.File outFile = new java.io.File(_to, filename);
            if (!(outFile.exists())) {
              out = new java.io.FileOutputStream(outFile);
              copyFile(in, out);
            }
          }
        } catch (java.io.IOException e) {
        } finally {
          if (in != null) {
            try {
              in.close();
            } catch (java.io.IOException e) {
            }
          }
          if (out != null) {
            try {
              out.close();
            } catch (java.io.IOException e) {
            }
          }
        }
      }
  }

  private void copyFile(java.io.InputStream in, java.io.OutputStream out)
      throws java.io.IOException {
    byte[] buffer = new byte[1024];
    int read;
    while ((read = in.read(buffer)) != -1) {
      out.write(buffer, 0, read);
    }
  }

  private void copyFile(String filename, String outPath) {
    AssetManager assetManager = myContext.getAssets();

    java.io.InputStream in;
    java.io.OutputStream out;
    try {
      in = assetManager.open(filename);
      String newFileName = outPath + "/" + filename;
      out = new java.io.FileOutputStream(newFileName);

      byte[] buffer = new byte[1024];
      int read;
      while ((read = in.read(buffer)) != -1) {
        out.write(buffer, 0, read);
      }
      in.close();
      out.flush();
      out.close();
    } catch (Exception e) {
    }
  }
}
