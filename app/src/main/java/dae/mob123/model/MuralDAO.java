package dae.mob123.model;

import java.util.ArrayList;

public class MuralDAO {

    private ArrayList<Mural> murals;
    public static final MuralDAO INSTANCE = new MuralDAO();

    private MuralDAO(){
    }

    public ArrayList<Mural> getMurals(){
        if (murals == null){
            murals = new ArrayList<>();
        }
        return murals;
    }
}
