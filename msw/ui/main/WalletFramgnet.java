package com.cowboy.msw.ui.main;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cowboy.msw.MainActivity;
import com.cowboy.msw.R;
import com.cowboy.msw.ui.FinalActivity;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFramgnet extends Fragment implements View.OnClickListener {

    private CircleGridAdapter adapter = new CircleGridAdapter();
    private String phoneNumber = "";
    private int itemCount = 0;
    private int walletIndex = 0;
    private int titleList[] = {R.string.menu_subitem_1, R.string.menu_subitem_2, R.string.menu_subitem_3, R.string.menu_subitem_4};
    private SubMenuFragment parentFragment = null;
    private ImageView maskView = null;

    public WalletFramgnet() {
        // Required empty public constructor
    }

    public WalletFramgnet setIndex(int index) {
        walletIndex = index;
        return this;
    }

    public WalletFramgnet setParentFragment(SubMenuFragment fragment) {
        parentFragment = fragment;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.currentFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);
        root.findViewById(R.id.btnGoBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome, parentFragment).commit();
                getActivity().setTitle(R.string.app_name);
                MainActivity.currentFragment = null;
            }
        });
        if (walletIndex < titleList.length) {
            getActivity().setTitle(titleList[walletIndex]);
        }

        ImageView topView = (ImageView)root.findViewById(R.id.imgWallet);
        ImageView bottomView = (ImageView)root.findViewById(R.id.imgWalletBottom);
        if (walletIndex == 0 || walletIndex == 1) {
            topView.setImageResource(R.drawable.ic_bar_rafaelli);
            bottomView.setImageResource(R.drawable.ic_bar_rafaelli_2);
        } else if (walletIndex == 2) {
            topView.setImageResource(R.drawable.ic_palo_imbis);
            bottomView.setImageResource(R.drawable.ic_city_imbis_2);
        } else if (walletIndex == 3) {
            topView.setImageResource(R.drawable.ic_city_imbis);
            bottomView.setImageResource(R.drawable.ic_city_imbis_2);
        }

        GridView gridView = (GridView)root.findViewById(R.id.gridItems);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(5);
        TextView numberView = (TextView)root.findViewById(R.id.txtNumber);
        numberView.setText("" + itemCount);

        root.findViewById(R.id.btnPhone).setOnClickListener(this);
        root.findViewById(R.id.btnLocation).setOnClickListener(this);
        root.findViewById(R.id.btnWeb).setOnClickListener(this);

        phoneNumber = "0821/000001";
        TextView txtPhoneNumber = root.findViewById(R.id.txtPhoneNumber);
        txtPhoneNumber.setText(phoneNumber);
        maskView = (ImageView)(getActivity().findViewById(R.id.imgMask));

        ImageButton btnChecked = root.findViewById(R.id.bntCheck);
        btnChecked.setEnabled(false);
        return root;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPhone) {

            View view = (View) getLayoutInflater().inflate(R.layout.alert_phonenumber, null);
            TextView txtPhNumberView = view.findViewById(R.id.txtPhoneNumber);
            txtPhNumberView.setText(phoneNumber);
            Dialog dialog = new AlertDialog.Builder(getActivity())
                    .setView(view)
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (maskView != null) {
                                maskView.setVisibility(View.GONE);
                            }
                        }
                    })
                    .show();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setLayout((int)getResources().getDimension(R.dimen.dp_300), (int)getResources().getDimension(R.dimen.dp_160));
            if (maskView != null) {
                maskView.setVisibility(View.VISIBLE);
            }
        } else if (v.getId() == R.id.btnLocation) {

        } else if (v.getId() == R.id.btnWeb) {

        }
    }

    private void showCouponDialog() {
        View view = (View) getLayoutInflater().inflate(R.layout.alert_coupon, null);
        View btnConfirm = view.findViewById(R.id.btnConfirm);
        View btnStop = view.findViewById(R.id.btnStop);
        final Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (maskView != null) {
                            maskView.setVisibility(View.GONE);
                        }
                    }
                })
                .show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout((int)getResources().getDimension(R.dimen.dp_300), (int)getResources().getDimension(R.dimen.dp_160));
        if (maskView != null) {
            maskView.setVisibility(View.VISIBLE);
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnConfirm) {
                    if (getActivity() != null) {
                        Intent i = new Intent(getActivity(), FinalActivity.class);
                        startActivityForResult(i, MainActivity.FINAL_REQUEST);
                    }
                } else if (v.getId() == R.id.btnStop) {

                }
                dialog.dismiss();
                if (maskView != null) {
                    maskView.setVisibility(View.GONE);
                }
            }
        };
        btnConfirm.setOnClickListener(listener);
        btnStop.setOnClickListener(listener);
    }

    public int getItemCount() {
        return itemCount;
    }

    public void clearItems() {
        itemCount = 0;
        adapter.notifyDataSetChanged();
        ImageButton btnChecked = (ImageButton)getView().findViewById(R.id.bntCheck);
        btnChecked.setColorFilter(getResources().getColor(android.R.color.white));
        btnChecked.setEnabled(false);
        TextView numberView = getView().findViewById(R.id.txtNumber);
        numberView.setText("" + itemCount);
    }

    public void addItem() {
        if (itemCount < 10) {
            itemCount = itemCount + 1;
            adapter.notifyDataSetChanged();
            TextView numberView = getView().findViewById(R.id.txtNumber);
            numberView.setText("" + itemCount);
        }
        if (itemCount == 10) {
            ImageButton btnChecked = (ImageButton)getView().findViewById(R.id.bntCheck);
            btnChecked.setColorFilter(getResources().getColor(R.color.mainForeColor));
            btnChecked.setEnabled(true);
            btnChecked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCouponDialog();
                }
            });
        }
    }

    class CircleGridAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_circle, parent, false);
            } else {
                view = convertView;
            }
            ImageView imgView = (ImageView) view.findViewById(R.id.imgCircle);
            if (position < itemCount) {
                imgView.setImageResource(R.drawable.ic_circle_checked);
            } else {
                imgView.setImageResource(R.drawable.ic_circle);
            }
            return view;
        }
    }
}
