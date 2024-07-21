package com.java.tanjingyu.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

// 显示图片或视频，视频优先级更高
public class ImageVideoView extends ConstraintLayout {
    private final ImageView imageView;
    private final PlayerView playerView;

    public ImageVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        imageView = new ImageView(context);
        playerView = new PlayerView(context);
        imageView.setLayoutParams(new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        imageView.setAdjustViewBounds(true);
        playerView.setLayoutParams(new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                1
        ));
    }

    // 输入图片和视频 URL
    @OptIn(markerClass = UnstableApi.class)
    public void put(String image, String video) {
        removeAllViewsInLayout();
        if(video.isEmpty()) {
            if(!image.isEmpty()) {
                Glide.with(this)
                        .load(image)
                        .override(Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<Drawable>() {

                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                int width = resource.getIntrinsicWidth();
                                int height = resource.getIntrinsicHeight();

                                // 过滤过小的图片
                                if(width > 70 && height > 70) addView(imageView);
                                return false;
                            }
                        })
                        .into(imageView);
            }
        } else {
            ExoPlayer player = new ExoPlayer.Builder(getContext()).build();
            playerView.setPlayer(player);
            player.setMediaItem(MediaItem.fromUri(video));
            player.prepare();

            // 设置播放器大小
            playerView.setAspectRatioListener((targetAspectRatio, naturalAspectRatio, aspectRatioMismatch) ->
                    playerView.setLayoutParams(new Constraints.LayoutParams(
                        playerView.getWidth(),
                        (int) (playerView.getWidth() / targetAspectRatio)))
            );
            addView(playerView);
        }
        requestLayout();
    }
}
