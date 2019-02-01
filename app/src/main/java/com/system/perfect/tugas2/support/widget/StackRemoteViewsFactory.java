package com.system.perfect.tugas2.support.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.system.perfect.tugas2.BuildConfig;
import com.system.perfect.tugas2.R;
import com.system.perfect.tugas2.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.system.perfect.tugas2.provider.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private int widgetId;
    private Cursor cursor;
    private List<Bitmap> widgetItem = new ArrayList<>();
    private List<Movie> favoriteList = new ArrayList<>();

    public StackRemoteViewsFactory(Context mContext, Intent intent) {
        context = mContext;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long idToken = Binder.clearCallingIdentity();
        getContentResolver(context);
        Binder.restoreCallingIdentity(idToken);

        for (Movie movie: favoriteList){
            try {
                Bitmap i = Glide.with(context)
                        .asBitmap()
                        .load(BuildConfig.TMDB_IMAGE_MEDIUM + movie.getPosterPath())
                        .apply(new RequestOptions().fitCenter())
                        .submit()
                        .get();
                widgetItem.add(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private void getFavoriteData(Context mContext){
        final long idToken = Binder.clearCallingIdentity();
        getContentResolver(mContext);
        Binder.restoreCallingIdentity(idToken);
    }

    private void getContentResolver(Context mContext){
        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor != null){
            while (cursor.moveToNext()){
                favoriteList.add(new Movie(cursor));
            }
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return widgetItem.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.favorite_widget_item);
        rv.setImageViewBitmap(R.id.image_widget,widgetItem.get(position));

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.image_widget, fillInIntent);
        rv.setTextViewText(R.id.widget_favorite_title, favoriteList.get(position).getTitle());
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
