package kr.ac.kumoh.s20131582.memo_java;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class Migration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema rsc = realm.getSchema();

        if(oldVersion ==0)
        {
            RealmObjectSchema mMemoSchema =  rsc.get("Memo");
            mMemoSchema.addField("imgurl",String.class,null)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("imgurl",null);
                        }
                    });
            oldVersion++;

        }

    }
}
