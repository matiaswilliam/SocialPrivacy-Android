package com.williammatias.socialunmask.Network;

import com.facebook.AccessToken;

/**
 * Created by William Matias <matias.b.william@gmail.com> on 10/10/2017.
 */

public class FacebookSingleton {
    private static final FacebookSingleton ourInstance = new FacebookSingleton();

    public static synchronized FacebookSingleton getInstance() {
        return ourInstance;
    }
    private AccessToken accessToken;

    private FacebookSingleton() {
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

}
