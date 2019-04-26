package com.test.axxess.is.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.test.axxess.is.R;
import com.test.axxess.is.ui.network.ImageData;
import com.test.axxess.is.ui.network.NetworkModule;

import java.util.List;

/**
 * ImageListAdapter to display the grid images
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    public static final String KEY_IMAGE_URL = "image_url";
    public static final String KEY_IMAGE_ID = "image_id";
    private List<ImageData> imageDataList;
    private Context context;

    public ImageListAdapter(Context context, List<ImageData> imageDataList) {
        this.imageDataList = imageDataList;
        this.context = context;
    }

    public void refreshAdapter(List<ImageData> imageDataList) {
        this.imageDataList.clear();
        this.imageDataList = imageDataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String imgId;
        final String imgUrl;
        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_default_image_thumbnail));
        if (imageDataList.isEmpty() || imageDataList.get(position).getImages() == null || imageDataList.get(position).getImages().isEmpty()) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_default_image_thumbnail));
            imgUrl = "";
            imgId = "";
        } else {
            imgUrl = imageDataList.get(position).isAlbum() ? NetworkModule.getBackgroundImageUrl(imageDataList.get(position).getCover()) : imageDataList.get(position).getLink();
            Picasso.with(context)
                    .load(imgUrl)
                    .resize(200, 200)
                    .placeholder(R.drawable.ic_default_image_thumbnail)
                    .into(holder.imageView);
            imgId = imageDataList.get(position).getId();
        }

        //start full screen image activity
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgUrl.isEmpty()) {
                    Toast.makeText(context, "Image cannot be viewed, thumbnail is not found", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(context, ImageViewerActivity.class);
                intent.putExtra(KEY_IMAGE_URL, imgUrl);
                intent.putExtra(KEY_IMAGE_ID, imgId);
                context.startActivity(intent);
            }
        });

        holder.title.setText(imageDataList.get(position).getTitle());
    }


    @Override
    public int getItemCount() {
        return imageDataList != null ? imageDataList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView title;

        public ViewHolder(View view) {
            super(view);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);

        }
    }
}