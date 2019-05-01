package com.filmoteca.app.config;

import android.content.Context;

import com.filmoteca.app.constantes.Constantes;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.IOException;

public class FireBaseConfig {

    private Context context;

    public FireBaseConfig(Context context) {
        this.context = context;
    }

    public FirebaseApp init() throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                                                     .setApiKey(Constantes.API_KEY)
                                                     .setDatabaseUrl(Constantes.URL_FIREBASE)
                                                     .build();

        return (FirebaseApp) FirebaseApp.initializeApp(getContext(), options);
    }

    private void setContext(Context context) {
        this.context = context;
    }
    private Context getContext() {
        return context;
    }

}
