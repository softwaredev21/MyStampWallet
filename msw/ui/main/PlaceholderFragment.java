package com.cowboy.msw.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.cowboy.msw.MainActivity;
import com.cowboy.msw.QRScanActivity;
import com.cowboy.msw.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private int tabMenuIds[] = {R.id.menuItem1, R.id.menuItem2, R.id.menuItem3, R.id.menuItem4, R.id.menuItem5};
    private static final int[] fragmentResIds= {R.layout.fragment_main, R.layout.fragment_like, R.layout.fragment_web};
    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (pageViewModel.getIndex() < fragmentResIds.length) {
            View root = inflater.inflate(fragmentResIds[pageViewModel.getIndex()-1], container, false);
            if (pageViewModel.getIndex() == 1){
                setupSubFragments(root);
            }
            return root;
        } else {
            View root = inflater.inflate(R.layout.fragment_web, container, false);

            return root;
        }
    }

    protected void setupMenus(View view) {

        for (int i=0; i<tabMenuIds.length; i++) {
            final RippleView rippleView = (RippleView) view.findViewById(tabMenuIds[i]);
            rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView rippleView) {
                    Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    protected void setupSubFragments(View root) {
        View scanQr = root.findViewById(R.id.btnScanQR);
        scanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (MainActivity.currentFragment != null && MainActivity.currentFragment.getItemCount() < 10) {
                    Intent i = new Intent(getActivity(), QRScanActivity.class);
                    startActivityForResult(i, MainActivity.SCAN_REQUEST);
                //}
            }
        });
        HomeMenuFragment.homeMenuFragment = new HomeMenuFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentHome, HomeMenuFragment.homeMenuFragment).commit();
    }

    public boolean backToHome(AppCompatActivity activity) {
        if (HomeMenuFragment.homeMenuFragment == null) {
            HomeMenuFragment.homeMenuFragment = new HomeMenuFragment();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome, HomeMenuFragment.homeMenuFragment).commit();
            activity.setTitle(R.string.app_name);
            MainActivity.currentFragment = null;
            return true;
        } else {
            return false;
        }
    }

}