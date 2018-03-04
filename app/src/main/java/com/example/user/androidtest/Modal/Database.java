package com.example.user.androidtest.Modal;

import io.realm.Realm;

/**
 * Created by User on 3/3/2018.
 */

//database class to simulate a database in an application to save/delete/update to Realm database
public class Database {

    public Database(){}
    public boolean registerOrUpdateAccount(final Account account)
    {

        try {

            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
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
        Account specificPerson = Realm.getDefaultInstance().where(Account.class)
                .equalTo("ID", account.getID())
                .findFirst();

        return specificPerson;
    }


}
