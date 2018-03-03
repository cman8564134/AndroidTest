package com.example.user.androidtest.Modal;

import io.realm.Realm;

/**
 * Created by User on 3/3/2018.
 */

public class Database {

    private static Realm realm;

    private static Database database;
    protected Database(){}

    public static Database getInstance()
    {
        if(database==null) {
            database = new Database();
            realm=Realm.getDefaultInstance();
        }
        return database;
    }

    public boolean registerOrUpdateAccount(final Account account)
    {

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(account);
                }
            });

            return true;
        }

        catch (Exception e)
        {
            return false;
        }
    }

    public Account getAccount(Account account)
    {
        Account specificPerson = realm.where(Account.class)
                .equalTo("ID", account.getID())
                .findFirst();

        return specificPerson;
    }


}
