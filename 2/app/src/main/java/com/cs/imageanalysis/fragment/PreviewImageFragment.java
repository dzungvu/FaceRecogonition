package com.cs.imageanalysis.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cs.imageanalysis.imagedetect.R;
import com.cs.imageanalysis.model.ImageRequest;
import com.cs.imageanalysis.model.ImageResponse;
import com.cs.imageanalysis.services.FaceRegconitionService;
import com.cs.imageanalysis.services.ServiceGenerator;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Use to
 * Created by DzungVu on 9/3/2017.
 */

public class PreviewImageFragment extends Fragment {
    private static final String TAG = "IMAGE";
    private static final String LOG_TAG = "PREVIEW--";
    private Bitmap bitmap;
    private ImageView ivPreview;
    private String url;
    private ImageResponse imageResponse;
    private ImageRequest imageRequest;
    private Handler mHandler;
    private FaceRegconitionService faceRegconitionService;
    private Subscription subscription;
    private Dialog dialog;
    private Runnable mRun;
    private byte[] byteArray;


    public static PreviewImageFragment newInstance(Bitmap bm) {

        PreviewImageFragment previewImageFragment = new PreviewImageFragment();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Bundle bundle = new Bundle();
        bundle.putByteArray(TAG, byteArray);
        previewImageFragment.setArguments(bundle);
        return previewImageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        byteArray = getArguments().getByteArray(TAG);
        assert byteArray != null;
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        faceRegconitionService = ServiceGenerator.createFaceReognitionService(FaceRegconitionService.class);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Preview");
        View view = inflater.inflate(R.layout.fragment_preview_image, container, false);
        ivPreview = view.findViewById(R.id.iv_preview_image);
        ivPreview.setImageBitmap(bitmap);
        imageResponse = new ImageResponse();
        imageRequest = new ImageRequest();
        mHandler = new Handler();
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.wait_dialog);
        dialog.setCancelable(false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.preview_menu, menu);
    }

    private void sendToServer(Bitmap bitmap) {
        dialog.show();
        final ImageRequest imageRequest = new ImageRequest();
        imageRequest.setId(2);
        Log.i(LOG_TAG, "" + imageRequest.getId());
        imageRequest.setTitle("title test image");
        Log.i(LOG_TAG, imageRequest.getTitle());
        String bitString = enCodeImage(bitmap);
        bitString = bitString.replaceAll("\\n", "");
        imageRequest.setValue(bitString);
        Log.i(LOG_TAG, imageRequest.getValue());
        subscription = faceRegconitionService.postFaceRecognition(imageRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ImageResponse>() {
                    @Override
                    public void onCompleted() {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ResultFragment resultFragment = ResultFragment.newInstance(imageResponse, byteArray);
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.main_container, resultFragment)
                                        .commit();
                            }
                        });
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(LOG_TAG, e.toString());
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(ImageResponse imageResponsee) {
                        Log.i(LOG_TAG, imageResponsee.getName());
                        imageResponse = imageResponsee;
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_preview_send:
                //do something
                sendToServer(bitmap);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String enCodeImage(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return (Base64.encodeToString(imageBytes, Base64.DEFAULT));

    }
}
