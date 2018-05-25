package id.itk.yaf.wisataku.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.itk.yaf.wisataku.API.BaseAPIService;
import id.itk.yaf.wisataku.Activity.DetailWisata;
import id.itk.yaf.wisataku.Model.Wisata;
import id.itk.yaf.wisataku.R;

public class RecyclerviewFragmentProfileAdapter extends RecyclerView.Adapter<RecyclerviewFragmentProfileAdapter.CustomViewHolder> {

    private List<Wisata> dataWisata;
    private Context mContext;

    public RecyclerviewFragmentProfileAdapter(List<Wisata> dataWisata, Context mContext) {
        this.dataWisata = dataWisata;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_fragment_profile, parent, false);

        RecyclerviewFragmentProfileAdapter.CustomViewHolder customViewHolder = new RecyclerviewFragmentProfileAdapter.CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerviewFragmentProfileAdapter.CustomViewHolder holder, int position) {
        final Wisata wisata = dataWisata.get(position);


        Glide.with(mContext)
                .load(wisata.getImage())
                .into(holder.thumbnailWisataProfile);

        holder.thumbnailWisataProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailWisata.class);
                intent.putExtra("dataWisata", wisata);
                v.getContext().startActivity(intent);
            }
        });

        holder.titleWisataProfile.setText(wisata.getTitle());
    }

    @Override
    public int getItemCount() {
        return dataWisata.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnailWisataProfile)
        ImageView thumbnailWisataProfile;

        @BindView(R.id.titleWisataProfile)
        TextView titleWisataProfile;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
