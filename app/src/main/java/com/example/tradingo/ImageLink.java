package com.example.tradingo;

import android.net.Uri;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.collection.ArraySet;

import androidx.collection.ArraySet;

public class ImageLink {
    public String url;

    public ImageLink() {

    }

    public ImageLink(String url) {
        this.url = url;
    }

    public ImageLink(Uri uri) {
        this.url = uri.toString();
    }

    public String getUrl() {
        return this.url;
    }
}
