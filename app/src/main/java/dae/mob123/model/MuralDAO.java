package dae.mob123.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MuralDAO {

    @Query("SELECT * FROM Mural ORDER BY character")
    MutableLiveData<List<Mural>> getAllMurals();

    @Query("SELECT * FROM Mural ORDER BY year ASC")
    LiveData<List<Mural>> getAllMuralsByYear();

    @Delete
    void deleteMural(Mural mural);

    @Insert
    void insertMural(Mural mural);

    @Update
    void updateMural(Mural mural);
}
