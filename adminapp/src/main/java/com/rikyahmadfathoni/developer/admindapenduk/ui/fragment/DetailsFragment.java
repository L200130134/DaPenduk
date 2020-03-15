package com.rikyahmadfathoni.developer.admindapenduk.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rikyahmadfathoni.developer.admindapenduk.R;
import com.rikyahmadfathoni.developer.admindapenduk.ui.activity.MainActivity;
import com.rikyahmadfathoni.developer.common_dapenduk.Constants;
import com.rikyahmadfathoni.developer.common_dapenduk.builder.MyBundle;
import com.rikyahmadfathoni.developer.common_dapenduk.callback.EditPendudukListener;
import com.rikyahmadfathoni.developer.common_dapenduk.callback.ImageProcessingListener;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataDetailsModel;
import com.rikyahmadfathoni.developer.common_dapenduk.task.ImageProcessingTask;
import com.rikyahmadfathoni.developer.common_dapenduk.ui.BaseDetailsFragment;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsData;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsDialog;

public class DetailsFragment extends BaseDetailsFragment {

    static final int REQUEST_GALLERY_IMAGE = 0;

    static final int REQUEST_CAMERA_IMAGE = 1;

    static final int REQUEST_PERMISSION_CODE = 1000;

    public static String getSimpleName() {
        return DetailsFragment.class.getSimpleName();
    }

    public static DetailsFragment newInstance(Bundle bundle) {

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected void initView() {

        adapter.setOnClickListener(new EditPendudukListener() {
            @Override
            public void onclick(DataDetailsModel detailsModel, int position) {

                Bundle bundle = MyBundle.Builder()
                        .putParcelable(DataDetailsModel.TAG, detailsModel)
                        .putInt(EditableDialogFragment.POSITION, position)
                        .Build();

                EditableDialogFragment.showMe(getChildFragmentManager(), bundle);
            }
        });
    }

    public void updateDataField(String fieldValue, int position) {

        adapter.getDataItem(position).setTextValue(fieldValue);

        adapter.notifyItemChanged(position);

        addCheckedMenu();
    }

    private void addCheckedMenu() {

        if (getActivity() instanceof MainActivity) {

            MainActivity activity = ((MainActivity) getActivity());

            UtilsData.updateDataPenduduk(dataPendudukModel, adapter.getCurrentList());

            activity.dataPendudukModel = dataPendudukModel;

            activity.setToolbarMenu(false, fragmentMode.equals(Constants.FRAGMENT_MODE_ADMIN_ADD)
                    ? R.menu.menu_add_check : R.menu.menu_edit_check);
        }
    }

    private ImageProcessingListener imageProcessingListener = new ImageProcessingListener() {

        @Override
        public void onCompleted(String imagePath, Bitmap bitmap, Exception e) {

            if (getActivity() != null) {

                getActivity().runOnUiThread(() -> {

                    if (e != null) {
                        UtilsDialog.showSnackbar(binding.recyclerView, e.getLocalizedMessage());
                        return;
                    }

                    if (adapter != null && imagePath != null) {

                        adapter.getDataItem(0).setImageUrl(imagePath);

                        adapter.notifyItemChanged(0);

                        addCheckedMenu();
                    }
                });
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY_IMAGE || requestCode == REQUEST_CAMERA_IMAGE) {

            if (resultCode == Activity.RESULT_OK) {

                if (data != null) {

                    Uri imageUri = data.getData();

                    if (imageUri != null) {

                        new ImageProcessingTask(imageProcessingListener)
                                .setImageUri(imageUri, 1000).execute();
                    } else {
                        Bundle bundle = data.getExtras();

                        if (bundle != null) {
                            Bitmap bitmap = (Bitmap) bundle.get("data");

                            new ImageProcessingTask(imageProcessingListener)
                                    .setBitmap(bitmap, 1000).execute();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                UtilsDialog.showSnackbar(binding.recyclerView, "Akses berhasil.");
            } else {

                UtilsDialog.showSnackbar(binding.recyclerView, "Akses tidak berhasil.");
            }
        }
    }
}
