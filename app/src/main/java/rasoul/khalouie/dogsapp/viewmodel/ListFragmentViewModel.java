package rasoul.khalouie.dogsapp.viewmodel;

import android.app.Application;
import android.nfc.FormatException;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import rasoul.khalouie.dogsapp.model.DogBreed;
import rasoul.khalouie.dogsapp.model.DogDao;
import rasoul.khalouie.dogsapp.model.DogDatabase;
import rasoul.khalouie.dogsapp.model.DogsApiService;
import io.reactivex.Scheduler;
import rasoul.khalouie.dogsapp.utils.NotificationsHelper;
import rasoul.khalouie.dogsapp.utils.SharedPreferencesHelper;

public class ListFragmentViewModel extends AndroidViewModel {

    public MutableLiveData<List<DogBreed>> dogsList = new MutableLiveData<>();
    public MutableLiveData<Boolean> dogLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private DogsApiService dogsApiService = new DogsApiService();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private AsyncTask<List<DogBreed>, Void, List<DogBreed>> insertTask;
    private AsyncTask<Void, Void, List<DogBreed>> retrievedTask;


    private SharedPreferencesHelper preferencesHelper = SharedPreferencesHelper.getInstance(getApplication());
    private long refreshTime = 5 * 60 * 1000 * 1000 * 1000L;

    public ListFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        long updateTime = preferencesHelper.getUpdateTime();
        long currentTime = System.nanoTime();

        if (updateTime != 0 && currentTime - updateTime < refreshTime) {
            fetchFromDatabase();
        } else {
            fetchFromRemote();
        }

    }

    private void checkCacheTime() {
        String cacheTime = SharedPreferencesHelper.getInstance(getApplication()).getCachePref();

        if (cacheTime != "") {
            try {
                int cacheInt = Integer.parseInt(cacheTime);
                refreshTime = cacheInt * 1000 * 1000 * 1000L;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void refreshByPassCache() {
        fetchFromRemote();
    }

    private void fetchFromRemote() {
        isLoading.setValue(true);
        compositeDisposable.add(
                dogsApiService.getAllDogs()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                            @Override
                            public void onSuccess(List<DogBreed> value) {
                                insertTask = new InsertDogsTask();
                                insertTask.execute(value);
                                Toast.makeText(getApplication(), "Data Retrieved from Remote...", Toast.LENGTH_SHORT).show();
                                NotificationsHelper.getInstance(getApplication()).CreateNotification();
                            }

                            @Override
                            public void onError(Throwable e) {
                                dogLoadError.setValue(true);
                                isLoading.setValue(false);
                            }
                        })
        );
    }

    private void fetchFromDatabase() {
        isLoading.setValue(true);
        retrievedTask = new RetrievedDogsTask();
        retrievedTask.execute();
        Toast.makeText(getApplication(), "Data Retrieved from Database...", Toast.LENGTH_SHORT).show();

    }

    private void dogsRetrieved(List<DogBreed> list) {
        dogsList.setValue(list);
        dogLoadError.setValue(false);
        isLoading.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();

        if (insertTask != null) {
            insertTask.cancel(true);
            insertTask = null;
        }

        if (retrievedTask != null) {
            retrievedTask.cancel(true);
            retrievedTask = null;
        }
    }

    private class InsertDogsTask extends AsyncTask<List<DogBreed>, Void, List<DogBreed>> {

        @Override
        protected List<DogBreed> doInBackground(List<DogBreed>... lists) {

            List<DogBreed> list = lists[0];
            DogDao dao = DogDatabase.getInstance(getApplication()).dogDao();
            dao.deleteAllDogs();

            ArrayList<DogBreed> newList = new ArrayList<>(list);
            List<Long> results = dao.insertAllDogs(newList.toArray(new DogBreed[0]));

            int i = 0;
            while (i < results.size()) {
                list.get(i).setUuid(results.get(i).intValue());
                ++i;
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<DogBreed> list) {
            dogsRetrieved(list);
            preferencesHelper.saveUpdateTime(System.nanoTime());
        }
    }

    private class RetrievedDogsTask extends AsyncTask<Void, Void, List<DogBreed>> {

        @Override
        protected List<DogBreed> doInBackground(Void... voids) {
            return DogDatabase.getInstance(getApplication()).dogDao().getAllDogs();
        }

        @Override
        protected void onPostExecute(List<DogBreed> list) {
            dogsRetrieved(list);
        }
    }
}
