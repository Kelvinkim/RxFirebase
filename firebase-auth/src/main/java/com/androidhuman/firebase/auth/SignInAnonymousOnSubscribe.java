package com.androidhuman.firebase.auth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.Subscriber;

final class SignInAnonymousOnSubscribe implements Observable.OnSubscribe<FirebaseUser> {

    private final FirebaseAuth instance;

    SignInAnonymousOnSubscribe(FirebaseAuth instance) {
        this.instance = instance;
    }

    @Override
    public void call(final Subscriber<? super FirebaseUser> subscriber) {
        final OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(task.getException());
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(task.getResult().getUser());
                    subscriber.onCompleted();
                }
            }
        };

        instance.signInAnonymously()
                .addOnCompleteListener(listener);
    }
}
