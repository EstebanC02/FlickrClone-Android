package com.estebanmoncaleano.flickrclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.estebanmoncaleano.flickrclone.utilties.FontHelper;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

public class LoginActivity extends AppCompatActivity {

    public static final int APP_REQUEST_CODE = 1;

    private View loginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AccessToken accessToken = AccountKit.getCurrentAccessToken();
        if (accessToken != null)
            launchAccountActivity();

        loginFormView = findViewById(R.id.rl_login_view);
        FontHelper.setCustomTypeface(loginFormView);
    }

    private void onLogin(final LoginType loginType) {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        loginType,
                        AccountKitActivity.ResponseType.TOKEN
                );
        final AccountKitConfiguration configuration = configurationBuilder.build();

        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configuration);
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (loginResult.getError() != null) {
                String errorMessage = loginResult.getError().getErrorType().getMessage();
                Snackbar.make(loginFormView,
                        errorMessage,
                        Snackbar.LENGTH_LONG).show();
            } else if (loginResult.getAccessToken() != null) {
                launchAccountActivity();
            }
        }
    }

    public void onPhoneLogin(View view) {
        onLogin(LoginType.PHONE);
    }

    public void onEmailLogin(View view) {
        onLogin(LoginType.EMAIL);
    }

    public void onLaterLogin(View view) {
        launchAccountActivity();
    }

    private void launchAccountActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
