/*
 * Copyright (c) 2016 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.douya.content;

import android.accounts.Account;
import android.content.Context;

import com.android.volley.Response;

import me.zhanghai.android.douya.account.util.AccountUtils;
import me.zhanghai.android.douya.network.Volley;
import me.zhanghai.android.douya.network.api.ApiRequest;

public abstract class ResourceWriter<W extends ResourceWriter, T>
        implements Response.Listener<T>, Response.ErrorListener {

    private ResourceWriterManager<W> mManager;

    private Account mAccount;
    private ApiRequest<T> mRequest;

    public ResourceWriter(ResourceWriterManager<W> manager) {
        mManager = manager;
    }

    public void onStart() {

        mAccount = AccountUtils.getActiveAccount(getContext());

        mRequest = onCreateRequest();
        mRequest.setListener(this);
        mRequest.setErrorListener(this);
        mRequest.setAccount(mAccount);
        Volley.getInstance().addToRequestQueue(mRequest);
    }

    protected abstract ApiRequest<T> onCreateRequest();

    public void onDestroy() {
        mRequest.cancel();
        mRequest.setListener(null);
        mRequest.setErrorListener(null);
    }

    public Account getAccount() {
        return mAccount;
    }

    protected Context getContext() {
        return mManager.getContext();
    }

    protected void stopSelf() {
        //noinspection unchecked
        mManager.stop((W) this);
    }
}
