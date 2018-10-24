package ch.epfl.sweng.studdybuddy;

import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.junit.Test;

import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.concurrent.Executor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmptyTaskTest {

    @Mock
    OnSuccessListener<? super Void> onSuccessListener;

    @Mock
    Activity activity;

    @Mock
    Executor executor;

    @Mock
    OnCompleteListener<Void> var2;

    @Mock
    OnFailureListener onFailureListener;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void properties(){
        EmptyTask task = new EmptyTask();
        assertThat(task.isComplete(), is(true));
        assertThat(task.isCanceled(), is(false));
        assertThat(task.isSuccessful(), is(true));
        assertThat(task.getResult(), is((Void) null));
        assertThat(task.getException(), is((Exception) null));
        assertThat(task.addOnSuccessListener(onSuccessListener), is((Task<Void>) null));
        assertThat(task.addOnSuccessListener(executor, onSuccessListener), is((Task<Void>) null));
        assertThat(task.addOnSuccessListener(activity, onSuccessListener), is((Task<Void>) null));
        assertThat(task.addOnFailureListener(onFailureListener), is((Task<Void>) null));
        assertThat(task.addOnFailureListener(executor, onFailureListener), is((Task<Void>) null));
        assertThat(task.addOnFailureListener(activity, onFailureListener), is((Task<Void>) null));
        assertThat(task.addOnCompleteListener(activity, var2), is(task));
    }
}
