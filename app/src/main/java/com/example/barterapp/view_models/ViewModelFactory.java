package com.example.barterapp.view_models;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.barterapp.models.AuthentificationModel;
import com.example.barterapp.models.OffersModel;
import com.example.barterapp.models.ProductsModel;
import com.example.barterapp.view_models.AccountViewModels.MyHistoryViewModel;
import com.example.barterapp.view_models.AccountViewModels.MyOffersViewModel;
import com.example.barterapp.view_models.AccountViewModels.MyProductsViewModel;
import com.example.barterapp.view_models.AccountViewModels.ProfileViewModel;
import com.example.barterapp.views.AccountFragments.MyProductsFragment;

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
        }
        else if (modelClass.isAssignableFrom(MakeOfferViewModel.class)) {
            return (T) new MakeOfferViewModel(OffersModel.getInstance());
        }
        else if (modelClass.isAssignableFrom(MyOffersViewModel.class)) {
            return (T) new MyOffersViewModel(OffersModel.getInstance());
        }
        else if (modelClass.isAssignableFrom(MyHistoryViewModel.class)) {
            return (T) new MyHistoryViewModel(OffersModel.getInstance());
        }
        else if (modelClass.isAssignableFrom(MyProductsViewModel.class)) {
            return (T) new MyProductsViewModel(ProductsModel.getInstance());
        }
        else if (modelClass.isAssignableFrom(MakeOfferViewModel.class)) {
            return (T) new MakeOfferViewModel(OffersModel.getInstance());
        }
        else if (modelClass.isAssignableFrom(ViewOfferViewModel.class)) {
            return (T) new ViewOfferViewModel(OffersModel.getInstance());
        }
        else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
