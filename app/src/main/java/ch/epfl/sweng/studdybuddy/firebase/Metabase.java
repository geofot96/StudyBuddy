package ch.epfl.sweng.studdybuddy.firebase;

import java.util.LinkedList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.util.AdapterAdapter;

abstract public class Metabase {
    protected ReferenceWrapper db;
    protected final List<AdapterAdapter> ads; // eventually use option

    public Metabase(ReferenceWrapper db, AdapterAdapter ad) {
        this.db = db;
        this.ads = new LinkedList<>();
        if(ad != null) {this.ads.add(ad);}
    }



    protected void notif() {
        if(ads != null) {
            for(AdapterAdapter ad : ads)
                ad.update();
        }
    }
}
