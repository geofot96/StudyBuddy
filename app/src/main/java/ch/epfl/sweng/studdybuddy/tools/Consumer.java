package ch.epfl.sweng.studdybuddy.tools;

import android.support.annotation.Nullable;

public interface Consumer<T> {
    void accept(@Nullable T t);

    static <T> Consumer<T> sequenced(Consumer<T> a1, Consumer<T> a2) {
        return new Consumer<T>() {
            @Override
            public void accept(T t) {
                a1.accept(t);
                a2.accept(t);
            }
        };
    }

    static <T> Consumer<T> doNothing() {
        return new Consumer<T>() {
            @Override
            public void accept(T t) {

            }
        };
    }


}
