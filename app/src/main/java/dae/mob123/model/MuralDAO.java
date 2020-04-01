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
    List<Mural> getAllMurals();

    @Query("SELECT * FROM Mural WHERE artworkID LIKE:artworkID")
    Mural findMuralByID(String artworkID);

    @Query("SELECT * FROM Mural ORDER BY year ASC")
    List<Mural> getAllMuralsByYear();

    @Delete
    void deleteMural(Mural m);

    @Insert
    void insertMural(Mural m);

    @Update
    void updateMural(Mural m);
}
