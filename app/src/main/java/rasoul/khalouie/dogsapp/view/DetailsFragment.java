package rasoul.khalouie.dogsapp.view;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.palette.graphics.Palette;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import rasoul.khalouie.dogsapp.R;
import rasoul.khalouie.dogsapp.databinding.FragmentDetailsBinding;
import rasoul.khalouie.dogsapp.databinding.LayoutSendSmsDialogBinding;
import rasoul.khalouie.dogsapp.model.DogBreed;
import rasoul.khalouie.dogsapp.model.DogPalette;
import rasoul.khalouie.dogsapp.model.SmsInfo;
import rasoul.khalouie.dogsapp.utils.Util;
import rasoul.khalouie.dogsapp.viewmodel.DetailsFragmentViewModel;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private DetailsFragmentViewModel viewModel;

    private int dogUId;
    private DogBreed currentDog;

    private Boolean sendSmsStarted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            dogUId = DetailsFragmentArgs.fromBundle(getArguments()).getDogUId();
        }

        viewModel = new ViewModelProvider(this).get(DetailsFragmentViewModel.class);
        viewModel.fetchData(dogUId);

        observerViewModel();
    }

    private void observerViewModel() {
        viewModel.dogsBreed.observe(getActivity(), dogBreed -> {
            if (dogBreed != null && dogBreed instanceof DogBreed) {
                binding.setDog(dogBreed);
                currentDog = dogBreed;
                setPaletteColor(dogBreed.getImageUrl());

            }
        });
    }

    private void setPaletteColor(String imageUrl) {
        Glide.with(this).asBitmap().load(imageUrl).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Palette.from(resource).generate(palette -> {
                    int intColor = palette.getLightMutedSwatch().getRgb();
                    DogPalette dogPalette = new DogPalette(intColor);
                    binding.setDogPalette(dogPalette);
                });
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionShare:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Check out this dog");
                intent.putExtra(Intent.EXTRA_TEXT , currentDog.getDogBreed()+"\n"+currentDog.getBredFor());
                intent.putExtra(Intent.EXTRA_STREAM,currentDog.getImageUrl());
                startActivity(Intent.createChooser(intent,"Share with : "));
                break;
            case R.id.actionSend:
                if (!sendSmsStarted) {
                    sendSmsStarted = true;
                    ((MainActivity) getActivity()).checkSmsPermission();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPermissionResult(boolean permissionGranted) {
        if (isAdded() && sendSmsStarted && permissionGranted) {
            SmsInfo smsInfo = new SmsInfo("", currentDog.getDogBreed() + "\n" + currentDog.getBredFor(), currentDog.getImageUrl());

            LayoutSendSmsDialogBinding layoutSendSmsDialogBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()),
                    R.layout.layout_send_sms_dialog,
                    null,
                    false
            );

            layoutSendSmsDialogBinding.setSmsInfo(smsInfo);

            new AlertDialog.Builder(getActivity())
                    .setView(layoutSendSmsDialogBinding.getRoot())
                    .setPositiveButton("Send", (dialogInterface, i) -> {

                        if(!layoutSendSmsDialogBinding.edtSmsDestination.getText().toString().isEmpty()){
                            smsInfo.setDestination(layoutSendSmsDialogBinding.edtSmsDestination.getText().toString());
                            sendSMS(smsInfo);
                        }

                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {}).show();

            sendSmsStarted = false;

        }
    }

    private void sendSMS(SmsInfo smsInfo) {
        Intent intent = new Intent(getContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),0,intent,0);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(smsInfo.getDestination(),null,smsInfo.getText(),pendingIntent,null);
    }
}