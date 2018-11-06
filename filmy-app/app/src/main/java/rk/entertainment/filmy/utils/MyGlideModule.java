package rk.entertainment.filmy.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;


@GlideModule
public final class MyGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, GlideBuilder builder) {
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_RGB_565));
        builder.setLogLevel(Log.ERROR);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }
}
