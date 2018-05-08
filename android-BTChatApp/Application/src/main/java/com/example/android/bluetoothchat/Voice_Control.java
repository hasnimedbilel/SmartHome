package com.example.android.bluetoothchat;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Voice_Control extends Activity {

    private static final int REQUEST_CODE = 1234;
    private TextView wordtext;


    /**
     * Called w the activity is first created.
     */

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice__control);
        ImageButton speakButton = (ImageButton) findViewById(R.id.speakButton);
        wordtext = (TextView) findViewById(R.id.wordtext);

        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0)
        {
            speakButton.setEnabled(false);

        }
    }

    /**
     * Handle the action of the button being clicked
     */
    public void speakButtonClicked(View v)
    {
        startVoiceRecognitionActivity();
    }

    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String ch=matches.get(0);
            wordtext.setText(ch);
            BluetoothChatFragment.sendMessage(ch);
            /* Intent intent = new Intent(this, BluetoothChatFragment.class);
            intent.putExtra("result",ch);
            BluetoothChatFragment.request_voice=1;
            startActivity(intent); */

            //  bcf.sendMessage(ch);
            //  wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
            //          matches));

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void retourner(View view) {
        Intent ret = new Intent(Voice_Control.this , MainActivity.class);
        startActivity(ret);
    }

    public void goToWifi(View view) {
        Intent i = new Intent(Voice_Control.this,WifiActivity.class);
        startActivity(i);
    }
}
