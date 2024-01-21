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
        list = new TrueOrFalse[jList.size()];

        System.out.println("Lista = " + jList.toString());

        for(int i = 0; i < jList.size();i++){
            JsonObject obj = jList.getJsonObject(i); 
            System.out.println(":" + obj.getString("Question") + " " + obj.getBoolean("True")); 
            list[i] = new TrueOrFalse(obj.getString("Question"), obj.getBoolean("True"));
        }
    }

    @Data
    @AllArgsConstructor
    private class TrueOrFalse {
        
        private String question;
        private boolean correct;
        
    }
}
