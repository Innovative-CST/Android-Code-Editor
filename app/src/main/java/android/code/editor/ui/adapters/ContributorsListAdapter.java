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

package android.code.editor.ui.adapters;

import android.code.editor.R;
import android.code.editor.ui.activities.ContributorsActivity;
import android.code.editor.ui.activities.MarkdownViewerActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.*;

public class ContributorsListAdapter
    extends RecyclerView.Adapter<ContributorsListAdapter.ViewHolder> {

  ArrayList<HashMap<String, Object>> _data;
  public ImageView profile;
  public TextView name;
  public TextView description;
  public ContributorsActivity mContributorsActivity;

  public ContributorsListAdapter(
      ArrayList<HashMap<String, Object>> _arr, ContributorsActivity mContributorsActivity) {
    _data = _arr;
    this.mContributorsActivity = mContributorsActivity;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater _inflater = mContributorsActivity.getLayoutInflater();
    View _v = _inflater.inflate(R.layout.contributors, null);
    RecyclerView.LayoutParams _lp =
        new RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    _v.setLayoutParams(_lp);
    return new ViewHolder(_v);
  }

  @Override
  public void onBindViewHolder(ViewHolder _holder, final int _position) {
    View _view = _holder.itemView;
    profile = _view.findViewById(R.id.profile);
    MultiTransformation multi = new MultiTransformation<Bitmap>(new CircleCrop());
    Glide.with(mContributorsActivity)
        .load(Uri.parse(_data.get(_position).get("Image").toString()))
        .thumbnail(0.10F)
        .into(profile);
    name = _view.findViewById(R.id.name);
    description = _view.findViewById(R.id.description);
    name.setText(_data.get(_position).get("Name").toString());
    description.setText(_data.get(_position).get("Description").toString());
    _view.findViewById(R.id.infoInMarkDown).setVisibility(View.GONE);
    if (_data.get(_position).containsKey("markdownUrl")) {
      _view.findViewById(R.id.infoInMarkDown).setVisibility(View.VISIBLE);
      _view
          .findViewById(R.id.infoInMarkDown)
          .setOnClickListener(
              (view) -> {
                Intent i = new Intent();
                i.setClass(mContributorsActivity, MarkdownViewerActivity.class);
                i.putExtra("type", "url");
                i.putExtra("style", "github");
                i.putExtra("title", _data.get(_position).get("Name").toString());
                i.putExtra("data", _data.get(_position).get("markdownUrl").toString());
                mContributorsActivity.startActivity(i);
              });
    }
  }

  @Override
  public int getItemCount() {
    return _data.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View v) {
      super(v);
    }
  }
}
