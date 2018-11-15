package ch.epfl.sweng.studdybuddy.tools;

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

    public static android.support.v7.widget.SearchView.OnQueryTextListener searchListener(GroupsRecyclerAdapter mAdapter) {
        return new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query); //FILTER AS YOU TYPE
                return false;
            }
        };
    }


}
