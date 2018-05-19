package id.itk.yaf.wisataku.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.codesgood.views.JustifiedTextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.itk.yaf.wisataku.Model.Wisata;
import id.itk.yaf.wisataku.R;

public class DetailWisata extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.detailThumbnail)
    ImageView detailImage;

    @BindView(R.id.description_detail)
    JustifiedTextView desciptionDetail;

    //@BindView(R.id.map)
    //SupportMapFragment mapFragment;

    private Context mContext;
    Wisata dataWisata;

    GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);

        ButterKnife.bind(this);
        mContext = this;
        initComponents();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initComponents() {
        dataWisata = getIntent().getParcelableExtra("dataWisata");

        setSupportActionBar(toolbar);

        collapsingToolbarLayout.setTitle(dataWisata.getTitle());
        Glide.with(mContext)
                .load(dataWisata.getImage())
                .into(detailImage);

        desciptionDetail.setText(dataWisata.getDescription());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng position = new LatLng(Double.parseDouble(dataWisata.getLatitude()), Double.parseDouble(dataWisata.getLongitude()));
        googleMap.addMarker(new MarkerOptions()
                .position(position)
                .title(dataWisata.getTitle()));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,15.0f));
    }
}
