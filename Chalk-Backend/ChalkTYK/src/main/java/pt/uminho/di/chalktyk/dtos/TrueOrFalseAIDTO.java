package pt.uminho.di.chalktyk.dtos;

import javax.json.JsonArray;
import javax.json.JsonObject;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TrueOrFalseAIDTO
 */
@Data
@AllArgsConstructor
public class TrueOrFalseAIDTO {

    private TrueOrFalse[] list;

    public TrueOrFalseAIDTO(JsonArray jList) {
        TrueOrFalse[] list = new TrueOrFalse[jList.size()];

        for(int i = 0; i < jList.size();i++){
            JsonObject obj = jList.getJsonObject(i); 
            list[i] = new TrueOrFalse(obj.getString("Question"), obj.getBoolean("True"));
        }
    }

    @AllArgsConstructor
    private class TrueOrFalse {
        
        private String question;
        private boolean correct;
        
    }
}
