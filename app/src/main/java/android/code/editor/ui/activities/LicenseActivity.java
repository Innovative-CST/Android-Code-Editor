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
import android.code.editor.common.utils.FileUtils;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class LicenseActivity extends BaseActivity {

  private TextView licenseText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_license);
    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.open_source_licenses);
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
    licenseText = findViewById(R.id.licenseText);
    licenseText.setAutoLinkMask(Linkify.WEB_URLS);
    licenseText.setMovementMethod(LinkMovementMethod.getInstance());
    licenseText.setText(FileUtils.readFileFromAssets(getAssets(), "oos.text"));
  }
}
