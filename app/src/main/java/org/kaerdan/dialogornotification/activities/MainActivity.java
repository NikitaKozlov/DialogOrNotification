package org.kaerdan.dialogornotification.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.kaerdan.dialogornotification.R;
import org.kaerdan.dialogornotification.services.SomeService;


public class MainActivity extends Activity {

    public static final String PERFORM_SOMETHING_ACTION
            = "org.kaerdan.dialogornotification.PERFORM_SOMETHING_ACTION";
    public static final String SOME_ACTION = "org.kaerdan.dialogornotification.SOME_ACTION";

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            new AlertDialog.Builder(context)
                    .setTitle("Dialog")
                    .setPositiveButton("Ok", null)
                    .show();
            abortBroadcast();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this, SomeService.class));

        findViewById(R.id.start_service_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalBroadcastManager.getInstance(MainActivity.this)
                        .sendBroadcast(new Intent(PERFORM_SOMETHING_ACTION));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,
                new IntentFilter(SOME_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
