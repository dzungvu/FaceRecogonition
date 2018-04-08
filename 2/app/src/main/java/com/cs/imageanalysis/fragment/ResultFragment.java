package com.cs.imageanalysis.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.imageanalysis.imagedetect.R;
import com.cs.imageanalysis.model.ImageResponse;

/**
 * Use to
 * Created by DzungVu on 9/10/2017.
 */

public class ResultFragment extends Fragment {
    private static final String RESULT_TAG_INFOR = "TAG_INFOR";
    private static final String RESULT_TAG_ARRAY = "IMAGE";
    private ImageView imgSearch;
    private TextView tvName;
    private TextView tvAge;
    private TextView tvGender;
    private TextView tvSchool;
    private TextView tvMajor;
    private TextView tvDescription;
    private ImageResponse imageResponse;
    private Button btnNew;
    private byte[] byteArray;

    public static ResultFragment newInstance(ImageResponse imageResponse, byte[] byteArray) {
        ResultFragment resultFragment = new ResultFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RESULT_TAG_INFOR, imageResponse);
        bundle.putByteArray(RESULT_TAG_ARRAY, byteArray);
        resultFragment.setArguments(bundle);
        return resultFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageResponse = getArguments().getParcelable(RESULT_TAG_INFOR);
        byteArray = getArguments().getByteArray(RESULT_TAG_ARRAY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        imgSearch = view.findViewById(R.id.img_search);
        tvName = view.findViewById(R.id.tv_name);
        tvAge = view.findViewById(R.id.tv_age);
        tvGender = view.findViewById(R.id.tv_gender);
        tvSchool = view.findViewById(R.id.tv_school);
        tvMajor = view.findViewById(R.id.tv_major);
        tvDescription = view.findViewById(R.id.tv_description);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imgSearch.setImageBitmap(bitmap);
        btnNew = view.findViewById(R.id.btn_new);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTurn();
            }
        });

        if (imageResponse != null) {
            tvName.setText(imageResponse.getName());
            tvAge.setText(String.valueOf(imageResponse.getAge()));
            tvGender.setText(imageResponse.getGender());
            tvMajor.setText(imageResponse.getMajor());
            tvSchool.setText(imageResponse.getSchool());
            tvDescription.setText(imageResponse.getDescription());

        }
        return view;
    }

    private void newTurn(){
        FragmentManager fragmentManager = getFragmentManager();
        SelectImageSourceFragment selectImageSourceFragment = new SelectImageSourceFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, selectImageSourceFragment).commit();
    }
}
