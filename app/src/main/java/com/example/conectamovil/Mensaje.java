package com.example.conectamovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class Mensaje extends AppCompatActivity {

    private MQTTManager mqttManager;
    private EditText messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        mqttManager = new MQTTManager(getApplicationContext());
        messageEditText = findViewById(R.id.editTextMessage);
        mqttManager.subscribeToTopic("prueba");
    }

    public void onSendMessageClick(View view) {
        String message = messageEditText.getText().toString();
        if (!message.isEmpty()) {
            mqttManager.publishMessage("prueba", message);
            messageEditText.setText("");
        }
    }
}
