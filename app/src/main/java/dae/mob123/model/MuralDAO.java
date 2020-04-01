package dae.mob123.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MuralDAO {

    @Query("SELECT * FROM Mural ORDER BY character")
    List<Mural> getAllMurals();

    @Query("SELECT * FROM Mural WHERE artworkID LIKE:artworkID")
    Mural findMuralByID(String artworkID);

    @Query("SELECT * FROM Mural ORDER BY year ASC")
    List<Mural> getAllMuralsByYear();

    @Query("SELECT * FROM Mural ORDER BY artist ASC")
    LiveData<List<Mural>> getAllMuralsByYear();

    @Delete
    void deleteMural(Mural m);

    //is insert functie wel nodig? gebruiker mag niet zomaar nieuwe muur toevoegen
    @Insert
    void insertMural(Mural m);

    @Update
    void updateMural(Mural m);
}
