package com.aliceapps.espressoutils.hilthelpers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.test.core.app.ActivityScenario;

import com.aliceapps.espressoutils.R;

import java.util.Objects;

public class HiltHelper {
    @NonNull
    public static <T extends Fragment> ActivityScenario<HiltEmptyActivity>
    launchFragmentInHiltContainer(Class<T> typeParameterClass, Bundle fragmentArgs, int themeResId, FragmentFactory factory) {
        ActivityScenario<HiltEmptyActivity> scenario = ActivityScenario.launch(HiltEmptyActivity.class);

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

    public static <T extends Fragment> ActivityScenario<HiltEmptyActivity> launchDialogInHiltContainer
            (Class<T> typeParameterClass, Bundle fragmentArgs, int themeResId, FragmentFactory factory) {

        ActivityScenario<HiltEmptyActivity> scenario = ActivityScenario.launch(HiltEmptyActivity.class);

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
}
