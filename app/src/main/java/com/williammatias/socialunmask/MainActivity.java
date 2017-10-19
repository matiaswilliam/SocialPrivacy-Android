package com.williammatias.socialunmask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.williammatias.socialunmask.Network.FacebookSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

    @BindView(R.id.facebook_login_button)
    LoginButton mFacebookLoginButton;
    private CallbackManager callbackManager;

    private static String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();
        mFacebookLoginButton.setReadPermissions("email", "public_profile");
        mFacebookLoginButton.registerCallback(callbackManager, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void facebookLogin(View view) {
        if(isLoggedInFacebook()){
            FacebookSingleton.getInstance().setAccessToken(AccessToken.getCurrentAccessToken());
            goToFacebookProfile();
        }else {
            mFacebookLoginButton.performClick();
        }
    }

    private void goToFacebookProfile() {
        // navigate to Facebook Activity
        Intent intent = new Intent(this, FacebookActivity.class);
        startActivity(intent);
    }

    /* START FACEBOOK CALLBACK INTERFACE */
    @Override
    public void onSuccess(LoginResult loginResult) {
        // set the access token to the singleton
        FacebookSingleton.getInstance().setAccessToken(loginResult.getAccessToken());
        goToFacebookProfile();
    }

    @Override
    public void onCancel() {
        // do nothing
    }

    @Override
    public void onError(FacebookException error) {
        //        error
    }
    /* END FACEBOOK CALLBACK INTERFACE */

    public boolean isLoggedInFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }


}

