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
 *   along with Android Code Editor.  If not, see <https://www.gnu.org/licenses/>.
 */

package android.code.editor.common.utils;

import android.app.Activity;
import android.code.editor.common.interfaces.FileDeleteListener;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileDeleteUtils {
  public static int count = 0;

  public static void getTotalCount(
      File file, FileDeleteListener listener, int totalCount, boolean isMain, Activity context) {
    ExecutorService exec = Executors.newSingleThreadExecutor();
    exec.execute(
        new FileDeleteUtils().new getCountRunnable(file, listener, totalCount, isMain, context));
  }

  public static void delete(
      File path, FileDeleteListener listener, boolean isMain, Activity context) {
    if (isMain) {
      ExecutorService exec = Executors.newSingleThreadExecutor();
      Runnable runnable =
          new Runnable() {
            @Override
            public void run() {
              if (path.isDirectory()) {
                for (File file : path.listFiles()) {
                  if (file.isFile()) {
                    context.runOnUiThread(
                        () -> {
                          listener.onDeleting(file);
                        });
                    file.delete();
                    context.runOnUiThread(
                        () -> {
                          listener.onDeleteComplete(file);
                        });
                  } else {
                    delete(file, listener, false, context);
                  }
                }
                context.runOnUiThread(
                    () -> {
                      listener.onDeleting(path);
                    });
                path.delete();
                context.runOnUiThread(
                    () -> {
                      listener.onDeleteComplete(path);
                    });
              } else {
                context.runOnUiThread(
                    () -> {
                      listener.onDeleting(path);
                    });
                path.delete();
                context.runOnUiThread(
                    () -> {
                      listener.onDeleteComplete(path);
                    });
              }
              if (isMain) {
                context.runOnUiThread(
                    () -> {
                      listener.onTaskComplete();
                    });
              }
            }
          };
      exec.execute(runnable);
    } else {
      if (path.isDirectory()) {
        for (File file : path.listFiles()) {
          if (file.isFile()) {
            context.runOnUiThread(
                () -> {
                  listener.onDeleting(file);
                });
            file.delete();
            context.runOnUiThread(
                () -> {
                  listener.onDeleteComplete(file);
                });
          } else {
            delete(file, listener, false, context);
          }
        }
        context.runOnUiThread(
            () -> {
              listener.onDeleting(path);
            });
        path.delete();
        context.runOnUiThread(
            () -> {
              listener.onDeleteComplete(path);
            });
      } else {
        context.runOnUiThread(
            () -> {
              listener.onDeleting(path);
            });
        path.delete();
        context.runOnUiThread(
            () -> {
              listener.onDeleteComplete(path);
            });
      }
    }
  }

  public class getCountRunnable implements Runnable {
    File file;
    FileDeleteListener listener;
    int totalCount;
    boolean isMain;
    Activity context;

    public getCountRunnable(
        File _file,
        FileDeleteListener _listener,
        int _totalCount,
        boolean _isMain,
        Activity _context) {
      file = _file;
      listener = _listener;
      totalCount = _totalCount;
      isMain = _isMain;
      context = _context;
    }

    @Override
    public void run() {
      getTotalCount(file, listener, true);
    }

    public void getTotalCount(File file, FileDeleteListener listener, boolean isMain) {
      for (File path : file.listFiles()) {
        if (path.isFile()) {
          this.totalCount = this.totalCount + 1;
        } else {
          this.totalCount = this.totalCount + 1;
          getTotalCount(path, listener, false);
        }
      }
      if (isMain) {
        final int i = this.totalCount;
        context.runOnUiThread(
            () -> {
              listener.onTotalCount(i);
            });
      }
    }
  }
}
