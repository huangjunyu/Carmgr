package com.yiwucheguanjia.carmgr.account;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.yiwucheguanjia.carmgr.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements View.OnClickListener,LoaderCallbacks<Cursor> {

    private String login_account;
    private String login_password;

    private Button email_sign_in_button;
    private EditText login_account_edit;
    private EditText login_password_edit;
    private OkHttpClient client;
    private static final MediaType JSON = MediaType.parse("application/json;charset:utf-8");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }
    private void initView(){
        login_account_edit = (EditText)findViewById(R.id.login_account_edit);
        login_password_edit = (EditText)findViewById(R.id.login_password_edit);
        client = new OkHttpClient();
    }
    /*OkHttpClient client = new OkHttpClient();
    String post(String url, String json) throws IOException {

        RequestBody formBody = new FormEncodingBuilder()
                .add("platform", "android")
                .add("name", "bug")
                .add("subject", "XXXXXXXXXXXXXXX")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                login_account = login_account_edit.getText().toString();
                login_password = login_password_edit.getText().toString();
        }
    }

    /**
     * Use an AsyncTask to fetch the user's email addresses on a background thread, and update
     * the email text field with results on the main UI thread.
     */
    class SetupEmailAutoCompleteTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... params) {
//            final Request request = new Request.Builder().post()
            return null;
        }
    }

}

