package tools.msm.b_tracking_list;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import tools.msm.b_tracking_list.domain.MsmElement;
import tools.msm.b_tracking_list.domain.MsmElementRepository;
import tools.msm.b_tracking_list.domain.MsmIsland;
import tools.msm.b_tracking_list.domain.MsmIslandRepository;
import tools.msm.b_tracking_list.domain.MsmMonster;

public class MsmMonsterDeserializer implements JsonDeserializer<MsmMonster> {

    private final MsmElementRepository eR;
    private final MsmIslandRepository iR;

    public MsmMonsterDeserializer(MsmElementRepository er, MsmIslandRepository ir) {
        this.eR = er;
        this.iR = ir;
    }

    @Override
    public MsmMonster deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonName = jsonObject.get("nameM");
        JsonElement jsonDesc = jsonObject.get("description");
        JsonArray jsonBTimes = jsonObject.getAsJsonArray("birthTimes");
        JsonArray jsonIslands = jsonObject.getAsJsonArray("islandsM");
        JsonArray jsonElements = jsonObject.getAsJsonArray("elementsM");

        ArrayList<Duration> bTimes = new ArrayList<Duration>();
        ArrayList<MsmIsland> iArrayList = new ArrayList<MsmIsland>();
        ArrayList<MsmElement> eArrayList = new ArrayList<MsmElement>();

        if (jsonElements != null) {
            for (int i = 0; i < jsonElements.size(); i++) {
                eArrayList.add(
                    eR.findByNameE(jsonElements.get(i).getAsString())
                );
            }
        }

        if (jsonIslands != null) {
            for (int i = 0; i < jsonIslands.size(); i++) {
                iArrayList.add(
                    iR.findByNameI(jsonIslands.get(i).getAsString())
                );
            }
        }

        if (jsonBTimes != null) {
            for (int i = 0; i < jsonBTimes.size(); i++) {
                bTimes.add(Duration.parse(jsonBTimes.get(i).getAsString())
                );
            }
        }
        
        MsmMonster monster = new MsmMonster(
            jsonName.getAsString(), 
            jsonDesc.getAsString(), 
            iArrayList,
            eArrayList,
            bTimes
            );
        return monster;
    }

}
