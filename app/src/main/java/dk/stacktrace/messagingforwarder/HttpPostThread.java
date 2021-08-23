package dk.stacktrace.messagingforwarder;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.*;

class HttpPostThread implements Runnable {
    private static final String TAG = HttpPostThread.class.getName();

    public HttpPostThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        String botAuth = "bot1234567890:XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        String botChatId = "123456789";

        String targetUrl = "https://api.telegram.org/" + botAuth + "/sendMessage";
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("chat_id", botChatId)
                .add("text", message)
                .build();
        Request request = new Request.Builder()
                .url(targetUrl)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        }  catch (IOException e) {
            Log.w(TAG, "Error communicating with Telegram server", e);
        }
    }
    private final String message;
}
