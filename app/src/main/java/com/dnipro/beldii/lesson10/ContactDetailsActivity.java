package com.dnipro.beldii.lesson10;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.dnipro.beldii.lesson10.databinding.ActivityContactDetailsBinding;
import com.dnipro.beldii.lesson10.helpers.AsyncPhotoLoader;
import com.dnipro.beldii.lesson10.model.Contact;

public class ContactDetailsActivity extends AppCompatActivity {
    private ActivityContactDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        showFruitDetails();
    }

    private void showFruitDetails() {
        Bundle contactBundle = getIntent().getExtras();
        if (contactBundle == null) return;
        Contact contact = (Contact)contactBundle.getSerializable("contact");
        if (contact != null) {
            binding.contactNameTextView.setText(contact.getName());
            binding.ageTextView.setText(Integer.toString(contact.getAge()));
            binding.companyTextView.setText(contact.getCompany());
            binding.genderTextView.setText(contact.getGender());
            binding.emailTextView.setText(contact.getEmail());
            AsyncPhotoLoader photoLoader = new AsyncPhotoLoader();
            photoLoader.load(contact.getPhoto(), binding.photoView);
        }
    }
}