/*
 * Copyright (c) 2016 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.douya.content;

import android.accounts.Account;
import android.os.Bundle;

import me.zhanghai.android.douya.account.util.AccountUtils;
import me.zhanghai.android.douya.app.TargetedRetainedFragment;
import me.zhanghai.android.douya.network.RequestFragment;
import me.zhanghai.android.douya.network.api.ApiRequest;
import me.zhanghai.android.douya.util.FragmentUtils;

public class ResourceFragment extends TargetedRetainedFragment {

    // Not static because we are to be subclassed.
    protected final String KEY_PREFIX = getClass().getName() + '.';

    private final String EXTRA_ACCOUNT = KEY_PREFIX + "account";

    private Account mAccount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = FragmentUtils.ensureArguments(this);
        if (arguments.containsKey(EXTRA_ACCOUNT)) {
            mAccount = arguments.getParcelable(EXTRA_ACCOUNT);
        } else {
            mAccount = AccountUtils.getActiveAccount(getContext());
        }
    }

    public Account getAccount() {
        return mAccount;
    }

    protected <T, S> void startRequest(int requestCode, ApiRequest<T> request, S state) {
        request.setAccount(mAccount);
        RequestFragment.startRequest(request, state, this, requestCode);
    }

    protected <T, S> void startRequest(ApiRequest<T> request, S state) {
        startRequest(RequestFragment.REQUEST_CODE_INVALID, request, state);
    }
}
