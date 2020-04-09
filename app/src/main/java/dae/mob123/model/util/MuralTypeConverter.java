package dae.mob123.model.util;

import androidx.room.TypeConverter;

public class MuralTypeConverter {

    @TypeConverter
    public static MuralType stringToMuralType(String typeAsString){
        MuralType muralTypeAsEnum = MuralType.valueOf(typeAsString);
        return muralTypeAsEnum;
    }

    @TypeConverter
    public static String muralTypeToString(MuralType typeAsEnum){
        String muralTypeAsString = typeAsEnum.name();
        return muralTypeAsString;
    }
}
