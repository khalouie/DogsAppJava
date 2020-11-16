package rasoul.khalouie.dogsapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import rasoul.khalouie.dogsapp.model.DogBreed;

public class DetailsFragmentViewModel extends AndroidViewModel {

    public MutableLiveData<DogBreed> dogsBreed = new MutableLiveData<>();

    public DetailsFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetchData(){
        DogBreed dog = new DogBreed("11", "Ghadrenjooni", "100 years", "wild", "d", "10 degrees", "");
        dogsBreed.setValue(dog);
    }
}
