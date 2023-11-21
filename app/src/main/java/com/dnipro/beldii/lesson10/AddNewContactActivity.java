package com.dnipro.beldii.lesson10;

import androidx.appcompat.app.AppCompatActivity;
import com.dnipro.beldii.lesson10.databinding.ActivityAddNewContactBinding;
import com.dnipro.beldii.lesson10.model.Contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class AddNewContactActivity extends AppCompatActivity {
    private ActivityAddNewContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewContactBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.addSaveBtn.setOnClickListener((View view) -> {
            saveData();
        });
    }

    private void saveData() {
        String contactName = binding.addContactNameEditText.getText().toString();
        if (!contactName.isEmpty()) {
            Contact contact = new Contact(contactName);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("contact", contact);
            intent.putExtras(bundle);
            setResult(1, intent);
            finish();
        }
    }
}