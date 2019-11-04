package com.example.barterapp.view_models;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.barterapp.models.AuthentificationModel;
import com.example.barterapp.models.ProductsModel;
import com.example.barterapp.view_models.AccountViewModels.ProfileViewModel;

/**
 * ViewModel provider factory to instantiate View Models.
 * Required given View Model has a non-empty constructor
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(AuthentificationModel.getInstance());
        }
        else if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(AuthentificationModel.getInstance());
        }
        else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(AuthentificationModel.getInstance(), ProductsModel.getInstance());
        }
        else if (modelClass.isAssignableFrom(ForgotPassViewModel.class)) {
            return (T) new ForgotPassViewModel(AuthentificationModel.getInstance());
        }
        else if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(AuthentificationModel.getInstance());
        }
        else if (modelClass.isAssignableFrom(AddProductViewModel.class)) {
            return (T) new AddProductViewModel(ProductsModel.getInstance());
        }
        else if (modelClass.isAssignableFrom(ProductInfoViewModel.class)) {
            return (T) new ProductInfoViewModel(AuthentificationModel.getInstance());
        }//
        else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
