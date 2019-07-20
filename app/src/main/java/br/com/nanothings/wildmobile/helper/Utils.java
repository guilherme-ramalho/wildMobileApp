package br.com.nanothings.wildmobile.helper;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {
    private ProgressDialog progressDialog;

    public void loadingDialog(Context context, boolean showDialog) {
        if(showDialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("teste");
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

}
