package com.example.barterapp.view_models;

import androidx.lifecycle.ViewModel;

import com.example.barterapp.models.AuthentificationModel;

public class AddProductViewModel extends ViewModel {
    AuthentificationModel   mAuth;

    AddProductViewModel(AuthentificationModel auth) { mAuth = auth;}


}
