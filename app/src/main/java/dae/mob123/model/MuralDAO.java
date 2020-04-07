package dae.mob123.model;


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

    @Query("SELECT * FROM Mural ORDER BY artist")
    List<Mural> getAllMuralsOrderByArtist();

    @Query("SELECT * FROM Mural WHERE muralID LIKE:muralID")
    Mural findMuralByID(String muralID);

    @Delete
    void deleteMural(Mural m);

    //is insert functie wel nodig? gebruiker mag niet zomaar nieuwe muur toevoegen
    @Insert
    void insertMural(Mural m);

    @Update
    void updateMural(Mural m);
}
