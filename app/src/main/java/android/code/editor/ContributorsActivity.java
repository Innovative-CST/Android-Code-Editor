package android.code.editor;

import android.code.editor.Sketchware.RequestNetwork;
import android.code.editor.Sketchware.RequestNetworkController;
import android.code.editor.ui.MaterialColorHelper;
import android.code.editor.utils.ThemeObservable;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.json.*;
import org.json.JSONException;

public class ContributorsActivity extends AppCompatActivity implements Observer {
    private LinearLayout main;
    
    private LinearLayout loading;
    
	private RecyclerView list;
    
    public String contributorsData = "https://raw.githubusercontent.com/TS-Code-Editor/Android-Code-Editor/main/assets/contributors.json";
    
	public RequestNetwork reqNetwork;
    
    public RequestNetwork.RequestListener reqListener;
    
    public  ArrayList<HashMap<String, Object>> contributorsList = new ArrayList<>();
	
	private ThemeObservable themeObservable = new ThemeObservable();

    
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        // TODO: Implement this method
		((MyApplication)getApplication()).getThemeObservable().addObserver(this);
		MaterialColorHelper.setUpTheme(this);
        setContentView(R.layout.activity_contributors);
		initActivity();
    }
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: Implement this method
		((MyApplication)getApplication()).getThemeObservable().deleteObserver(this);
    }
	
	public void initActivity() {
		// Initialze views in layout
		init();
        main.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        
        reqNetwork = new RequestNetwork(this);
		
		reqListener = new RequestNetwork.RequestListener() {
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
							_item.put("Description", contributors.getJSONObject(count).getString("Description"));
	              	      contributorsList.add(_item);
                        }
                    }
					list.setAdapter(new ContribitorsListAdaptor(contributorsList));
                    list.setLayoutManager(new LinearLayoutManager(ContributorsActivity.this));
                } catch (JSONException e) {
                    Toast.makeText(ContributorsActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		reqNetwork.startRequestNetwork(RequestNetworkController.GET,contributorsData,"Contributors",reqListener);
	}
    
    public void init() {
        // Setup Toolbat
        Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		toolbar.setTitleTextColor(
                MaterialColorHelper.getMaterialColorInt(
                        this, com.google.android.material.R.attr.colorOnPrimary));
        toolbar.getNavigationIcon()
                .setTint(
                        MaterialColorHelper.getMaterialColorInt(
                                this, com.google.android.material.R.attr.colorOnPrimary));
		// Define views
		list = findViewById(R.id.list);
		main = findViewById(R.id.main);
        loading = findViewById(R.id.loading);
    }
	
	public class ContribitorsListAdaptor extends RecyclerView.Adapter<ContribitorsListAdaptor.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		public ImageView profile;
		public TextView name;
		public TextView description;
		
		public ContribitorsListAdaptor(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.contributors, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			profile = _view.findViewById(R.id.profile);
			MultiTransformation multi = new MultiTransformation<Bitmap>(new CircleCrop());
			Glide
				.with(ContributorsActivity.this)
				.load(Uri.parse(_data.get(_position).get("Image").toString()))
				.thumbnail(0.10F)
				.apply(RequestOptions.bitmapTransform(multi))
				.into(profile);
			name = _view.findViewById(R.id.name);
			description = _view.findViewById(R.id.description);
			name.setText(_data.get(_position).get("Name").toString());
			description.setText(_data.get(_position).get("Description").toString());
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
	
	@Override
    public void update(Observable arg0, Object arg1) {
		if ((String)arg1 == "ThemeUpdated") {
			recreate();
		}
	}
}
