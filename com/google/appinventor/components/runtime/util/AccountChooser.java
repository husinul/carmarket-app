package com.google.appinventor.components.runtime.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import java.util.concurrent.SynchronousQueue;

public class AccountChooser {
    private static final String ACCOUNT_PREFERENCE = "account";
    private static final String ACCOUNT_TYPE = "com.google";
    private static final String LOG_TAG = "AccountChooser";
    private static final String NO_ACCOUNT = "";
    private AccountManager accountManager;
    private Activity activity;
    private String chooseAccountPrompt;
    private String preferencesKey;
    private String service;

    class SelectAccount extends Thread implements OnClickListener, OnCancelListener {
        private String[] accountNames;
        private SynchronousQueue<String> queue;

        /* renamed from: com.google.appinventor.components.runtime.util.AccountChooser.SelectAccount.1 */
        class C01351 implements Runnable {
            C01351() {
            }

            public void run() {
                new Builder(AccountChooser.this.activity).setTitle(Html.fromHtml(AccountChooser.this.chooseAccountPrompt)).setOnCancelListener(SelectAccount.this).setSingleChoiceItems(SelectAccount.this.accountNames, -1, SelectAccount.this).show();
                Log.i(AccountChooser.LOG_TAG, "Dialog showing!");
            }
        }

        SelectAccount(Account[] accounts, SynchronousQueue<String> queue) {
            this.queue = queue;
            this.accountNames = new String[accounts.length];
            for (int i = 0; i < accounts.length; i++) {
                this.accountNames[i] = accounts[i].name;
            }
        }

        public void run() {
            AccountChooser.this.activity.runOnUiThread(new C01351());
        }

        public void onClick(DialogInterface dialog, int button) {
            if (button >= 0) {
                try {
                    String account = this.accountNames[button];
                    Log.i(AccountChooser.LOG_TAG, "Chose: " + account);
                    this.queue.put(account);
                } catch (InterruptedException e) {
                }
            } else {
                this.queue.put(AccountChooser.NO_ACCOUNT);
            }
            dialog.dismiss();
        }

        public void onCancel(DialogInterface dialog) {
            Log.i(AccountChooser.LOG_TAG, "Chose: canceled");
            onClick(dialog, -1);
        }
    }

    public AccountChooser(Activity activity, String service, String title, String key) {
        this.activity = activity;
        this.service = service;
        this.chooseAccountPrompt = title;
        this.preferencesKey = key;
        this.accountManager = AccountManager.get(activity);
    }

    public Account findAccount() {
        Account[] accounts = this.accountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length == 1) {
            persistAccountName(accounts[0].name);
            return accounts[0];
        } else if (accounts.length == 0) {
            accountName = createAccount();
            if (accountName != null) {
                persistAccountName(accountName);
                return this.accountManager.getAccountsByType(ACCOUNT_TYPE)[0];
            }
            Log.i(LOG_TAG, "User failed to create a valid account");
            return null;
        } else {
            accountName = getPersistedAccountName();
            if (accountName != null) {
                Account account = chooseAccount(accountName, accounts);
                if (account != null) {
                    return account;
                }
            }
            accountName = selectAccount(accounts);
            if (accountName != null) {
                persistAccountName(accountName);
                return chooseAccount(accountName, accounts);
            }
            Log.i(LOG_TAG, "User failed to choose an account");
            return null;
        }
    }

    private Account chooseAccount(String accountName, Account[] accounts) {
        for (Account account : accounts) {
            if (account.name.equals(accountName)) {
                Log.i(LOG_TAG, "chose account: " + accountName);
                return account;
            }
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String createAccount() {
        /*
        r12 = this;
        r3 = 0;
        r0 = "AccountChooser";
        r1 = "Adding auth token account ...";
        android.util.Log.i(r0, r1);
        r0 = r12.accountManager;
        r1 = "com.google";
        r2 = r12.service;
        r5 = r12.activity;
        r4 = r3;
        r6 = r3;
        r7 = r3;
        r10 = r0.addAccount(r1, r2, r3, r4, r5, r6, r7);
        r11 = r10.getResult();	 Catch:{ OperationCanceledException -> 0x003c, AuthenticatorException -> 0x0042, IOException -> 0x0047 }
        r11 = (android.os.Bundle) r11;	 Catch:{ OperationCanceledException -> 0x003c, AuthenticatorException -> 0x0042, IOException -> 0x0047 }
        r0 = "authAccount";
        r8 = r11.getString(r0);	 Catch:{ OperationCanceledException -> 0x003c, AuthenticatorException -> 0x0042, IOException -> 0x0047 }
        r0 = "AccountChooser";
        r1 = new java.lang.StringBuilder;	 Catch:{ OperationCanceledException -> 0x003c, AuthenticatorException -> 0x0042, IOException -> 0x0047 }
        r1.<init>();	 Catch:{ OperationCanceledException -> 0x003c, AuthenticatorException -> 0x0042, IOException -> 0x0047 }
        r2 = "created: ";
        r1 = r1.append(r2);	 Catch:{ OperationCanceledException -> 0x003c, AuthenticatorException -> 0x0042, IOException -> 0x0047 }
        r1 = r1.append(r8);	 Catch:{ OperationCanceledException -> 0x003c, AuthenticatorException -> 0x0042, IOException -> 0x0047 }
        r1 = r1.toString();	 Catch:{ OperationCanceledException -> 0x003c, AuthenticatorException -> 0x0042, IOException -> 0x0047 }
        android.util.Log.i(r0, r1);	 Catch:{ OperationCanceledException -> 0x003c, AuthenticatorException -> 0x0042, IOException -> 0x0047 }
    L_0x003b:
        return r8;
    L_0x003c:
        r9 = move-exception;
        r9.printStackTrace();
    L_0x0040:
        r8 = r3;
        goto L_0x003b;
    L_0x0042:
        r9 = move-exception;
        r9.printStackTrace();
        goto L_0x0040;
    L_0x0047:
        r9 = move-exception;
        r9.printStackTrace();
        goto L_0x0040;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.AccountChooser.createAccount():java.lang.String");
    }

    private String selectAccount(Account[] accounts) {
        SynchronousQueue<String> queue = new SynchronousQueue();
        new SelectAccount(accounts, queue).start();
        Log.i(LOG_TAG, "Select: waiting for user...");
        String account = null;
        try {
            account = (String) queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Selected: " + account);
        return account == NO_ACCOUNT ? null : account;
    }

    private SharedPreferences getPreferences() {
        return this.activity.getSharedPreferences(this.preferencesKey, 0);
    }

    private String getPersistedAccountName() {
        return getPreferences().getString(ACCOUNT_PREFERENCE, null);
    }

    private void persistAccountName(String accountName) {
        Log.i(LOG_TAG, "persisting account: " + accountName);
        getPreferences().edit().putString(ACCOUNT_PREFERENCE, accountName).commit();
    }

    public void forgetAccountName() {
        getPreferences().edit().remove(ACCOUNT_PREFERENCE).commit();
    }
}
