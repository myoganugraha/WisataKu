package id.itk.yaf.wisataku.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import id.itk.yaf.wisataku.Model.Wisata;
import id.itk.yaf.wisataku.R;

public class DetailWisata extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Context mContext;
    private ImageView detailImage;
    private Toolbar toolbar;
    Wisata dataWisata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);

        mContext = this;
        initComponents();
    }

    private void initComponents() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        detailImage = (ImageView) findViewById(R.id.detailThumbnail);
        dataWisata = getIntent().getParcelableExtra("dataWisata");

        collapsingToolbarLayout.setTitle(dataWisata.getTitle());
        Glide.with(mContext)
                .load(dataWisata.getImage())
                .into(detailImage);

    }
}
