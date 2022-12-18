package com.example.alexandrie;

import static com.example.alexandrie.BooksAdapter.onBindViewHolderCover;
import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
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
import java.util.List;

public class ScanQRCodeActivity extends AppCompatActivity {

    private Context context;
    private CodeScanner codeScanner;
    private String isbn, previousActivity;
    private BookAPIRequest bookAPIRequest;
    private List<BookAPIRequestResult> resultAPIRequest;
    private BookAPIRequestResult resultingBookAPIRequest;
    private List<String> authors;
    private String title, author, coverUrl, publishYear;

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

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, scannerView);

        Thread thread = onDecodeIsbnCode();
        thread.start();

        /*
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isbn = result.getText();
                        System.out.println("\tISBN code = " + isbn);
                        Toast.makeText(ScanQRCodeActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        //isbn = "9782344007440";

                        bookAPIRequest = new BookAPIRequest(context);
                        resultAPIRequest = bookAPIRequest.doInBackground(isbn);
                        resultingBookAPIRequest = resultAPIRequest.get(0);
                        title = resultingBookAPIRequest.getTitle();
                        authors = resultingBookAPIRequest.getAuthors();
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
                });
            }
        });
        */

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

    private Thread onDecodeIsbnCode() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //isbn = result.getText();
                isbn = "2765410054";
                System.out.println("\tISBN code = " + isbn);
                //Toast.makeText(ScanQRCodeActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                //isbn = "9782344007440";

                bookAPIRequest = new BookAPIRequest(context);
                resultAPIRequest = bookAPIRequest.doInBackground(isbn);
                resultingBookAPIRequest = resultAPIRequest.get(0);
                title = resultingBookAPIRequest.getTitle();
                authors = resultingBookAPIRequest.getAuthors();
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
        });
        return thread;
    }
}

// Source : https://github.com/yuriy-budiyev/code-scanner