package com.sinervis.helloworld;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int PICK_CONTACT_REQUEST = 1;  // The request code

    TextView mTextView;
    FloatingActionButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mTextView = (TextView) findViewById(R.id.myTextView);
        mButton = (FloatingActionButton) findViewById(R.id.myButton);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickContact(v);
            }
        });

        FragmentText text = FragmentText.getInstance("Pluto");
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("back")
                .add(R.id.main_content, text)
                .commit();
    }

    protected Fragment getCurrentFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment f = fragments.get( fragments.size() - 1 );
        return f;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.call:
                callNumber();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(MainActivity.class.getName(), "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MainActivity.class.getName(), "onResume() called");
    }

    @Override
    public void onStop() {
        Log.d(MainActivity.class.getName(), "onStop() called");
        super.onStop();
    }

    @Override
    public void onPause() {
        Log.d(MainActivity.class.getName(), "onPause() called");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.d(MainActivity.class.getName(), "onDestroy() called");
        super.onDestroy();
    }

    public void callNumber() {
        String number = mTextView.getText().toString();
        if (!number.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please, enter a number", Toast.LENGTH_SHORT).show();
        }
    }

    public void pickContact(View v) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK,
                Uri.parse("content://contacts"));

        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_CONTACT_REQUEST) {

            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                String[] projection = { Phone.NUMBER };
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                String number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));

                mTextView.setText(number);
            }
        }
    }
}
