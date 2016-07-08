package com.example.isoft.studyskadden.realm;

import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.entities.MyWeather;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by isoft on 15.06.16.
 */
public class MyCityRealmManager extends BaseRealmManager<MyCity>{
    public MyCityRealmManager() {
        super(MyCity.class);
    }

    @Override
    public RealmObject save(Realm realm, MyCity item) {

        if (realm != null){
            // need to drop old weather log if it exist
            MyCity oldInstance = super.getById(realm, item.getId());
            if (oldInstance !=null) {
                RealmList<MyWeather> oldLog = (RealmList<MyWeather>) oldInstance.getWeatherLog();
                if (oldLog != null){
                    realm.beginTransaction();
                    oldLog.deleteAllFromRealm();
                    realm.commitTransaction();
                }
            }
        }

        return super.save(realm, item);
    }
}
