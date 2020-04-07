package dae.mob123.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import android.util.Log;


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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
ViewModel class fetches data from web service using Okhttp library and stores it locally using Room (therefore must inherit from AndroidViewModel).
It also provides methods to access the data by accessing the Room DAO.
*/
public class MuralViewModel extends AndroidViewModel {

    private MutableLiveData<List<Mural>> murals;
    private ArrayList<Mural> muralArrayList;
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
        fetchMurals();
        return murals;
    }

    /*Consume the restAPI and deserialise data from JSON objects to Java objects by running in a thread in the background*/
    private void fetchMurals(){
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                /*Using the classes provided by Okhttp, make a GET request to access the JSON data*/
                Request request = new Request.Builder()
                        .url("https://bruxellesdata.opendatasoft.com/api/records/1.0/search/?dataset=comic-book-route&rows=58")
                        .get()
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    String json = response.body().string();
                    /*Response is a JSON object containing a JSON array of objects representing the Mural class */
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonRecordsArray = jsonObject.getJSONArray("records");


                    /*Traverse the JSON array and get the objects representing Mural class */
                    muralArrayList = new ArrayList<>();
                    int arraySize = jsonRecordsArray.length();
                    int i = 0;
                    while(i < arraySize){
                        String jsonMuralID = jsonRecordsArray.getJSONObject(i).getString("recordid");
                        JSONObject jsonMural = jsonRecordsArray.getJSONObject(i).getJSONObject("fields");
                        /*If object and nested objects contain the key:value pairs that correspond to the fields in Mural class, pass them as arguments in constructor of a new Murals instance.*/
                        final Mural currentMural = new Mural(
                                jsonMuralID,
                                (jsonMural.has("auteur_s")) ? jsonMural.getString("auteur_s") : "Unknown author",
                                (jsonMural.has("photo")) ? jsonMural.getJSONObject("photo").getString("id") : "No picture available!",
                                (jsonMural.has("personnage_s")) ? jsonMural.getString("personnage_s") : "Unknown character",
                                (jsonMural.has("annee")) ? jsonMural.getString("annee") : "Unknown year of creation",
                                new LatLng(jsonMural.getJSONArray("coordonnees_geographiques").getDouble(0),
                                           jsonMural.getJSONArray("coordonnees_geographiques").getDouble(1))
                        );
                        /*If the Mural object does not exist in the database, store it in database*/
                        MuralDatabase.databaseWriteExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                if(findMuralByID(currentMural.getMuralID()) == null){
                                    insertMural(currentMural);
                                }
                            }
                        });
                        /*Add the Mural object to an ArrayList*/
                        muralArrayList.add(currentMural);
                        i++;
                    }

                    for(Mural mural : muralArrayList){
                        Log.d("Fetched JSON Data:", "" + mural);
                    }
                    /*Convert the ArrayList containing all the instances of Mural to MutableLiveData*/
                    murals.postValue(muralArrayList);

                } catch (IOException e){
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
