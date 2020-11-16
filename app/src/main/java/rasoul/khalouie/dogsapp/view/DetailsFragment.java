package rasoul.khalouie.dogsapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import rasoul.khalouie.dogsapp.R;
import rasoul.khalouie.dogsapp.databinding.FragmentDetailsBinding;
import rasoul.khalouie.dogsapp.model.DogBreed;
import rasoul.khalouie.dogsapp.viewmodel.DetailsFragmentViewModel;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private DetailsFragmentViewModel viewModel;

    private int dogUId;

    private ImageView dogImage;
    private TextView txtDogName , txtDogPurpose , txtDogTemperament , txtDogLifeSpan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        dogImage = binding.imgDogImage;
        txtDogName = binding.txtDogName;
        txtDogPurpose = binding.txtDogPurpose;
        txtDogTemperament = binding.txtDogTemperament;
        txtDogLifeSpan = binding.txtDogLifespan;
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null){
            dogUId = DetailsFragmentArgs.fromBundle(getArguments()).getDogUId();
        }

        viewModel = new ViewModelProvider(this).get(DetailsFragmentViewModel.class);
        viewModel.fetchData();

        observerViewModel();
    }

    private void observerViewModel() {
        viewModel.dogsBreed.observe(getActivity(), dogBreed -> {
            if(dogBreed != null && dogBreed instanceof DogBreed){
                txtDogName.setText(dogBreed.getDogBreed());
                txtDogPurpose.setText(dogBreed.getBredFor());
                txtDogTemperament.setText(dogBreed.getTemperament());
                txtDogLifeSpan.setText(dogBreed.getLifeSpan());
            }
        });
    }

}