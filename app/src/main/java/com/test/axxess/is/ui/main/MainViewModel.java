package com.test.axxess.is.ui.main;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.util.Log;

import com.test.axxess.is.MainActivity;
import com.test.axxess.is.ui.network.ImageData;
import com.test.axxess.is.ui.network.ImageSearchResponse;
import com.test.axxess.is.ui.network.ApiClientListener;
import com.test.axxess.is.ui.network.NetworkModule;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel class for main activity
 */
public class MainViewModel extends BaseObservable {
    private static final String TAG = MainViewModel.class.getSimpleName();
    private static final int STATUS_SUCCESS = 200;

    public ObservableBoolean showLoader = new ObservableBoolean(false);
    public ObservableBoolean noImages = new ObservableBoolean(false);

    public ObservableField<String> searchData = new ObservableField<>("");
    public ObservableField<List<ImageData>> imageDataList = new ObservableField<List<ImageData>>(new ArrayList<ImageData>());

    private MainActivity mainActivity;

    public MainViewModel(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * Load images from api call
     */
    public void loadImagesOnApiCall() {
        Log.d(TAG, "on search click");
        ApiClientListener apiClientListener = NetworkModule.getRetrofitInstance().create(ApiClientListener.class);
        String searchData = this.searchData.get();
        Log.d(TAG, "searchData is " + searchData);
        if (searchData == null || searchData.isEmpty()) {
            return;
        }

        mainActivity.setUntouchableWindowFlag();
        Call<ImageSearchResponse> imageSearchResponse = apiClientListener.getImageSearchResponse(searchData);
        showLoader.set(true);
        imageSearchResponse.enqueue(new Callback<ImageSearchResponse>() {
            @Override
            public void onResponse(Call<ImageSearchResponse> call, Response<ImageSearchResponse> response) {
                if (response == null || response.body() == null || (response != null && response.errorBody() != null)) {
                    onFailure(call, null);
                    return;
                }
                mainActivity.clearWindowFlags();
                showLoader.set(false);
                noImages.set(false);
                ImageSearchResponse imageSearchResponse = response.body();
                if (imageSearchResponse.getStatus() == STATUS_SUCCESS) {
                    if (imageSearchResponse.getData() == null || imageSearchResponse.getData().isEmpty()) {
                        noImages.set(true);
                    }
                    imageDataList.set(imageSearchResponse.getData());
                }
            }

            @Override
            public void onFailure(Call<ImageSearchResponse> call, Throwable t) {
                mainActivity.clearWindowFlags();
                showLoader.set(false);
                noImages.set(true);
            }
        });
    }

    /**
     * After edit text changed
     *
     * @param s
     */
    public void afterTextChanged(Editable s) {
        searchData.set(s.toString());
    }
}
