package com.example.alexandrie;

import static com.example.alexandrie.BooksAdapter.onBindViewHolderCover;
import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ScanQRCodeActivity extends AppCompatActivity {

    private Context context;
    private String isbn, previousActivity;
    private BookAPIRequest bookAPIRequest;
    private List<BookAPIRequestResult> resultAPIRequest;
    private BookAPIRequestResult resultingBookAPIRequest;
    private List<String> authors;
    private String title, author, coverUrl, publishYear;
    private android.widget.Button scanBarcodeBtn, addManuallyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        colorSystemBarTop(getWindow(), getResources(), this); // Set the color of the system bar at the top

        context = this;

        // Retrieve Bundle infos
        Bundle bundle = getIntent().getExtras();
        previousActivity = bundle.getString("prevActivity");

        // Display return arrow (with linked intent)
        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent returnIntent = new Intent(ScanQRCodeActivity.this, ListBooksActivity.class); // Default
        if (previousActivity.equals("verticalList"))
            returnIntent = new Intent(ScanQRCodeActivity.this, ListBooksActivity.class);
        fragmentManager.beginTransaction().add(R.id.topBarScanFragContainerV, new AppBarFragment(returnIntent)).commit();


        scanBarcodeBtn = findViewById(R.id.scanBarcodeBtn);
        addManuallyBtn = findViewById(R.id.addManuallyBtn);

        scanBarcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ScanQRCodeActivity.this);
                intentIntegrator.setPrompt("Pour utiliser le flash utilisez le bouton du volume");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });

        addManuallyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanQRCodeActivity.this, OneBookAllInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "create");
                bundle.putString("prevActivity", "scan");
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult.getContents() != null) {
            Toast.makeText(ScanQRCodeActivity.this, "Livre correctement scanné.", Toast.LENGTH_SHORT).show();
            Thread thread = onDecodeIsbnCode(intentResult.getContents());
            thread.start();
        }
        else
            showErrorBuilder("Résultat", "Oup ... Aucun code barre n'a été détecté.");
    }

    private Thread onDecodeIsbnCode(String scannedIsbn) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // isbn = "2765410054"; // For tests
                isbn = scannedIsbn;

                bookAPIRequest = new BookAPIRequest(context);
                resultAPIRequest = bookAPIRequest.doInBackground(isbn, "DEFAULT");
                if (resultAPIRequest != null) {
                    resultingBookAPIRequest = resultAPIRequest.get(0);
                    System.out.println(resultingBookAPIRequest.getTitle());
                    title = resultingBookAPIRequest.getTitle();
                    authors = resultingBookAPIRequest.getAuthors();
                    author = "";
                    for (int i = 0; i < authors.size(); i++) {
                        if (i == 0)
                            author += authors.get(i);
                        else
                            author += " " + authors.get(i);
                    }
                    coverUrl = resultingBookAPIRequest.getCoverUrl();
                    publishYear = resultingBookAPIRequest.getPublishYear();

                    Intent intent = new Intent(ScanQRCodeActivity.this, OneBookAllInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("mode", "create");
                    bundle.putString("prevActivity", "scan");
                    bundle.putString("title", title);
                    bundle.putString("author", author);
                    bundle.putString("releaseDate", publishYear);
                    bundle.putString("coverUrl", coverUrl);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
                else
                    showErrorBuilder("Résultat", "Oup ... Aucun code barre n'a été détecté.");
            }
        });
        return thread;
    }

    private void showErrorBuilder(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}

// Source : https://github.com/journeyapps/zxing-android-embedded