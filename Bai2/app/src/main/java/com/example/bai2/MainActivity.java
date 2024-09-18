package com.example.bai2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;


import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textView1, textView2, textView3;
    private Button button1, button2, button3;
    private boolean isRunningThread1 = false;
    private boolean isRunningThread2 = false;
    private boolean isRunningThread3 = false;

    private int currentRandomNumber = 0;  // Giá trị hiện tại của Thread 1
    private int currentOddNumber = 1;     // Giá trị hiện tại của Thread 2
    private int currentNumber = 0;        // Giá trị hiện tại của Thread 3

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        // Sau khi Activity hiện ra, chờ 2 giây rồi bắt đầu các thread
        handler.postDelayed(() -> startThreads(), 2000);

        // Thread 1: Hiển thị số ngẫu nhiên trong khoảng 50-100 với độ trễ 1 giây
        button1.setOnClickListener(v -> {
            if (isRunningThread1) {
                isRunningThread1 = false;
                button1.setText("Start");
            } else {
                isRunningThread1 = true;
                button1.setText("Stop");
                startThread1();
            }
        });

        // Thread 2: Hiển thị các số lẻ tăng dần bắt đầu từ 1 với độ trễ 2.5 giây
        button2.setOnClickListener(v -> {
            if (isRunningThread2) {
                isRunningThread2 = false;
                button2.setText("Start");
            } else {
                isRunningThread2 = true;
                button2.setText("Stop");
                startThread2();
            }
        });

        // Thread 3: Hiển thị các số nguyên tăng dần bắt đầu từ 0 với độ trễ 2 giây
        button3.setOnClickListener(v -> {
            if (isRunningThread3) {
                isRunningThread3 = false;
                button3.setText("Start");
            } else {
                isRunningThread3 = true;
                button3.setText("Stop");
                startThread3();
            }
        });
    }

    // Hàm khởi chạy các Thread sau 2 giây khi Activity hiện ra
    private void startThreads() {
        isRunningThread1 = true;
        isRunningThread2 = true;
        isRunningThread3 = true;

        button1.setText("Stop");
        button2.setText("Stop");
        button3.setText("Stop");

        startThread1();
        startThread2();
        startThread3();
    }

    // Thread 1: Hiển thị số ngẫu nhiên trong khoảng 50-100 với độ trễ 1 giây
    private void startThread1() {
        new Thread(() -> {
            Random random = new Random();
            while (isRunningThread1) {
                final int randomNumber = random.nextInt(51) + 50;
                handler.post(() -> textView1.setText(String.valueOf(randomNumber)));
                try {
                    Thread.sleep(1000); // Độ trễ 1 giây
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Thread 2: Hiển thị các số lẻ tăng dần bắt đầu từ giá trị hiện tại với độ trễ 2.5 giây
    private void startThread2() {
        new Thread(() -> {
            while (isRunningThread2) {
                final int finalOddNumber = currentOddNumber;
                handler.post(() -> textView2.setText(String.valueOf(finalOddNumber)));
                currentOddNumber += 2;  // Tăng số lẻ thêm 2 mỗi lần
                try {
                    Thread.sleep(2500); // Độ trễ 2.5 giây
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Thread 3: Hiển thị các số nguyên tăng dần bắt đầu từ giá trị hiện tại với độ trễ 2 giây
    private void startThread3() {
        new Thread(() -> {
            while (isRunningThread3) {
                final int finalNumber = currentNumber;
                handler.post(() -> textView3.setText(String.valueOf(finalNumber)));
                currentNumber++;  // Tăng số nguyên lên 1 mỗi lần
                try {
                    Thread.sleep(2000); // Độ trễ 2 giây
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}