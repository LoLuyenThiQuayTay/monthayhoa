package hecosodulieudaphuongtien.demonhom19.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.sql.SQLException;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.sqlite.DataBaseHelper;

public class MainActivity extends AppCompatActivity {
    //    private MyPlayer myPlayer;
    public int DIALOG_DOWNLOAD = 1011;
    public ProgressDialog progressDialog;
    public int FRAGMENT_CONTENT = R.id.fragment_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        myPlayer = new MyPlayer(this);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.createDataBase();
        try {
            dataBaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        Snackbar.make(this.getCurrentFocus(), message, Snackbar.LENGTH_SHORT).show();
    }

//    public MyPlayer getPlayerController() {
//        return myPlayer;
//    }


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

}
