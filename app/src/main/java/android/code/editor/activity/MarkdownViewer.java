package android.code.editor.activity;

import android.code.editor.R;
import android.code.editor.ui.MaterialColorHelper;
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
        MaterialColorHelper.setUpTheme(this);
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
