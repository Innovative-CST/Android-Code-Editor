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

package android.code.editor.ui.activities;

import android.code.editor.R;
import android.code.editor.ui.adapters.ContributorsListAdapter;
import android.code.editor.utils.RequestNetwork;
import android.code.editor.utils.RequestNetworkController;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.*;
import org.json.JSONException;

public class ContributorsActivity extends BaseActivity {
  private LinearLayout main;

  private LinearLayout loading;

  private RecyclerView list;

  public String contributorsData =
      "https://raw.githubusercontent.com/TS-Code-Editor/Android-Code-Editor/main/assets/contributors.json";

  public RequestNetwork reqNetwork;

  public RequestNetwork.RequestListener reqListener;

  public ArrayList<HashMap<String, Object>> contributorsList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle arg0) {
    super.onCreate(arg0);
    setContentView(R.layout.activity_contributors);
    initActivity();
  }

  public void initActivity() {
    // Initialze views in layout
    init();
    main.setVisibility(View.GONE);
    loading.setVisibility(View.VISIBLE);

    reqNetwork = new RequestNetwork(this);

    reqListener =
        new RequestNetwork.RequestListener() {
          @Override
          public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
            final String _tag = _param1;
            final String _response = _param2;
            final HashMap<String, Object> _responseHeaders = _param3;
            try {
              loading.setVisibility(View.GONE);
              main.setVisibility(View.VISIBLE);
              JSONObject object = new JSONObject(_response);
              JSONArray contributors = object.getJSONArray("Contributors");
              for (int count = 0; count < contributors.length(); count++) {
                {
                  HashMap<String, Object> _item = new HashMap<>();
                  _item.put("Name", contributors.getJSONObject(count).getString("Name"));
                  _item.put("Image", contributors.getJSONObject(count).getString("Image"));
                  _item.put(
                      "Description", contributors.getJSONObject(count).getString("Description"));
                  if (!contributors.getJSONObject(count).isNull("markdownUrl")) {
                    _item.put(
                        "markdownUrl", contributors.getJSONObject(count).getString("markdownUrl"));
                  }
                  contributorsList.add(_item);
                }
              }
              list.setAdapter(new ContributorsListAdapter(contributorsList, ContributorsActivity.this));
              list.setLayoutManager(new LinearLayoutManager(ContributorsActivity.this));
            } catch (JSONException e) {
              Toast.makeText(ContributorsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
          }

          @Override
          public void onErrorResponse(String _param1, String _param2) {
            final String _tag = _param1;
            final String _message = _param2;
          }
        };

    reqNetwork.startRequestNetwork(
        RequestNetworkController.GET, contributorsData, "Contributors", reqListener);
  }

  public void init() {
    // Setup Toolbat
    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.contributors);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View arg0) {
            onBackPressed();
          }
        });
    // Define views
    list = findViewById(R.id.list);
    main = findViewById(R.id.main);
    loading = findViewById(R.id.loading);
  }
}
