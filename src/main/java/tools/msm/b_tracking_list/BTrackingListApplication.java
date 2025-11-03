package tools.msm.b_tracking_list;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;

import javax.swing.text.html.parser.Element;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import tools.msm.b_tracking_list.domain.MsmElement;
import tools.msm.b_tracking_list.domain.MsmElementRepository;
import tools.msm.b_tracking_list.domain.MsmIsland;
import tools.msm.b_tracking_list.domain.MsmIslandRepository;
import tools.msm.b_tracking_list.domain.MsmMonster;
import tools.msm.b_tracking_list.domain.MsmMonsterRepository;
import tools.msm.b_tracking_list.domain.MsmUser;
import tools.msm.b_tracking_list.domain.MsmUserRepository;

@SpringBootApplication
public class BTrackingListApplication {

	private final MsmUserRepository uR;
	private static final Logger log = LoggerFactory.getLogger(BTrackingListApplication.class);

	BTrackingListApplication(MsmUserRepository ur) {
		this.uR = ur;
	}

	public static void main(String[] args) {
		SpringApplication.run(BTrackingListApplication.class, args);
	}

	@Bean
	public CommandLineRunner msmTrackerRunner(
		MsmMonsterRepository mR,
		MsmIslandRepository iR,
		MsmElementRepository eR
	) {
		
		return (args) -> {
		
			// // CREATING SAMPLE ELEMENTS (placeholder)
			// MsmElement stone = new MsmElement("Stone");
			// MsmElement water = new MsmElement("Water");
			// MsmElement cold = new MsmElement("Cold");
			// MsmElement plant = new MsmElement("Plant");
			// MsmElement air = new MsmElement("Air");

			// // Islands
			// MsmIsland plantIsland = new MsmIsland("Plant", Arrays.asList(stone,water,cold,plant));
			// MsmIsland coldIsland = new MsmIsland("Cold", Arrays.asList(air,water,cold,plant));

			// // Monsters
			// MsmMonster noggin = new MsmMonster("Noggin",
			// "Noggin + Quad",
			// Arrays.asList(plantIsland),
			// Arrays.asList(stone),
			// Arrays.asList(Duration.ofSeconds(5),Duration.ofSeconds(3),Duration.ofSeconds(4),Duration.ofSeconds(3)));
			// MsmMonster mammott = new MsmMonster("Mammott",
			// "Mammott + Quad",
			// Arrays.asList(plantIsland,coldIsland),
			// Arrays.asList(cold),
			// Arrays.asList(Duration.parse("PT2M"),Duration.parse("PT1M30S"),Duration.parse("PT1M48S"),Duration.parse("PT1M18S")));

			// Logins
			log.info("Creating users.");
			MsmUser user1 = new MsmUser("user", "ea/u5KKXV6EVyZKETumONM7p5hqQvRqcfNNj/Z5513OS2E.ZCoC", "USER");
			MsmUser user2 = new MsmUser("admin", "$2a$12$N3BS0FKBUnzdg80TcsXKr.3k3AQlx3zwcmNA8T7z7d/gILLQF8KTi","ADMIN");

			log.info("Inserting users.");
			uR.save(user1);
			uR.save(user2);	
			log.info("Users inserted.");

			log.info("Creating Gsons.");
			Gson iGson = new GsonBuilder()
			.registerTypeAdapter(MsmIsland.class,new MsmIslandDeserializer(eR))
			.create();
			Gson mGson = new GsonBuilder()
			.registerTypeAdapter(MsmIsland.class,new MsmMonsterDeserializer(eR, iR))
			.create();
			Gson gson = new Gson();
			log.info("Gsons created.");

			log.info("Trying to parse files:");
			try {
				log.info("Reading elements.");
				JsonReader eReader = new JsonReader(new FileReader("b_tracking_list/src/main/resources/elements.json"));
				log.info("Parsing elements.");
				List<MsmElement> eList = gson.fromJson(eReader, MsmElement[].class);
				eReader.close();
				log.info("Elements parsed.");

				log.info("Inserting elements.");
				for (int i = 0; i < eList.size(); i++) {
					eR.save(eList.get(i));
				}
				log.info("Elements inserted.");

				log.info("Reading islands.");
				JsonReader iReader = new JsonReader(new FileReader("b_tracking-list/src/main/resources/islands.json"));
				log.info("Parsing islands.");
				List<MsmIsland> iList = iGson.fromJson(iReader, MsmIsland[].class);
				iReader.close();
				log.info("Islands parsed.");

				log.info("Inserting islands.");
				for (int i = 0; i < eList.size(); i++) {
					iR.save(iList.get(i));
				}
				log.info("Islands Inserted.");
				
				log.info("Reading monsters.");
				JsonReader mReader = new JsonReader(new FileReader("b_tracking_list/src/main/resources/static/monsters.json"));
				log.info("Parsing monsters.");
				List<MsmMonster> mList = mGson.fromJson(mReader, MsmMonster[].class);
				mReader.close();
				log.info("Monsters parsed.");

				log.info("Inserting monsters.");
				for (int i = 0; i < eList.size(); i++) {
					mR.save(mList.get(i));
				}
				log.info("Monsters inserted.");

				log.info("Full success.");

			} catch (FileNotFoundException e) {
				System.out.println(e);
				log.info("Error: FileNotFound.");
			} catch (IOException e) {
				System.out.println(e);
				log.info("Error: IO.");
			} 

			log.info("Log end.");
		};

	}
}
