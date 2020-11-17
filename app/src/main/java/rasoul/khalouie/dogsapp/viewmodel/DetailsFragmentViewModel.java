package rasoul.khalouie.dogsapp.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import rasoul.khalouie.dogsapp.model.DogBreed;
import rasoul.khalouie.dogsapp.model.DogDatabase;

public class DetailsFragmentViewModel extends AndroidViewModel {

    public MutableLiveData<DogBreed> dogsBreed = new MutableLiveData<>();

    private RetrievedDogTask task;

    public DetailsFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetchData(int uuId){
        task = new RetrievedDogTask();
        task.execute(uuId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (task != null){
            task.cancel(true);
            task = null;
        }
    }

    private class RetrievedDogTask extends AsyncTask<Integer,Void,DogBreed>{

        @Override
        protected DogBreed doInBackground(Integer... integers) {
            int uuId = integers[0];
            return DogDatabase.getInstance(getApplication()).dogDao().getDog(uuId);
        }

        @Override
        protected void onPostExecute(DogBreed dog) {
            dogsBreed.setValue(dog);
        }
    }
}
