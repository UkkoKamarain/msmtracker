package tools.msm.b_tracking_list;

import java.lang.reflect.Type;
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

public class MsmIslandDeserializer implements JsonDeserializer<MsmIsland> {

    private final MsmElementRepository eR;

    public MsmIslandDeserializer(MsmElementRepository er) {
        this.eR = er;
    }

    @Override
    public MsmIsland deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonName = jsonObject.get("nameI");
        JsonElement jsonHatch = jsonObject.get("hatcherTier");
        JsonElement jsonSeasonal = jsonObject.get("seasonal");
        JsonArray jsonElements = jsonObject.getAsJsonArray("elementsI");

        ArrayList<MsmElement> eArrayList = new ArrayList<MsmElement>();

        if (jsonElements != null) {
            for (int i = 0; i < jsonElements.size(); i++) {
                eArrayList.add(
                    eR.findByNameE(jsonElements.get(i).getAsString())
                );
            }
        }

        MsmIsland island = new MsmIsland(
            jsonName.getAsString(),
            jsonHatch.getAsByte(),
            jsonSeasonal.getAsBoolean(),
            eArrayList
        );

        return island;

    }

}
