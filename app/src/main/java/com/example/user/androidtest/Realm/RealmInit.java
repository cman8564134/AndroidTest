package com.example.user.androidtest.Realm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by User on 27/2/2018.
 */

public class RealmInit extends Application{
        @Override
        public void onCreate() {
            super.onCreate();
            Realm.init(this);
            RealmConfiguration config = new RealmConfiguration
                    .Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(config);
        }

}
