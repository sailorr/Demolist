package me.sailor.demolist.ui.media.retrofit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import me.sailor.demolist.R;
import me.sailor.demolist.ui.widget.preview.PrePhotoActivity;
import me.sailor.demolist.base.BaseRecycleViewAdapter;
import me.sailor.demolist.base.BaseViewHolder;
import me.sailor.demolist.bean.Result;

/**
 * @author sailor
 * @description
 * @time 2018/11/20
 */
public class ReRVAdapter extends BaseRecycleViewAdapter<Result> {
    private Context mContext;

    public ReRVAdapter(Context context, List<Result> datas, int layoutId) {
        super(datas, layoutId);
        this.mContext = context;
    }

    @Override
    protected void bindData(BaseViewHolder viewHolder, final Result data, int position) {
        final ImageView imageView = viewHolder.getView(R.id.item_retrofit_img);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext).asBitmap()
                .load(data.getUrl())
                .thumbnail(0.5f)
                .apply(new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(new SimpleTarget<Bitmap>() {
                    //加载完得到bitmap 先预处理，设置高度
                    @Override
                    public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.getLayoutParams().height = resource.getHeight();
                        imageView.setImageBitmap(resource);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext,PrePhotoActivity.class);
                                intent.putExtra("url",data.getUrl());
                                mContext.startActivity(intent);
                            }
                        });

                    }
                });

    }
}