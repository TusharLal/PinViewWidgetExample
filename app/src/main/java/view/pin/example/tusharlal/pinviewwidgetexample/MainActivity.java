package view.pin.example.tusharlal.pinviewwidgetexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import view.pin.example.tusharlal.pinviewwidgetexample.widget.PinView;

public class MainActivity extends AppCompatActivity implements PinView.OnPinValueEntered{

    PinView pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pinView = findViewById(R.id.pinView);
        pinView.setOnPinValueEntered(this);
    }

    @Override
    public void onAllPinValueFilled(String pin) {
        Toast.makeText(this, "onAllPinValueFilled : "+pin, Toast.LENGTH_SHORT).show();
    }
}
