package com.thoughtworks.todo_list.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.thoughtworks.todo_list.R;
import com.thoughtworks.todo_list.common.EditTextRegexRule;
import com.thoughtworks.todo_list.repository.utils.Encryptor;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private UserRepository userRepository;

    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void observeLoginFormState(LifecycleOwner lifecycleOwner, Observer<LoginFormState> observer) {
        loginFormState.observe(lifecycleOwner, observer);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    void observeLoginResult(LifecycleOwner lifecycleOwner, Observer<LoginResult> observer) {
        loginResult.observe(lifecycleOwner, observer);
    }

    public void login(String username, String password) {
        Disposable disposable = userRepository.findByName(username)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(compositeDisposable::add)
                .doOnComplete(() -> loginResult.postValue(new LoginResult(R.string.login_failed_username)))
                .doOnSuccess(u -> {
                            if (u.getPassword().equals(Encryptor.md5(password))) {
                                loginResult.postValue(new LoginResult(new LoggedInUserView(u.getName())));
                            } else {
                                loginResult.postValue(new LoginResult(R.string.login_failed_password));
                            }
                        }
                ).subscribe();
        compositeDisposable.add(disposable);
    }

    public void loginDataChanged(String username, String password) {
        if (isUserNameInvalid(username)) {
            loginFormState.postValue(new LoginFormState(R.string.invalid_username, null));
        } else if (isPasswordInvalid(password)) {
            loginFormState.postValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.postValue(new LoginFormState(true));
        }
    }

    private boolean isUserNameInvalid(String username) {
        if (username == null) {
            return true;
        }
        if (username.contains("@")) {
            return !Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().matches(EditTextRegexRule.USERNAME_RULE.getRegex());
        }
    }

    private boolean isPasswordInvalid(String password) {
        return !(password != null && password.trim().matches(EditTextRegexRule.PASSWORD_RULE.getRegex()));
    }
}