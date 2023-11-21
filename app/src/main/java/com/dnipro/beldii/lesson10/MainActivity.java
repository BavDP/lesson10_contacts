package com.dnipro.beldii.lesson10;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dnipro.beldii.lesson10.adapters.ContactAdapter;
import com.dnipro.beldii.lesson10.adapters.ContactAdapterClickListener;
import com.dnipro.beldii.lesson10.helpers.JsonReader;
import com.dnipro.beldii.lesson10.model.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Contact> contacts;
    private ContactAdapter contactAdapter;
    private FloatingActionButton addButton;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 1) {
                        Contact contact = (Contact) result.getData().getExtras().getSerializable("contact");
                        if (contact != null) {
                            ArrayList<Contact> newContacts = new ArrayList<>();
                            newContacts.addAll(contacts);
                            newContacts.add(0, contact);
                            contactAdapter.setContacts(newContacts);
                            contacts = newContacts;
                        }
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.contactList);
        addButton = findViewById(R.id.addNewContactBtn);
        addButton.setOnClickListener((View v) -> {
            addContactToList();
        });
        createContactRecycleList();
    }

    private ArrayList<Contact> getContacts() {
        InputStream fruitJsonStream = getResources().openRawResource(R.raw.contacts);
        JsonReader jsonReader = new JsonReader(fruitJsonStream);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Type contactType = new TypeToken<Collection<Contact>>(){}.getType();
        Collection<Contact> contacts = gson.fromJson(jsonReader.getAsString(), contactType);
        this.contacts = new ArrayList<>(contacts);
        return this.contacts;
    }

    private void createContactRecycleList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(getContacts());
        contactAdapter.setItemViewClickListener(new ContactAdapterClickListener() {
            @Override
            public void nameContactClick(View view) {
                openContactDetails(view);
            }

            @Override
            public void deleteContactClick(View view) {
                deleteContactFromList(view);
            }
        });
        recyclerView.setAdapter(contactAdapter);
    }

    private void openContactDetails(View view) {
        Contact contact = (Contact)view.getTag();
        Intent intent = new Intent(this, ContactDetailsActivity.class);
        Bundle contactBundle = new Bundle();
        contactBundle.putSerializable("contact", contact);
        intent.putExtras(contactBundle);
        startActivity(intent);
    }

    private void deleteContactFromList(View view) {
        Contact contact = (Contact) view.getTag();
        ArrayList<Contact> newContacts = new ArrayList<>();
        newContacts.addAll(this.contacts);
        if (newContacts.remove(contact)) {
            this.contactAdapter.setContacts(newContacts);
            this.contacts = newContacts;
        }
    }

    private void addContactToList() {
        Intent intent = new Intent(this, AddNewContactActivity.class);
        launcher.launch(intent);
    }
}