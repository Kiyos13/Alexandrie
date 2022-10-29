package com.example.alexandrie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OneBookAllInfoActivity extends AppCompatActivity {

    private View editLayout, seeLayout;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_book_all_info);

        editLayout = findViewById(R.id.oneBookAllLytEdit);
        seeLayout = findViewById(R.id.oneBookAllLytSee);

        Bundle bundle = getIntent().getExtras();
        mode = bundle.getString("mode");

        // Display return arrow (with linked intent)
        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent returnIntent = new Intent(OneBookAllInfoActivity.this, ScanQRCodeActivity.class);

        if (mode.equals("edit")) {
            editLayout.setVisibility(View.VISIBLE);
            seeLayout.setVisibility(View.GONE);
            fragmentManager.beginTransaction().add(R.id.topBarOneBookInfoFragContainerVEdit, new AppBarFragment(returnIntent)).commit();
        }
        else if (mode.equals("see")) {
            editLayout.setVisibility(View.GONE);
            seeLayout.setVisibility(View.VISIBLE);
            fragmentManager.beginTransaction().add(R.id.topBarOneBookInfoFragContainerVSee, new AppBarFragment(returnIntent)).commit();
        }
    }
}