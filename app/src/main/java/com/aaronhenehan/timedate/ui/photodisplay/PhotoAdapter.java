package com.aaronhenehan.timedate.ui.photodisplay;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.aaronhenehan.timedate.R;
import com.aaronhenehan.timedate.model.Photo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    List<Photo> photos = new ArrayList<>();

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.title.setText(photo.getTitle() != null
                ? photo.getTitle()
                : "");
        String url = buildImageUrl(photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret());
        Glide.with(holder.itemView.getContext())
                .load(url)
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.loadingSpinner.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.loadingSpinner.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.photoImage);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void setPhotos(List<Photo> photos) {
        this.photos.clear();
        this.photos = photos;
        notifyDataSetChanged();
    }

    private String buildImageUrl(int farmId, String serverId, String id, String secret) {
        // Format https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
        StringBuilder sb = new StringBuilder();
        sb.append("https://farm")
                .append(farmId)
                .append(".staticflickr.com/")
                .append(serverId)
                .append("/")
                .append(id)
                .append("_")
                .append(secret)
                .append(".jpg");

        return sb.toString();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        private View loadingSpinner;
        private ImageView photoImage;
        private TextView title;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImage = itemView.findViewById(R.id.photo_image);
            title = itemView.findViewById(R.id.photo_title);
            loadingSpinner = itemView.findViewById(R.id.image_loading_spinner);
        }
    }
}
