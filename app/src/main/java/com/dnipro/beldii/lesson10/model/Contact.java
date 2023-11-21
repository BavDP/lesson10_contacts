package com.dnipro.beldii.lesson10.model;

import java.io.Serializable;
import java.util.Objects;

public class Contact implements Serializable {
      private String id = java.util.UUID.randomUUID().toString();
      private int age = 0;
      private String name;
      private String gender = "";
      private String company = "";
      private String email = "";
      private String photo = "";

      public int getAge() {
            return age;
      }

      public String getId() {
            return id;
      }

      public Contact(String name) {
            this.name = name;
      }

      public Contact() {
      }

      public String getName() {
            return name;
      }

      public String getGender() {
            return gender;
      }

      public String getCompany() {
            return company;
      }

      public String getEmail() {
            return email;
      }

      public String getPhoto() {
            return photo;
      }

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Contact contact = (Contact) o;
            return age == contact.age && id.equals(contact.id) && name.equals(contact.name) &&
                    Objects.equals(gender, contact.gender) && Objects.equals(company, contact.company) &&
                    Objects.equals(email, contact.email) && Objects.equals(photo, contact.photo);
      }

      @Override
      public int hashCode() {
            return Objects.hash(id, age, name, gender, company, email, photo);
      }
}
