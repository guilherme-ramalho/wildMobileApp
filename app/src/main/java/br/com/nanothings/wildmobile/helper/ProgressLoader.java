package br.com.nanothings.wildmobile.helper;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressLoader {
    private Context context;
    private ProgressDialog progressDialog;

    public ProgressLoader(Context context) {
        this.context = context;
    }

    public void setLoader() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carregando...");
    }

    public void showLoader(boolean showDialog) {
        if(showDialog) {
            progressDialog.show();
        } else {
            progressDialog.hide();
        }
    }
}
