package se.miun.visa2104.dt031g.contactlist;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactAdapter.OnItemClickListener {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();
        contactAdapter = new ContactAdapter(contactList, this);
        recyclerView.setAdapter(contactAdapter);

        if (checkReadContactsPermission()) {
            loadContacts();
        } else {
            requestReadContactsPermission();
        }
    }

    private boolean checkReadContactsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestReadContactsPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.READ_CONTACTS},
                PERMISSIONS_REQUEST_READ_CONTACTS
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts();
            } else {
                Toast.makeText(this, "Permission denied. Cannot fetch contacts.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadContacts() {
        contactList.clear();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                // Compruebe si el índice de la columna es válido antes de acceder a los datos
                if (nameIndex >= 0 && phoneNumberIndex >= 0) {
                    String name = cursor.getString(nameIndex);
                    String phoneNumber = cursor.getString(phoneNumberIndex);

                    Contact contact = new Contact(name, phoneNumber);
                    contactList.add(contact);
                }
            }
            cursor.close();
        }

        contactAdapter.notifyDataSetChanged();
    }
    // Método que se llama cuando se hace clic en un elemento del RecyclerView
    @Override
    public void onItemClick(Contact contact) {
        Intent intent = new Intent(this, ContactDetailActivity.class);
        intent.putExtra("name", contact.getName());
        intent.putExtra("phoneNumber", contact.getPhone());
        startActivity(intent);
    }
}