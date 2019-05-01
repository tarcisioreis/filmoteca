package com.filmoteca.app.service;

import android.content.Context;

import com.filmoteca.app.config.FireBaseConfig;
import com.google.firebase.FirebaseApp;

import java.io.IOException;

public class FireBaseService {

    private Context context;

    public FireBaseService(Context context) {
        this.context = context;
    }

    public FirebaseApp init() throws IOException {
        return new FireBaseConfig(getContext()).init();
    }

    private void setContext(Context context) {
        this.context = context;
    }
    private Context getContext() {
        return context;
    }

}
