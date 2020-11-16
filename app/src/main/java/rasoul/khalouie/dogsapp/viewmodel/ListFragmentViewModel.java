package rasoul.khalouie.dogsapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import rasoul.khalouie.dogsapp.model.DogBreed;

public class ListFragmentViewModel extends AndroidViewModel {

    public MutableLiveData<List<DogBreed>> dogsList = new MutableLiveData<>();
    public MutableLiveData<Boolean> dogLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public ListFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh(){
        DogBreed dog1 = new DogBreed("1","Name 1" , "15 years" ,"","","","");
        DogBreed dog2 = new DogBreed("2","Name 2" , "1 years" ,"","","","");
        DogBreed dog3 = new DogBreed("3", "Name 3", "50 years", "", "", "", "");

        ArrayList<DogBreed> dogs = new ArrayList<>();
        dogs.add(dog1);
        dogs.add(dog2);
        dogs.add(dog3);

        dogsList.setValue(dogs);
        dogLoadError.setValue(false);
        isLoading.setValue(false);
    }
}
