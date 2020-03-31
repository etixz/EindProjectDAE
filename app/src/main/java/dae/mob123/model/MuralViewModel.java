package dae.mob123.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

public class MuralViewModel extends ViewModel {

    private ArrayList<Mural> murals;
    public ExecutorService threadExecutor = Executors.newFixedThreadPool(4);

    public MuralViewModel() {
    this.murals = new ArrayList<>();
    }

    //Opvragen mural list
    public ArrayList<Mural> getMurals(){
        fetchMurals();
        return murals;
    }

    //Data binnenhalen van de web service!
    //Alle murals fetchen
    private void fetchMurals(){
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://bruxellesdata.opendatasoft.com/api/records/1.0/search/?dataset=comic-book-route&rows=58")
                        .get()
                        .build();

                try{
                    Response response = client.newCall(request).execute();
                    String json = response.body().string();
                    JSONArray jsonArray= new JSONArray(json);

                    int arraySize = jsonArray.length();
                    int i = 0;

                    while(i < arraySize){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Mural currentMural = new Mural(
                                jsonObject.getString("auteur_s"),
                                jsonObject.getString("filename"),
                                jsonObject.getString("personnage_s"),
                                jsonObject.getString("annee"), new LatLng(jsonObject.getJSONArray("coordonnees_geographiques").getDouble(0),
                                                                                 jsonObject.getJSONArray("coordonnees_geographiques").getDouble(1))
                        );
                        murals.add(currentMural);
                        i++;
                    }

                } catch (IOException e){
                    e.printStackTrace();
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
