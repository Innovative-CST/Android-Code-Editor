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

package android.code.editor.utils.sketchware;

import android.app.Activity;
import java.util.HashMap;

public class RequestNetwork {
  private HashMap<String, Object> params = new HashMap<>();
  private HashMap<String, Object> headers = new HashMap<>();

  private Activity activity;

  private int requestType = 0;

  public RequestNetwork(Activity activity) {
    this.activity = activity;
  }

  public void setHeaders(HashMap<String, Object> headers) {
    this.headers = headers;
  }

  public void setParams(HashMap<String, Object> params, int requestType) {
    this.params = params;
    this.requestType = requestType;
  }

  public HashMap<String, Object> getParams() {
    return params;
  }

  public HashMap<String, Object> getHeaders() {
    return headers;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getRequestType() {
    return requestType;
  }

  public void startRequestNetwork(
      String method, String url, String tag, RequestListener requestListener) {
    RequestNetworkController.getInstance().execute(this, method, url, tag, requestListener);
  }

  public interface RequestListener {
    public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders);

    public void onErrorResponse(String tag, String message);
  }
}
