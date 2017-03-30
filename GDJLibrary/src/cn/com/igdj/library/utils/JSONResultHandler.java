package cn.com.igdj.library.utils;

public abstract class JSONResultHandler {
    public abstract void onSuccess(String jsonString);
    public abstract void onError(String errorMessage);
}