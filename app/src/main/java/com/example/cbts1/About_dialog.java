package com.example.test_covid.Model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class About_dialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Contact Us");
        builder.setMessage("We highly recommend you to help us improve.Give us your Feedback to this Mail Address\nS.SumanthSai\nE-mail=s.sumanthsai1998@gmail.com");
        return builder.create();
    }
}
