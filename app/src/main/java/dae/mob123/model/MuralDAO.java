package dae.mob123.model;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/*
Author: EB
Data Access Object that uses the Room package to make queries to the local database
*/
@Dao
public interface MuralDAO {

    @Query("SELECT * FROM Mural ORDER BY character")
    List<Mural> getAllMurals();

    @Query("SELECT * FROM Mural ORDER BY artist")
    List<Mural> getAllMuralsOrderByArtist();

    @Query("SELECT * FROM Mural WHERE muralID LIKE:muralID")
    Mural findMuralByID(String muralID);

    @Insert
    void insertMural(Mural m);

// is het wel nodig om Stripuur te verwijderen of updaten?
//    @Update
//    void updateMural(Mural m);

//    @Delete
//    void deleteMural(Mural m);
//
}
