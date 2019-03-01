package com.example.mvvmsampleproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final T data;
   /* @Nullable
    public final String message;*/
    public final AppException exception;


    private Resource(@NonNull Status status, @Nullable T data,
                     @Nullable AppException exception) {
        this.status = status;
        this.data = data;
        this.exception=exception;
    }

    public Status getCurrentState() {
        return status;
    }

    public AppException getException() {
        return exception;
    }

    public T getData() {
        return data;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(AppException exception, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, exception);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    public enum Status {SUCCESS, ERROR, LOADING}
}
