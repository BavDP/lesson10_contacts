package com.dnipro.beldii.lesson10.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dnipro.beldii.lesson10.R;
import com.dnipro.beldii.lesson10.helpers.AsyncPhotoLoader;
import com.dnipro.beldii.lesson10.model.Contact;

import java.io.InputStream;
import java.util.ArrayList;

class ContactDiffCallback extends DiffUtil.Callback {
    private ArrayList<Contact> oldContacts;
    private ArrayList<Contact> newContact;

    public ContactDiffCallback(ArrayList<Contact> oldContacts, ArrayList<Contact> newContact) {
        this.oldContacts = oldContacts;
        this.newContact = newContact;
    }

    @Override
    public int getOldListSize() {
        return oldContacts.size();
    }

    @Override
    public int getNewListSize() {
        return newContact.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldContacts.get(oldItemPosition).getId() == newContact.get(newItemPosition).getId() ;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldContacts.get(oldItemPosition).equals(newContact.get(newItemPosition));
    }
}


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    private ArrayList<Contact> contacts;
    private ContactAdapterClickListener itemViewClickListener;

    public ContactAdapterClickListener getItemViewClickListener() {
        return itemViewClickListener;
    }

    public void setItemViewClickListener(ContactAdapterClickListener itemViewClickListener) {
        this.itemViewClickListener = itemViewClickListener;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        final ContactDiffCallback diffCallback = new ContactDiffCallback(this.contacts, contacts);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.contacts = contacts;
        diffResult.dispatchUpdatesTo(this);
    }

    protected static class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;
        private TextView nameTextView;
        private TextView companyTextView;
        private ImageButton deleteBtn;
        private ImageView photoView;
        private Drawable photoDrawable;
        private ContactAdapterClickListener clickListener;

        public ContactAdapterClickListener getClickListener() {
            return clickListener;
        }

        public void setClickListener(ContactAdapterClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View view) {
            if (view == deleteBtn) {
                // нажатие кнопки удаления
                this.clickListener.deleteContactClick(itemView);
            } else if (view == nameTextView) {
                // нажатие по имени контакта
                this.clickListener.nameContactClick(itemView);
            }
        }

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
        public void bind(Contact contact) {
            nameTextView = itemView.findViewById(R.id.contactName);
            companyTextView = itemView.findViewById(R.id.company);
            photoView = itemView.findViewById(R.id.photo);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);

            nameTextView.setText(contact.getName());
            companyTextView.setText(contact.getCompany());

            itemView.setTag(contact);

            nameTextView.setOnClickListener(this);
            deleteBtn.setOnClickListener(this);

            if (!contact.getPhoto().isEmpty()) {
                AsyncPhotoLoader photoLoader = new AsyncPhotoLoader();
                photoLoader.load(contact.getPhoto(), photoView);
            } else {
                Drawable defaultPhoto = AppCompatResources.getDrawable(itemView.getContext(), R.drawable.user);
                photoView.setImageDrawable(defaultPhoto);
            }
        }
    }

    public ContactAdapter(ArrayList<Contact> contacts) {
        super();
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        holder.bind(contacts.get(position));
        holder.setClickListener(itemViewClickListener);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}