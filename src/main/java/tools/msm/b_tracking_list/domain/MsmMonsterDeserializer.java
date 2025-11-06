package tools.msm.b_tracking_list.domain;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Deserializes MsmMonster from JSON, resolving islands/elements from repositories.
 * Will NOT create islands/elements — it throws if a referenced name is missing.
 * 
 * Made with AI (GPT-5 mini)
 */
public class MsmMonsterDeserializer extends StdDeserializer<MsmMonster> {

    private final MsmIslandRepository islandRepo;
    private final MsmElementRepository elementRepo;

    public MsmMonsterDeserializer(MsmIslandRepository islandRepo, MsmElementRepository elementRepo) {
        super(MsmMonster.class);
        this.islandRepo = islandRepo;
        this.elementRepo = elementRepo;
    }

    @Override
    public MsmMonster deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        MsmMonster monster = new MsmMonster();
        monster.setNameM(node.path("nameM").asText(null));
        monster.setDescription(node.path("description").asText(null));

        // parse birthTimes
        JsonNode btNode = node.get("birthTimes");
        if (btNode != null && btNode.isArray()) {
            List<Duration> bts = new ArrayList<>();
            for (JsonNode t : btNode) {
                try {
                    bts.add(Duration.parse(t.asText()));
                } catch (Exception ex) {
                    // fallback: 0 duration on parse error
                    bts.add(Duration.ofSeconds(0));
                }
            }
            monster.setBirthTimes(bts);
        }

        // parse island name list 
        JsonNode islandsNode = node.get("islandsM");
        if (islandsNode != null && islandsNode.isArray()) {
            List<MsmIsland> islands = new ArrayList<>();
            for (JsonNode nameNode : islandsNode) {
                String name = nameNode.asText();
                if (name == null || name.isBlank()) continue;
                Optional<MsmIsland> opt = islandRepo.findByNameI(name);
                if (opt.isEmpty()) {
                    throw JsonMappingException.from(p,
                        "Island '" + name + "' referenced by monster '" + monster.getNameM()
                        + "' not found. Make sure islands.json is loaded before monsters.json.");
                }
                islands.add(opt.get());
            }
            monster.setIslandsM(islands);
        }

        // parse element name list
        JsonNode elementsNode = node.get("elementsM");
        if (elementsNode != null && elementsNode.isArray()) {
            List<MsmElement> elements = new ArrayList<>();
            for (JsonNode nameNode : elementsNode) {
                String name = nameNode.asText();
                if (name == null || name.isBlank()) continue;
                Optional<MsmElement> opt = elementRepo.findByNameE(name);
                if (opt.isEmpty()) {
                    throw JsonMappingException.from(p,
                        "Element '" + name + "' referenced by monster '" + monster.getNameM()
                        + "' not found. Make sure elements.json is loaded before monsters.json.");
                }
                elements.add(opt.get());
            }
            monster.setElementsM(elements);
        }

        return monster;
    }
}