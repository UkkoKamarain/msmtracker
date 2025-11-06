package tools.msm.b_tracking_list.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Deserializes MsmIsland from JSON.
 * Made following the example of MsmMonsterDeserializer.
 */
public class MsmIslandDeserializer extends StdDeserializer<MsmIsland> {
    private final MsmElementRepository eR;

    public MsmIslandDeserializer(MsmElementRepository er) {
        super(MsmIsland.class);
        this.eR = er;
    }

    @Override
    public MsmIsland deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        MsmIsland island = new MsmIsland();
        island.setNameI(node.path("nameI").asText());
        island.setHatcherTier(((byte)node.path("hatcherTier").asInt()));
        island.setSeasonal(node.path("seasonal").asBoolean());

        // parse element name list
        JsonNode elementsNode = node.get("elementsI");
        if (elementsNode != null && elementsNode.isArray()) {
            List<MsmElement> elements = new ArrayList<>();
            for (JsonNode nameNode : elementsNode) {
                String name = nameNode.asText();
                if (name == null || name.isBlank()) {
                    continue;
                }
                Optional<MsmElement> opt = eR.findByNameE(name);
                if (opt.isEmpty()) {
                    throw JsonMappingException.from(p,
                        "Element '" + name + "' referenced by island '" + island.getNameI()
                        + "' not found. Make sure elements.json is loaded before islands.json.");
                }

                elements.add(opt.get());
            }
            island.setElementsI(elements);
        }

        return island;
    }
}
