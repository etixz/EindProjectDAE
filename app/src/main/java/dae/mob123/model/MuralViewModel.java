package dae.mob123.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dae.mob123.model.util.MuralType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
ViewModel class fetches data from web service using Okhttp library and stores it locally using Room (therefore must inherit from AndroidViewModel).
It also provides methods to access the data by accessing the Room DAO.
*/
public class MuralViewModel extends AndroidViewModel {

    private MutableLiveData<List<Mural>> murals;
    private ArrayList<Mural> muralArrayList = new ArrayList<>();
    private MuralDatabase database;
    private final Application mApplication;
    public ExecutorService threadExecutor = Executors.newFixedThreadPool(4);

    /*Constructor must include the context*/
    public MuralViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        database = MuralDatabase.getInstance(application);

        this.murals = new MutableLiveData<>();
        //murals = (MutableLiveData<List<Mural>>) database.getRepoDao().getAllMurals();
    }

    /*Method to get a list with all the Murals as Live Data*/
    public MutableLiveData<List<Mural>> getMurals(){
        fetchAllMurals();
        return murals;
    }

    /*Consume the restAPI and deserialise data from JSON objects to Java objects by running in a thread in the background*/
    private void fetchAllMurals(){
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                /*Using the classes provided by Okhttp, make a GET request to access the JSON data*/
                Request requestComicBookMurals = new Request.Builder()
                        .url("https://bruxellesdata.opendatasoft.com/api/records/1.0/search/?dataset=comic-book-route&rows=58")
                        .get()
                        .build();

                try{
                    Response responseComicBookMurals = client.newCall(requestComicBookMurals).execute();
                    String jsonResponseComicBookMurals = responseComicBookMurals.body().string();

                    /*Response is a JSON object containing a JSON array of objects representing the Mural class */
                    JSONObject jsonComicBookMuralsObject = new JSONObject(jsonResponseComicBookMurals);
                    JSONArray jsonComicBookMuralsArray = jsonComicBookMuralsObject.getJSONArray("records");


                    /*Traverse the JSON array and get the objects representing Mural class */
                    for (int i = 0; i < jsonComicBookMuralsArray.length(); i++){
                        String jsonComicBookMuralID = jsonComicBookMuralsArray.getJSONObject(i).getString("recordid");
                        JSONObject jsonComicBookMural = jsonComicBookMuralsArray.getJSONObject(i).getJSONObject("fields");
                        /*If object and nested objects contain the key:value pairs that correspond to the fields in Mural class, pass them as arguments in constructor of a new Murals instance.*/
                        final Mural currentComicBookMural = new Mural(
                                MuralType.COMIC_BOOK,
                                jsonComicBookMuralID,
                                (jsonComicBookMural.has("auteur_s")) ? jsonComicBookMural.getString("auteur_s") : "Unknown author",
                                (jsonComicBookMural.has("photo")) ? jsonComicBookMural.getJSONObject("photo").getString("id") : "No picture available!",
                                (jsonComicBookMural.has("personnage_s")) ? jsonComicBookMural.getString("personnage_s") : "Unknown character",
                                (jsonComicBookMural.has("annee")) ? jsonComicBookMural.getString("annee") : "year unknown",
                                new LatLng(jsonComicBookMural.getJSONArray("coordonnees_geographiques").getDouble(0),
                                           jsonComicBookMural.getJSONArray("coordonnees_geographiques").getDouble(1))
                        );
                        /*If the Mural object does not exist in the database, store it in database*/
                        MuralDatabase.databaseWriteExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                if(findMuralByID(currentComicBookMural.getMuralID()) == null){
                                    insertMural(currentComicBookMural);
                                }
                            }
                        });
                        /*Add the Mural object to an ArrayList*/
                        muralArrayList.add(currentComicBookMural);
                    }

                    /*Convert the ArrayList containing all the instances of Mural to MutableLiveData*/
                    murals.postValue(muralArrayList);

                } catch (IOException e){
                    e.printStackTrace();
                } catch (JSONException e){
                    e.printStackTrace();
                }

                Request requestStreetArt = new Request.Builder()
                        .url("https://opendata.brussel.be/api/records/1.0/search/?dataset=street-art&rows=24")
                        .get()
                        .build();

                try {
                    Response responseStreetArt = client.newCall(requestStreetArt).execute();
                    String jsonResponseStreetArt = responseStreetArt.body().string();
                    JSONObject jsonStreetArtObject = new JSONObject((jsonResponseStreetArt));
                    JSONArray jsonStreetArtArray = jsonStreetArtObject.getJSONArray("records");

                    for (int j = 0; j < jsonStreetArtArray.length(); j++){
                        String jsonStreetArtID = jsonStreetArtArray.getJSONObject(j).getString("recordid");
                        JSONObject jsonStreetArt = jsonStreetArtArray.getJSONObject(j).getJSONObject("fields");

                        final Mural currentStreetArt = new Mural(
                                MuralType.STREET_ART,
                                jsonStreetArtID,
                                (jsonStreetArt.has("name_of_the_artist")) ? jsonStreetArt.getString("name_of_the_artist") : "Anonymous",
                                (jsonStreetArt.has("photo")) ? jsonStreetArt.getJSONObject("photo").getString("id") : "No picture evailable",
                                (jsonStreetArt.has("name_of_the_work")) ? jsonStreetArt.getString("name_of_the_work") : "Untitled",
                                (jsonStreetArt.has("annee")) ? jsonStreetArt.getString("annee") : "year unknown",
                                new LatLng(jsonStreetArt.getJSONArray("geocoordinates").getDouble(0),
                                           jsonStreetArt.getJSONArray("geocoordinates").getDouble(1))
                        );

                        MuralDatabase.databaseWriteExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                if(findMuralByID(currentStreetArt.getMuralID()) == null){
                                    insertMural(currentStreetArt);
                                }
                            }
                        });
                        muralArrayList.add(currentStreetArt);
                    }
                    murals.postValue(muralArrayList);
                }

                catch (IOException e){
                    e.printStackTrace();
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public Mural findMuralByID(String id){
        return MuralDatabase.getInstance(getApplication()).getRepoDao().findMuralByID(id);
    }

    public void insertMural( Mural mural){
        MuralDatabase.getInstance(getApplication()).getRepoDao().insertMural(mural);
    }
//
//    public void updateMural(Mural mural){
//        MuralDatabase.getInstance(getApplication()).getRepoDao().updateMural(mural);
//    }
//
//    public void deleteMural(Mural mural){
//        MuralDatabase.getInstance(getApplication()).getRepoDao().deleteMural(mural);
//    }
}
