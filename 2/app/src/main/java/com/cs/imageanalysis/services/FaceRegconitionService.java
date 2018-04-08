package com.cs.imageanalysis.services;

import com.cs.imageanalysis.model.ImageRequest;
import com.cs.imageanalysis.model.ImageResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Use to
 * Created by DzungVu on 9/13/2017.
 */

public interface FaceRegconitionService {
    @POST("todo/api/v1.0/tasks/recognition")
    Observable<ImageResponse> postFaceRecognition(@Body ImageRequest imageRequest);
}
