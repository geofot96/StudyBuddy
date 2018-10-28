package ch.epfl.sweng.studdybuddy;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import java.util.List;

final public class AdapterConsumer {

    private AdapterConsumer() {}
    public static <T> Consumer<List<T>> adapterConsumer(Class<T> type, final List<T> set, final AdapterAdapter adapter) {
        return new Consumer<List<T>>() {
            @Override
            public void accept(List<T> list) {
                if(list != null) { // set == null is a programmer error so we let excpetion be thrown
                    set.removeAll(set);
                    set.addAll(list);
                    adapter.update();
                }
            }
        };
    }


}
