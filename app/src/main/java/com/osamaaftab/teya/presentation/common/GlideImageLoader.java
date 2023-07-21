package com.osamaaftab.teya.presentation.common;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GlideImageLoader {

   private final ImageView mImageView;
   private final CircularProgressBar mProgressBar;

   public GlideImageLoader(ImageView imageView, CircularProgressBar progressBar) {
      mImageView = imageView;
      mProgressBar = progressBar;
   }

   public void load(final String url, RequestOptions options) {
      if (url == null || options == null) return;

      onConnecting();

      //set Listener & start
      ProgressAppGlideModule.expect(url, new ProgressAppGlideModule.UIonProgressListener() {
         @Override
         public void onProgress(long bytesRead, long expectedLength) {
            if (mProgressBar != null) {
               mProgressBar.setProgress((int) (100 * bytesRead / expectedLength));
            }
         }

         @Override
         public float getGranualityPercentage() {
            return 1.0f;
         }
      });

      GlideApp.with(mImageView.getContext())
              .load(url)
              .transition(withCrossFade())
              .apply(options.skipMemoryCache(true))
              .listener(new RequestListener<Drawable>() {
                 @Override
                 public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    ProgressAppGlideModule.forget(url);
                    onFinished();
                    return false;
                 }

                 @Override
                 public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    ProgressAppGlideModule.forget(url);
                    onFinished();
                    return false;
                 }
              }).into(mImageView);
   }


   private void onConnecting() {
      if (mProgressBar != null) mProgressBar.setVisibility(View.VISIBLE);
   }

   private void onFinished() {
      if (mProgressBar != null && mImageView != null) {
         mProgressBar.setVisibility(View.GONE);
         mImageView.setVisibility(View.VISIBLE);
      }
   }
}
