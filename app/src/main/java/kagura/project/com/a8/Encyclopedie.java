package kagura.project.com.a8;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import kagura.project.com.a8.adapters.ImageAdapter;


public class Encyclopedie extends AppCompatActivity {

    GridView gridViewFruits;
    private List<String> fruitsName;
    private boolean isImagesLoaded;
    private Integer[] idDrawables;
    private boolean isMenuView = true;

    ImageView fruitPlein, fruitCoupe, fruitArbre, fruitGraine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //noinspection ConstantConditions
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_solution_menu);
        
        loadCards();
        loadMenu();
    }

    private void loadMenu() {
        gridViewFruits = (GridView) findViewById(R.id.gridviewFruits);

        gridViewFruits.setAdapter(new ImageAdapter(this, idDrawables));

        gridViewFruits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setContentView(R.layout.activity_solution);
                isMenuView = false;
                loadFruit(position);
            }
        });
    }

    @Override
    public void onBackPressed() {}

    private void loadFruit(int position) {
        fruitPlein = (ImageView) findViewById(R.id.fruitPlein);
        fruitCoupe = (ImageView) findViewById(R.id.fruitCoupe);
        fruitArbre = (ImageView) findViewById(R.id.fruitArbre);
        fruitGraine = (ImageView) findViewById(R.id.fruitGraine);

        fruitPlein.setImageDrawable(getFruitResource(position, "plein"));
        fruitCoupe.setImageDrawable(getFruitResource(position, "coupe"));
        fruitArbre.setImageDrawable(getFruitResource(position, "arbre"));
        fruitGraine.setImageDrawable(getFruitResource(position, "graine"));
    }

    private Drawable getFruitResource(int position, String type) {
        String pathFruit = getString(getResources().getIdentifier("fruit_" + type + "_path", "string", getPackageName())) + fruitsName.get(position);
        return getResources().getDrawable(getResources().getIdentifier(pathFruit, "drawable", getPackageName()));
    }

    private void loadCards() {
        if (!isImagesLoaded) {
            buildListFruits();
        }

        idDrawables = new Integer[fruitsName.size()];

        for(int i = 0; i < fruitsName.size(); i++){
            idDrawables[i] = getResources().getIdentifier(getString(R.string.fruit_plein_path) + fruitsName.get(i), "drawable", getPackageName());
            Log.i("k", Integer.toString(getResources().getIdentifier(getString(R.string.fruit_plein_path) + fruitsName.get(i), "drawable", getPackageName())));
        }
    }

    private void buildListFruits() {
        isImagesLoaded = true;
        fruitsName = new ArrayList<>();

        try {
            LoadJson lj = new LoadJson();
            JSONObject obj = new JSONObject(lj.loadJSONFromAsset(this, "fruits"));
            JSONArray arr = obj.getJSONArray("fruits");
            for (int i = 0; i < arr.length(); i++) {

                fruitsName.add(Normalizer.normalize(arr.get(i).toString().toLowerCase(), Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
                Log.i("fruit", fruitsName.get(i));
            }
            Log.i("kkk", fruitsName.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void backToMenu(View view) {
        if(isMenuView){
            super.onBackPressed();
            overridePendingTransition(R.anim.down_start, R.anim.down_end);
        }else{
            setContentView(R.layout.activity_solution_menu);
            loadMenu();
            isMenuView = true;
        }
    }
}
