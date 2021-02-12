package in.savitar.smjewelsadmin.mvp.ui.Dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;

import net.glxn.qrgen.android.QRCode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentUserCardBinding;


public class UserCardFragment extends Fragment implements MainActivityContract.View {

    FragmentUserCardBinding mBinding;
    MainActivityPresenter mPresenter;


    public UserCardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_card,container,false);
        mPresenter = new MainActivityPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {
        generateQRCode("202008001");

//        getActivity().setRequestedOrientation(
//                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mBinding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareCard();
            }
        });
    }

    private void shareCard() {

        File mediaStorageDir = getContext().getExternalFilesDir(null);
        File folder = new File(mediaStorageDir, "pdf");
        folder.mkdir();

        PdfGenerator.getBuilder()
                .setContext(getContext())
                .fromViewIDSource()
                .fromViewID(getActivity(),R.id.userCardLayout)
                .setCustomPageSize(3000,2000)
                .setFileName("Test-PDF")
                .setFolderName("pdf")
                .build(new PdfGeneratorListener() {
                    @Override
                    public void onSuccess(SuccessResponse response) {
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        Uri screenshotUri = Uri.parse(response.getPath());
                        sharingIntent.setType("*/*");
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));
                    }

                    @Override
                    public void showLog(String log) {
                        super.showLog(log);
                        Log.v("TAG","QRCODE Log=>"+log);
                    }

                    @Override
                    public void onFailure(FailureResponse failureResponse) {
                        Log.v("TAG","QRCODE Log=>"+failureResponse.getErrorMessage());
                    }
                });

    }

    private void generateQRCode(String id) {
        Bitmap myBitmap = QRCode.from(id).withSize(200,200).bitmap();
        mBinding.userCode.setImageBitmap(myBitmap);
    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}