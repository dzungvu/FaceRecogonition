package com.cs.imageanalysis.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cs.imageanalysis.imagedetect.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Use to
 * Created by DzungVu on 9/3/2017.
 */

public class SelectImageSourceFragment extends Fragment {

    private final int PERMISSION_CODE = 0;
    private final int REQUEST_CODE_TAKE_IMAGE = 1;
    private final int REQUEST_CODE_CHOOSE_IMAGE = 2;
    private Button btnChooseExist;
    private Button btnTakePhoto;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        //Check the permission here
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_image_source, container, false);
        btnChooseExist = view.findViewById(R.id.btn_choose_exist);
        btnTakePhoto = view.findViewById(R.id.btn_take_photo);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String[] permissions = new String[]{Manifest.permission.CAMERA
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE};
        for (String param : permissions) {
            int permission = ContextCompat.checkSelfPermission(getActivity(), param);
            if (permission != PermissionChecker.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= 23) {
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                return;
            }
        }
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoTakePicture();
            }
        });

        btnChooseExist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoGallery();
            }
        });

    }

    private void gotoTakePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_TAKE_IMAGE);
    }

    private void gotoGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_CHOOSE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_IMAGE && resultCode == getActivity().RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                PreviewImageFragment previewImageFragment = PreviewImageFragment.newInstance(bitmap);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction
                        .replace(R.id.main_container, previewImageFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
        if (requestCode == REQUEST_CODE_CHOOSE_IMAGE && resultCode == getActivity().RESULT_OK) {
            chooseImageTask task = new chooseImageTask();
            task.execute(data);
        }
    }

    private class chooseImageTask extends AsyncTask<Intent, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Working");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            PreviewImageFragment previewImageFragment = PreviewImageFragment.newInstance(bitmap);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            progressDialog.dismiss();
            fragmentTransaction
                    .replace(R.id.main_container, previewImageFragment)
                    .addToBackStack(null)
                    .commit();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(Intent... intents) {
            Bitmap selectedBitmap = null;
            try {
                Uri imageUri = intents[0].getData();
                InputStream stream = getActivity().getContentResolver().openInputStream(imageUri);
                selectedBitmap = BitmapFactory.decodeStream(stream);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            return selectedBitmap;
        }
    }
}
