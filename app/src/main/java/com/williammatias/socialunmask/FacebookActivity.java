package com.williammatias.socialunmask;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.williammatias.socialunmask.Network.FacebookSingleton;
import com.williammatias.socialunmask.models.FacebookUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FacebookActivity extends AppCompatActivity implements GraphRequest.GraphJSONObjectCallback {

    @BindView(R.id.fb_cover)
    ImageView facebookCover;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static String TAG = "FacebookActivity";
    private FacebookUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        ButterKnife.bind(this);

        getUser();
        setUpFloatingButton();
    }


    private void getUser() {
        AccessToken accessToken = FacebookSingleton.getInstance().getAccessToken();

        GraphRequest request = GraphRequest.newMeRequest(accessToken, this);
        Bundle parameters = new Bundle();
        parameters.putString("fields", getResources().getString(R.string.facebook_fields));
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void setFacebookCover() {
        Glide.with(this)
                .load(this.user.getCover().getSource())
                .into(facebookCover);
    }

    // GraphJSONObjectCallback onCompleted

    @Override
    public void onCompleted(org.json.JSONObject object, GraphResponse response) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(object.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        user = gson.fromJson(jsonObject.toString(), FacebookUser.class);

        setFacebookCover();
        toolbar.setTitle(user.getName());
        setUpToolbar();

        Log.d(TAG, user.getName());
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
