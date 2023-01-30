package com.example.waclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.waclone.Adapter.FragmentAdapter;
import com.example.waclone.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);

    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu , menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Toast.makeText(this, "Setting is Clicked", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent2);
                break;
            case R.id.groupChat:
//                Toast.makeText(this, "Group chat is started", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this,GroupChatActivity.class);
                startActivity(intent1);
                break;
            case R.id.logout:


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.logout);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure want to Logout??");

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mAuth.signOut();
                        Intent intent = new Intent(MainActivity.this , SignInActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(MainActivity.this, "Logout Successful", Toast.LENGTH_LONG).show();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Logout Unsuccessful", Toast.LENGTH_LONG).show();

                    }
                });
                builder.show();



                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {



        AlertDialog.Builder exit = new AlertDialog.Builder(this);

        exit.setTitle("Exit??");
        exit.setMessage("Are you sure want to exit??");
        exit.setIcon(R.drawable.exit);
        exit.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "You are exit from Dialog Box app", Toast.LENGTH_LONG).show();
                MainActivity.super.onBackPressed();
            }
        });
        exit.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "You are not exit from Dialog Box app", Toast.LENGTH_LONG).show();
            }
        });

        exit.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "operation cancel!!", Toast.LENGTH_LONG).show();
            }
        });
        exit.show();
    }
}