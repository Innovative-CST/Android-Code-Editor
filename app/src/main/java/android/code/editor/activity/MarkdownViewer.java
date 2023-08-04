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

package android.code.editor.activity;

import android.code.editor.R;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.styles.Github;
import java.io.File;

public class MarkdownViewer extends BaseActivity {

  public MarkdownView markdown_view;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_markdown_viewer);
    Toolbar toolbar = findViewById(R.id.toolbar);
    if (getIntent().hasExtra("title")) {
      toolbar.setTitle(getIntent().getStringExtra("title"));
    }
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
    markdown_view = findViewById(R.id.markdown_view);
    if (getIntent().hasExtra("style")) {
      if (getIntent().getStringExtra("style").equals("github")) {
        markdown_view.addStyleSheet(new Github());
      }
    }
    if (getIntent().getStringExtra("type").equals("url")) {
      markdown_view.loadMarkdownFromUrl(getIntent().getStringExtra("data"));
    } else if (getIntent().getStringExtra("type").equals("file")) {
      markdown_view.loadMarkdownFromFile(new File(getIntent().getStringExtra("data")));
    }
  }
}
