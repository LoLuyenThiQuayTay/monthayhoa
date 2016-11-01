package hecosodulieudaphuongtien.demonhom19.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.sql.SQLException;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.mediaplayer.MyPlayer;
import hecosodulieudaphuongtien.demonhom19.sqlite.DataBaseHelper;

public class MainActivity extends AppCompatActivity {
    public int DIALOG_DOWNLOAD = 1011;
    public ProgressDialog progressDialog;
    public int FRAGMENT_CONTENT = R.id.fragment_content;
    public FloatingActionButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyPlayer.initIfNeed(this);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.createDataBase();
        try {
            dataBaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btn = (FloatingActionButton) findViewById(R.id.btn_player);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragmentUp(new PlayerFragment(MyPlayer.getAudioPlaying()));
            }
        });
        addFragmentContent(new HomeFragment());

    }

    public void addFragmentContent(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(FRAGMENT_CONTENT, fragment, "FRAGMENT").commit();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.right_enter, R.anim.left_exit, R.anim.left_enter, R.anim.right_exit);
        transaction.replace(FRAGMENT_CONTENT, fragment, "FRAGMENT").addToBackStack("FRAGMENT").commit();
    }

    public void replaceFragmentUp(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.top_enter, R.anim.stand_hide, R.anim.stand_show, R.anim.top_exit);
        transaction.replace(FRAGMENT_CONTENT, fragment, "FRAGMENT").addToBackStack("FRAGMENT").commit();
    }


    public void makeNotification(String message) {
        try {
            Snackbar.make(this.getCurrentFocus(), message, Snackbar.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DOWNLOAD) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Downloading..");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            return progressDialog;
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btn.getWindowToken(), 0);

    }
}
