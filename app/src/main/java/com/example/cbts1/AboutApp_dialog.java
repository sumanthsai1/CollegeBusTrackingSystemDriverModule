package com.example.cbts1;

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
        builder.setMessage("The main goal of this app is to locate the position of the College Bus in realtime by using the concept of GPS and help the Passengers to make their day happy without missing the bus");
        return builder.create();
    }
}
