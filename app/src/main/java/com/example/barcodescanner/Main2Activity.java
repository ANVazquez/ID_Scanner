package com.example.barcodescanner;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
//implementing the scannerView from ZXing **Check build gradle to see**
public class Main2Activity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    ImageView cChange;

    DatabaseReference dbREF;
    MainActivity.FirebaseHelper helper;
    FirebaseAuth mAuth;
    FirebaseDatabase mFireDB;
    FirebaseAuth.AuthStateListener mAuthListener;
    MainActivity.myAdapter adapter;

    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Making scanner view be set to the content to present that instead of the actual page itself
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        dbREF = FirebaseDatabase.getInstance().getReference();
        mFireDB = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

       // dbREF.child("users").child(userID).c
    }

    //Whatever the camera scans will be put in the Tv1
    @Override
    public void handleResult(Result result) {
        //MainActivity.tv1.setText(result.getText());
        new AlertDialog.Builder(this)
                .setTitle("Barcode Result")
                .setMessage(result.getText())
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                })
                .show();


    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.startCamera();
    }
}
