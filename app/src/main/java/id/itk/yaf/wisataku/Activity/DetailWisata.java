package id.itk.yaf.wisataku.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.itk.yaf.wisataku.Model.Wisata;
import id.itk.yaf.wisataku.R;

public class DetailWisata extends AppCompatActivity {

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.detailThumbnail)
    ImageView detailImage;

    @BindView(R.id.description_detail)
    TextView desciptionDetail;

    private Context mContext;
    Wisata dataWisata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);

        ButterKnife.bind(this);
        mContext = this;
        initComponents();
    }

    private void initComponents() {
        dataWisata = getIntent().getParcelableExtra("dataWisata");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout.setTitle(dataWisata.getTitle());
        Glide.with(mContext)
                .load(dataWisata.getImage())
                .into(detailImage);

        desciptionDetail.setText(dataWisata.getDescription());
    }
}
