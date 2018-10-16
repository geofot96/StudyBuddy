package ch.epfl.sweng.studdybuddy;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.List;

final public class AdapterConsumer {

    private AdapterConsumer() {}
    public static <T> Consumer<List<T>> adapterConsumer(Class<T> type, final List<T> set, final RecyclerView.Adapter adapter) {
        return new Consumer<List<T>>() {
            @Override
            public void accept(List<T> list) {
                set.removeAll(set);
                set.addAll(list);
                Log.i("MSG", "MSG");
                adapter.notifyDataSetChanged();
            }
        };
    }
    public static <T> Consumer<List<T>> adapterConsumer(Class<T> type, final List<T> set, final ArrayAdapter<T> adapter) {
        return new Consumer<List<T>>() {
            @Override
            public void accept(List<T> list) {
                set.removeAll(set);
                set.addAll(list);
                Log.i("MSG", "MSG");

                adapter.notifyDataSetChanged();
            }
        };
    }
}
