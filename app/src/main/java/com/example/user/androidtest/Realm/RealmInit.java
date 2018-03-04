package com.example.user.androidtest.Realm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by User on 27/2/2018.
 */

//used to initialize realm database in the application. Only once during the lifetime of the application.
public class RealmInit extends Application{
        @Override
        public void onCreate() {
            super.onCreate();
            initRealm();

        }

    protected void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

}
