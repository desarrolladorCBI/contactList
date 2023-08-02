package se.miun.visa2104.dt031g.contactlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        // Extraer los datos de contacto del Intent
        String contactName = getIntent().getStringExtra("name");
        String contactPhoneNumber = getIntent().getStringExtra("phoneNumber");

        // Encuentra TextViews en el layout
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        Button callButton = findViewById(R.id.callButton);

        //Mostrar los datos de contacto en TextViews
        textViewName.setText(contactName);
        textViewPhoneNumber.setText(contactPhoneNumber);



        // Set an OnClickListener for the call button
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + contactPhoneNumber));
                startActivity(callIntent);
            }
        });
    }
}