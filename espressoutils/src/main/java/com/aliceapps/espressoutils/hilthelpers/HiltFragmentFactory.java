package com.aliceapps.espressoutils.hilthelpers;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class HiltFragmentFactory extends FragmentFactory {
    private final NavController navController;

    public HiltFragmentFactory(NavController navController) {
        this.navController = navController;
    }

    protected void setNavController(Fragment fragment) {
        // In addition to returning a new instance of our fragment,
        // get a callback whenever the fragment’s view is created
        // or destroyed so that we can set the NavController
        fragment.getViewLifecycleOwnerLiveData().observeForever(viewLifecycleOwner -> {
            // The fragment’s view has just been created
            if (viewLifecycleOwner != null) {
                Navigation.setViewNavController(fragment.requireView(), navController);
            }
        });
    }
}
