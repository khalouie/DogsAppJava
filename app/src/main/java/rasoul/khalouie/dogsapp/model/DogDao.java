package rasoul.khalouie.dogsapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DogDao {

    @Insert
    List<Long> insertAllDogs(DogBreed ... dogBreeds);

    @Query("SELECT * FROM dogbreed")
    List<DogBreed> getAllDogs();

    @Query("SELECT * FROM dogbreed where uuid = :uuid")
    DogBreed getDog (int uuid);

    @Query("DELETE FROM dogbreed")
    void deleteAllDogs();
}
