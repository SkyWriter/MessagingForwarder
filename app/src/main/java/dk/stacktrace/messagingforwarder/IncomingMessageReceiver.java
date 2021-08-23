package dk.stacktrace.messagingforwarder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class IncomingMessageReceiver extends BroadcastReceiver {
    private static final String TAG = IncomingMessageReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (SmsMessage message : messages) {
            String phone = message.getDisplayOriginatingAddress();
            String msg = message.getDisplayMessageBody();
            String forwardedText = phone + "> " + msg;
            new Thread(new HttpPostThread(forwardedText)).start();
        }
    }
}
