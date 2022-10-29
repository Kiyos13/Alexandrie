package com.example.alexandrie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.net.MalformedURLException;
import java.net.URL;

public class ScanQRCodeActivity extends AppCompatActivity {

    private CodeScanner codeScanner;
    private String isbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        // Display return arrow (with linked intent)
        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent returnIntent = new Intent(ScanQRCodeActivity.this, ListBooksActivity.class);
        fragmentManager.beginTransaction().add(R.id.topBarScanFragContainerV, new AppBarFragment(returnIntent)).commit();

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, scannerView);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isbn = result.getText();
                        System.out.println("\tISBN code = " + isbn);
                        Toast.makeText(ScanQRCodeActivity.this, result.getText(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ScanQRCodeActivity.this, OneBookAllInfoActivity.class);
                        Bundle bundle = new Bundle();
                        //bundle.putString("mode", "see");
                        bundle.putString("mode", "edit");
                        intent.putExtras(bundle); //Put your id to your next Intent
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }
}

// Source : https://github.com/yuriy-budiyev/code-scanner