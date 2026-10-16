package com.example.cannongame;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private CannonView cannonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        cannonView = new CannonView(this);
        setContentView(cannonView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cannonView != null) {
            cannonView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cannonView != null) {
            cannonView.resume();
        }
    }
}