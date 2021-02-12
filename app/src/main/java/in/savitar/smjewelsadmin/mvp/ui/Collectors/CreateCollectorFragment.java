package in.savitar.smjewelsadmin.mvp.ui.Collectors;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentCreateCollectorBinding;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.CreateUserFragment;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityContract;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityPresenter;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;


public class CreateCollectorFragment extends DialogFragment implements MainActivityContract.View {

    FragmentCreateCollectorBinding mBinding;
    MainActivityPresenter mPresenter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String imageUrl;
    Uri imageUri;
    public static final int IMAGE_CODE = 1;
    StorageReference storageReference;

    EasyImage easyImage;

    int imageFlag = 0;




    public CreateCollectorFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_collector, container, false);
        mPresenter = new MainActivityPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {


        mBinding.userPhotoCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        mBinding.signUpCollector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCollector();
            }
        });

    }

    private void createCollector() {

        if (mBinding.collectorIdSignup.getText().toString().compareToIgnoreCase("") == 0){
            Toasty.error(getContext(),"Enter ID").show();
        } else if (mBinding.collectorNameSignUp.getText().toString().compareToIgnoreCase("") == 0) {
            Toasty.error(getContext(),"Enter Name").show();
        } else if (mBinding.collectorPhoneSignUp.getText().toString().compareToIgnoreCase("") == 0){
            Toasty.error(getContext(),"Enter Phone").show();
        } else if (imageFlag==0){
            Toasty.error(getContext(),"Please Select a photo").show();
        } else {
                uploadData();
        }

    }

    private void uploadData() {

        final HashMap<String, Object> usersMap = new HashMap<>();
        storageReference = FirebaseStorage.getInstance().getReference("ProfilePhotos");

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please Wait...");
        pd.show();

        final StorageReference reference = storageReference.child(mBinding.collectorIdSignup.getText().toString() + ".jpg");
        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        pd.dismiss();
                        usersMap.put("ProfilePhoto", uri.toString());

                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference = firebaseDatabase.getReference();

                        usersMap.put("Name", mBinding.collectorNameSignUp.getText().toString());
                        usersMap.put("Phone", mBinding.collectorPhoneSignUp.getText().toString());
                        usersMap.put("Collector_ID",mBinding.collectorIdSignup.getText().toString());

                        databaseReference.child("CollectorsInfo").child(mBinding.collectorIdSignup.getText().toString())
                                .setValue(usersMap);

                        Toasty.success(getContext(),"Collector Created Successfully").show();

                        dismiss();

                    }
                });
            }
        });

    }

    private void selectImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, IMAGE_CODE);

        // Pix.start((FragmentActivity) getContext(), Options.init().setRequestCode(100));

        easyImage = new EasyImage.Builder(getContext())
                .setCopyImagesToPublicGalleryFolder(true)
                .setFolderName("SM Jewels")
                .allowMultiple(false)
                .build();

        easyImage.openChooser(CreateCollectorFragment.this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                // onPhotosReturned(imageFiles);

                imageFlag = 1;
                imageUri = Uri.fromFile(new File(imageFiles[0].getFile().toString()));
                Log.v("TAG", "ImageUri=>" + imageUri);
                mBinding.userPhotoCreateAccount.setImageURI(imageUri);
                //mBinding.userCardLayoutInclude.userPhoto.setImageURI(imageUri);

            }

            @Override
            public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
                //Some error handling
                error.printStackTrace();
            }

            @Override
            public void onCanceled(@NonNull MediaSource source) {
                //Not necessary to remove any files manually anymore
            }
        });


    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}