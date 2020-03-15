package com.rikyahmadfathoni.developer.admindapenduk.ui.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rikyahmadfathoni.developer.admindapenduk.R;
import com.rikyahmadfathoni.developer.common_dapenduk.databinding.BottomSheetEditBinding;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataDetailsModel;
import com.rikyahmadfathoni.developer.common_dapenduk.ui.BaseDialogFragment;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsPermission;

public class EditableDialogFragment extends BaseDialogFragment<BottomSheetEditBinding>
        implements View.OnClickListener {

    static final String POSITION = "Position";

    static void showMe(FragmentManager fragmentManager, Bundle bundle) {

        EditableDialogFragment editableDialogFragment = EditableDialogFragment.newInstance();

        editableDialogFragment.setArguments(bundle);

        editableDialogFragment.show(fragmentManager, EditableDialogFragment.getSimpleName());
    }

    private static String getSimpleName() {
        return EditableDialogFragment.class.getSimpleName();
    }

    private static EditableDialogFragment newInstance() {
        return new EditableDialogFragment();
    }

    private DataDetailsModel dataDetailsModel;

    private int position;

    @Override
    protected int getLayoutRes() {
        return R.layout.bottom_sheet_edit;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getContext();

        if (context != null) {

            return new BottomSheetDialog(context, R.style.TransparentDialog);
        }

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    protected void onCreatedBundle(@Nullable Bundle bundle) {

        if (bundle != null) {
            dataDetailsModel = bundle.getParcelable(DataDetailsModel.TAG);
            position = bundle.getInt(EditableDialogFragment.POSITION, -1);
        }

        if (dataDetailsModel == null) {
            dataDetailsModel = new DataDetailsModel();
        }
    }

    @Override
    protected void onCreatedView() {

        initData();

        binding.btnSave.setOnClickListener(this);
        binding.btnCancel.setOnClickListener(this);

        binding.layoutImage.layoutGallery.setOnClickListener(this);
        binding.layoutImage.layoutCamera.setOnClickListener(this);
    }

    private void initData() {

        if (position == 0) {
            binding.layoutImage.layoutMain.setVisibility(View.VISIBLE);
            binding.layoutInput.setVisibility(View.GONE);
            binding.layoutButton.setVisibility(View.GONE);
        }

        binding.textTitle.setText(dataDetailsModel.getTextTitle());
        binding.inputValue.setText(dataDetailsModel.getTextValue());
    }

    @Override
    public void onClick(View v) {

        if (v == binding.datePicker) {
            //ignore
        } else if (v == binding.btnSave) {

            if (getParentFragment() instanceof DetailsFragment) {

                String beforeType = dataDetailsModel.getTextValue();
                String afterType = binding.inputValue.getText().toString();

                if (beforeType != null) {

                    if (!beforeType.trim().equals(afterType.trim())) {

                        DetailsFragment fragment = ((DetailsFragment) getParentFragment());

                        fragment.updateDataField(binding.inputValue.getText().toString(), position);
                    }
                }
            }

            dismiss();

        } else if (v == binding.btnCancel) {

            dismiss();
        } else if (v == binding.layoutImage.layoutGallery) {

            openGalleryIntent();

            dismiss();
        } else if (v == binding.layoutImage.layoutCamera) {

            openCameraIntent();

            dismiss();
        }
    }

    private boolean hasPermission() {

        final String[] permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        boolean hasPermission = UtilsPermission.hasPermission(getContext(), permissions);

        if (!hasPermission) {

            Fragment fragment = getParentFragment();

            if (fragment != null) {
                fragment.requestPermissions(permissions,
                        DetailsFragment.REQUEST_PERMISSION_CODE);
            }
        }

        return hasPermission;
    }


    private void openGalleryIntent() {

        Fragment fragment = getParentFragment();

        if (fragment != null && hasPermission()) {

            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            fragment.startActivityForResult(pickPhoto, DetailsFragment.REQUEST_GALLERY_IMAGE);
        }
    }

    private void openCameraIntent() {

        Fragment fragment = getParentFragment();

        if (fragment != null && hasPermission()) {

            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fragment.startActivityForResult(takePicture, DetailsFragment.REQUEST_CAMERA_IMAGE);
        }
    }
}
