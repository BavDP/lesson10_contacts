package com.dnipro.beldii.lesson10.helpers;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncPhotoLoader {
    private Drawable photoDrawable;
    public void load(String url, ImageView imageView) {
        // загрузка фотографий по ссылке из json-файла
        try {
            URL imageUrl = new URL(url);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // параллельный поток
                    try {
                        InputStream photoStream = (InputStream) imageUrl.getContent();
                        photoDrawable = Drawable.createFromStream(photoStream, "photo");
                    } catch (IOException e) {
                        e.getStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // UI-поток
                            imageView.setImageDrawable(photoDrawable);
                        }
                    });
                }
            });
        } catch (MalformedURLException e) {
            e.getStackTrace();
        }
    }
}
