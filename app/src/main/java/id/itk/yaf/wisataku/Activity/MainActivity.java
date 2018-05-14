package id.itk.yaf.wisataku.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.itk.yaf.wisataku.Fragment.ProfileFragment;
import id.itk.yaf.wisataku.Fragment.WisataFragment;
import id.itk.yaf.wisataku.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bnve)
    BottomNavigationViewEx bnve;

    
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);
        mContext = this;

        initComponents();
    }

    private void initComponents() {
        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setIconSize(24, 24);

        bnve();
    }

    @OnClick(R.id.floatingActionButton)
    public void tambahWisata(){
        Intent i = new Intent(MainActivity.this, TambahWisata.class);
        startActivity(i);
    }

    public void bnve() {
        bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_item1:
                        selectedFragment = WisataFragment.newInstance();
                        break;

                    case R.id.action_item2:
                        selectedFragment = ProfileFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameFragment, selectedFragment);
                transaction.commit();
                return true;
            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameFragment, WisataFragment.newInstance());
        transaction.commit();
    }

}
