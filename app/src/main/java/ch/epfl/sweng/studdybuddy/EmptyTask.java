package ch.epfl.sweng.studdybuddy;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

class EmptyTask extends Task<Void> {
    @Override
    public boolean isComplete() {
        return true;
    }

    @Override
    public boolean isSuccessful() {
        return true;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Nullable
    @Override
    public Void getResult() {
        return null;
    }

    @Nullable
    @Override
    public <X extends Throwable> Void getResult(@NonNull Class<X> aClass) throws X {
        return null;
    }

    @Nullable
    @Override
    public Exception getException() {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> addOnSuccessListener(@NonNull OnSuccessListener<? super Void> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super Void> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super Void> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @Override
    public Task<Void> addOnCompleteListener(@NonNull Activity var1, @NonNull OnCompleteListener<Void> var2) {
        var2.onComplete(this);
        return this;
    }
}
