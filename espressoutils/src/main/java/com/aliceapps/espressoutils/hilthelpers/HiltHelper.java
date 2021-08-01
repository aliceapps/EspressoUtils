package com.aliceapps.espressoutils.hilthelpers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.test.core.app.ActivityScenario;
import androidx.work.Configuration;
import androidx.work.testing.SynchronousExecutor;
import androidx.work.testing.WorkManagerTestInitHelper;

import com.aliceapps.espressoutils.R;

import java.util.Objects;

public class HiltHelper {
    @NonNull
    public static <T extends Fragment, A extends AppCompatActivity> ActivityScenario<A>
    launchFragmentInHiltContainer(Class<A> activityClass, Class<T> typeParameterClass, Bundle fragmentArgs, int themeResId, FragmentFactory factory) {
        ActivityScenario<A> scenario = ActivityScenario.launch(activityClass);

        scenario.onActivity(activity -> {
            if (factory != null)
                activity.getSupportFragmentManager().setFragmentFactory(factory);
            Fragment fragment = activity.getSupportFragmentManager().getFragmentFactory()
                    .instantiate(Objects.requireNonNull(typeParameterClass.getClassLoader()), typeParameterClass.getName());
            if (fragmentArgs != null)
                fragment.setArguments(fragmentArgs);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, fragment, typeParameterClass.getSimpleName())
                    .commitNow();
        });
        return scenario;
    }

    public static <T extends Fragment, A extends AppCompatActivity> ActivityScenario<A> launchDialogInHiltContainer
            (Class<A> activityClass, Class<T> typeParameterClass, Bundle fragmentArgs, int themeResId, FragmentFactory factory) {

        ActivityScenario<A> scenario = ActivityScenario.launch(activityClass);

        scenario.onActivity(activity -> {
            if (factory != null)
                activity.getSupportFragmentManager().setFragmentFactory(factory);
            Fragment fragment = activity.getSupportFragmentManager().getFragmentFactory()
                    .instantiate(Objects.requireNonNull(typeParameterClass.getClassLoader()), typeParameterClass.getName());
            if (fragmentArgs != null)
                fragment.setArguments(fragmentArgs);

            if (fragment instanceof DialogFragment) {
                DialogFragment dialog = (DialogFragment) fragment;
                dialog.show(activity.getSupportFragmentManager(),typeParameterClass.getSimpleName());
            }
        });
        return scenario;
    }

    public static void startWorkers(Context context, HiltWorkerFactory workerFactory) {
        Configuration config = new Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .setWorkerFactory(workerFactory)
                .setExecutor(new SynchronousExecutor())
                .build();
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config);
    }
}
