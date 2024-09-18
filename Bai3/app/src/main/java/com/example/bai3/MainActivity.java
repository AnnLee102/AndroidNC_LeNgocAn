package com.example.bai3;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;

import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView timeTextView;
    private Button setTimeButton, setDurationButton, stopButton;
    private Vibrator vibrator;
    private Handler handler;
    private int vibrationDuration = 0;
    private boolean isVibrating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeTextView = findViewById(R.id.timeTextView);
        setTimeButton = findViewById(R.id.setTimeButton);
        setDurationButton = findViewById(R.id.setDurationButton);
        stopButton = findViewById(R.id.stopButton);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        handler = new Handler();

        setTimeButton.setOnClickListener(v -> pickTime());
        setDurationButton.setOnClickListener(v -> setVibrationDuration());
        stopButton.setOnClickListener(v -> stopVibration());

        stopButton.setEnabled(false); // Tắt nút dừng khi chưa set time
    }

    private void pickTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minuteOfHour) -> {
            String time = String.format("%02d:%02d", hourOfDay, minuteOfHour);
            timeTextView.setText("Báo thức lúc: " + time);
            scheduleAlarm(hourOfDay, minuteOfHour);
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void scheduleAlarm(int hourOfDay, int minuteOfHour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minuteOfHour);
        calendar.set(Calendar.SECOND, 0);

        long delay = calendar.getTimeInMillis() - System.currentTimeMillis();
        if (delay < 0) {
            delay += 24 * 60 * 60 * 1000; // Đặt cho ngày hôm sau nếu thời gian đã qua
        }

        handler.postDelayed(this::startVibration, delay);
        Toast.makeText(this, "Đặt báo thức thành công!", Toast.LENGTH_SHORT).show();
    }

    private void setVibrationDuration() {
        // đặt thời lượng rung (tính bằng mili giây)
        vibrationDuration = 5 * 60 * 1000; // 5 phút
        Toast.makeText(this, "Thời lượng rung được đặt thành 5 phút", Toast.LENGTH_SHORT).show();
    }

    private void startVibration() {
        if (vibrationDuration > 0) {
            isVibrating = true;
            vibrator.vibrate(vibrationDuration);
            stopButton.setEnabled(true); // Bật nút dừng
            Toast.makeText(this, "Đang rung...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vui lòng đặt thời lượng rung", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopVibration() {
        if (isVibrating) {
            vibrator.cancel();
            isVibrating = false;
            stopButton.setEnabled(false); // Tắt nút dừng sau khi dừng
            Toast.makeText(this, "Đã dừng rung", Toast.LENGTH_SHORT).show();
        }
    }
}