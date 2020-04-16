package dae.mob123.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import dae.mob123.R;

public class RequestMuralDialog extends AppCompatDialogFragment {

    private final String[] REQUEST_MURAL_EMAIL = {"stripparcours@brucity.be"};

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater dialogInflater = getActivity().getLayoutInflater();
        View dialogView = dialogInflater.inflate(R.layout.dialog_mural_request, null);
        dialogBuilder.setView(dialogView)
                .setTitle(R.string.str_request_mural_dialog_title)
                .setPositiveButton(R.string.str_request_mural_dialog_posbtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendMail();
                        //TODO: Intent to send email
                    }
                })
                .setNegativeButton(R.string.str_request_mural_dialog_negbtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        return dialogBuilder.create();

    }

    private void sendMail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, REQUEST_MURAL_EMAIL);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.str_request_mural_email_subject));
        if (emailIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.str_request_mural_email_client)));
        }
    }
}
