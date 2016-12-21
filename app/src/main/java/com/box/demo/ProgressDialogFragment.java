package com.box.demo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProgressDialogFragment extends DialogFragment {
    private static final String TAG = "PROGRESS_DIALOG";

    public ProgressDialogFragment() {
    }

    public static ProgressDialogFragment newInstance() {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        setCancelable(false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        ProgressDialog dialog = new ProgressDialog(getActivity(), getTheme());
        dialog.setMessage("Loading...");
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return dialog;
    }

    public void show(FragmentManager fragmentManager){
        show(fragmentManager,TAG);
    }

    public static void hide(FragmentManager fragmentManager){
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if(fragment!=null&&fragment instanceof DialogFragment){
            ((DialogFragment)fragment).dismiss();
        }
    }
}
