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

import fi.iki.elonen.NanoHTTPD;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import android.util.Log;

public class nanohttpd {
  public WebServer server;

  private int port;

  private String NameFolder;
  private String indexFile;

  public nanohttpd(int port, String NameFolder, String indexFile) {
    this.port = port;
    this.NameFolder = NameFolder;
    this.indexFile = indexFile;
  }

  public void startServer() {
    try {
      server = new WebServer(port);
      server.start();
    } catch (IOException e) {
      Log.e("WebServer", "Failed to start the server: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void stopServer() {
    if (server != null) {
      server.stop();
    }
  }

  public String getLocalIpAddress() {
    return "http://localhost:"+port;
  }

  private class WebServer extends NanoHTTPD {

    public WebServer(int port) {
      super("localhost", port);
    }

    @Override
    public Response serve(IHTTPSession session) {
      String uri = session.getUri();
      if (uri.endsWith("/")) {
        uri += indexFile;
      }
      String filePath = NameFolder + uri;

      try {
        if (new File(filePath).exists()) {
          FileInputStream fis = new FileInputStream(filePath);

          int contentLength = fis.available();

          return newFixedLengthResponse(
              NanoHTTPD.Response.Status.OK, getMimeTypeForFile(filePath), fis, contentLength);
        } else {
          return newFixedLengthResponse(
              NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "File not found.");
        }
      } catch (IOException e) {
        Log.e("WebServer", e.toString());
        e.printStackTrace();
        return newFixedLengthResponse(
            NanoHTTPD.Response.Status.INTERNAL_ERROR,
            NanoHTTPD.MIME_PLAINTEXT,
            "Internal server error.");
      }
    }
  }
}
