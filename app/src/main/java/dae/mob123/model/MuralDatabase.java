package dae.mob123.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dae.mob123.model.util.Converters;


@Database(entities = {Mural.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MuralDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "murals.db";
    private static MuralDatabase instance;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static MuralDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context,
                    MuralDatabase.class,
                    DATABASE_NAME).build();
        }
        return instance;
    }

    public abstract MuralDAO getRepoDao();
}
