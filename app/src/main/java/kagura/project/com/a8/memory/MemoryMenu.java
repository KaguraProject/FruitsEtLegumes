package kagura.project.com.a8.memory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import kagura.project.com.a8.R;

import static android.R.attr.button;

public class MemoryMenu extends AppCompatActivity{

    Intent intentMemory;
    Intent intentBack;
    ImageView avatar;
    String avatarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_memory_menu);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        avatarName = preferences.getString(getString(R.string.avatar), null);


        avatar = (ImageView) findViewById(R.id.imageAvatar);
        Log.i("Avatar", avatarName);
        switch(avatarName) {
            case "verMince":
                avatar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ver_mince_hd));
                break;
            case "verGros":
                avatar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ver_gros_hd_reverse));
                break;
        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_start, R.anim.left_end);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 66){
            finish();
        }
    }

    public void startGame(View v) {
        int button = 1;
        Log.i("view", Integer.toString(v.getId()));
        Log.i("1", Integer.toString(R.id.level_1tv));
        Log.i("2", Integer.toString(R.id.level_2tv));
        Log.i("3", Integer.toString(R.id.level_3tv));
        switch (v.getId()){
            case R.id.level_1tv:
                button = 1;
                break;
            case R.id.level_2tv:
                button = 2;
                break;
            case R.id.level_3tv:
                button = 3;
                break;
        }
        Log.i("button", Integer.toString(button));
        intentMemory = new Intent(this, MemoryGame.class);
        intentMemory.putExtra("level", button);
        this.startActivityForResult(intentMemory, 0);
    }

    public void back(View v) {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_start, R.anim.left_end);
    }


}
