package id.itk.yaf.wisataku.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
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
import id.itk.yaf.wisataku.Model.Wisata;
import id.itk.yaf.wisataku.R;
import id.itk.yaf.wisataku.Utility.ImageUtility;

public class RecyclerviewListWisataAdapter extends RecyclerView.Adapter<RecyclerviewListWisataAdapter.CustomViewHolder> {

    private List<Wisata> dataWisata;
    private Context mContext;
    public View root = null;
    private Bitmap bitmap;

    public RecyclerviewListWisataAdapter(Context mContext, List<Wisata> dataWisata){
        this.mContext = mContext;
        this.dataWisata = dataWisata;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_list_wisata, parent, false);

        RecyclerviewListWisataAdapter.CustomViewHolder customViewHolder = new RecyclerviewListWisataAdapter.CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Wisata wisata = dataWisata.get(position);

        ImageView thumbnailWisata = ((CustomViewHolder) holder).thumbnailWisata;
        bitmap = ImageUtility.convert(wisata.getImage());
        Glide.with(mContext)
                .load(bitmap)
                .into(thumbnailWisata);

        holder.titleWisata.setText(wisata.getTitle());
    }

    @Override
    public int getItemCount() {
        return dataWisata.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnailWisata)
        ImageView thumbnailWisata;

        @BindView(R.id.titleWisata)
        TextView titleWisata;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
