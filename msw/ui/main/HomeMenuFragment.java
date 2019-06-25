package com.cowboy.msw.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.cowboy.msw.QRScanActivity;
import com.cowboy.msw.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeMenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeMenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private int tabMenuIds[] = {R.id.menuItem1, R.id.menuItem2, R.id.menuItem3, R.id.menuItem4, R.id.menuItem5};
    private OnFragmentInteractionListener mListener;

    public HomeMenuFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeMenuFragment newInstance(String param1, String param2) {
        HomeMenuFragment fragment = new HomeMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_home, container, false);
        setupMenus(root);
        return root;
    }

    protected void setupMenus(View view) {

        for (int i=0; i<tabMenuIds.length; i++) {
            final RippleView rippleView = (RippleView) view.findViewById(tabMenuIds[i]);
            rippleView.setTag(i);
            rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView view) {
                    int tag = (Integer)view.getTag();
                    switch (tag){
                    case 0:
                        homeMenuFragment = null;
                        getActivity().getSupportFragmentManager()
                                .beginTransaction().replace(R.id.fragmentHome, new SubMenuFragment().setParentFragment(HomeMenuFragment.this).setSubMenuIndex(0)).commit();
                        break;
                    case 1:
                        homeMenuFragment = null;
                        getActivity().getSupportFragmentManager()
                                .beginTransaction().replace(R.id.fragmentHome, new SubMenuFragment().setParentFragment(HomeMenuFragment.this).setSubMenuIndex(1)).commit();
                        break;
                    }
                }
            });
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static HomeMenuFragment homeMenuFragment = null;
}
