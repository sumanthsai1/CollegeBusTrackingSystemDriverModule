package com.example.test_covid.Model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AboutApp_dialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("About This App");
        builder.setMessage("The main goal of this app is to locate the position of the infected person in realtime by using the concept of geofencing and by self-assessment check whether covid virus is predicted or not and spot your zone to other users");
        return builder.create();
    }
}
