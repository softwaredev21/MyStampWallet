package com.cowboy.msw.ui.main;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.cowboy.msw.MainActivity;
import com.cowboy.msw.R;

public class SubMenuFragment extends Fragment {

    private int subMenuIndex = 0;
    private HomeMenuFragment homeFragment = null;

    public SubMenuFragment() {
        // Required empty public constructor
    }

    public SubMenuFragment setParentFragment(HomeMenuFragment fragment) {
        homeFragment = fragment;
        return this;
    }

    public SubMenuFragment setSubMenuIndex(int index) {
        subMenuIndex = index;
        return this;
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
        View root = inflater.inflate(R.layout.fragment_submenu, container, false);
        root.findViewById(R.id.btnGoBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setTitle(R.string.app_name);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome, homeFragment).commit();
                HomeMenuFragment.homeMenuFragment = homeFragment;
                MainActivity.currentFragment = null;
            }
        });
        if (subMenuIndex == 1) {
            setupSubMenu(root);
        }
        setupAction(root);
        return root;
    }

    public void setupAction(View root) {
        final RippleView topItem = (RippleView) root.findViewById(R.id.subMenuItem1);
        final RippleView bottomItem = (RippleView) root.findViewById(R.id.subMenuItem2);
        topItem.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentHome, new WalletFramgnet().setIndex(subMenuIndex*2).setParentFragment(SubMenuFragment.this)).commit();
            }
        });
        bottomItem.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentHome, new WalletFramgnet().setIndex(subMenuIndex*2+1).setParentFragment(SubMenuFragment.this)).commit();
            }
        });
    }

    public void setupSubMenu(View root) {
        TextView title = (TextView)root.findViewById(R.id.txtSubMenuTitle);
        title.setText(R.string.title_submenu2);
        ImageView imgBack1 = (ImageView)root.findViewById(R.id.imgSubItemBack1);
        TextView txt1 = (TextView)root.findViewById(R.id.txtSubItem1);
        ImageView img1 = (ImageView)root.findViewById(R.id.imgSubItem1);
        imgBack1.setImageResource(R.drawable.ic_item_back_3);
        img1.setImageResource(R.drawable.ic_item3);
        txt1.setText(R.string.menu_subitem_3);

        ImageView imgBack2 = (ImageView)root.findViewById(R.id.imgSubItemBack2);
        TextView txt2 = (TextView)root.findViewById(R.id.txtSubItem2);
        ImageView img2 = (ImageView)root.findViewById(R.id.imgSubItem2);
        imgBack2.setImageResource(R.drawable.ic_item_back_4);
        img2.setImageResource(R.drawable.ic_item4);
        txt2.setText(R.string.menu_subitem_4);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
}
