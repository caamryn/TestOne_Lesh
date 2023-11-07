package com.example.testone_lesh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();

        if(intent.getAction().equals(TelephonyManager.EXTRA_STATE_RINGING)){
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            String incomingCaller = intent.getStringExtra(TelephonyManager.EXTRA_CARRIER_NAME);
            String message = "Incoming call from: " + incomingCaller + " " + incomingNumber;
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        }

    }
}