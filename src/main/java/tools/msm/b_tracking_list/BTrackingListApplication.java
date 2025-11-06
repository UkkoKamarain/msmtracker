package tools.msm.b_tracking_list;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import tools.msm.b_tracking_list.domain.MsmElement;
import tools.msm.b_tracking_list.domain.MsmElementRepository;
import tools.msm.b_tracking_list.domain.MsmIsland;
import tools.msm.b_tracking_list.domain.MsmIslandDeserializer;
import tools.msm.b_tracking_list.domain.MsmIslandRepository;
import tools.msm.b_tracking_list.domain.MsmMonster;
import tools.msm.b_tracking_list.domain.MsmMonsterDeserializer;
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
			MsmElementRepository eR) {

		return (args) -> {

			// Logins
			log.info("Creating users.");
			MsmUser user1 = new MsmUser("user", "ea/u5KKXV6EVyZKETumONM7p5hqQvRqcfNNj/Z5513OS2E.ZCoC", "USER");
			MsmUser user2 = new MsmUser("admin", "$2a$12$N3BS0FKBUnzdg80TcsXKr.3k3AQlx3zwcmNA8T7z7d/gILLQF8KTi",
					"ADMIN");

			log.info("Inserting users.");
			uR.save(user1);
			uR.save(user2);
			log.info("Users inserted.");

			ObjectMapper mapper = new ObjectMapper();
			mapper.findAndRegisterModules();

			SimpleModule monsterModule = new SimpleModule();
			monsterModule.addDeserializer(
					MsmMonster.class,
					new MsmMonsterDeserializer(iR, eR));
			mapper.registerModule(monsterModule);

			SimpleModule islandModule = new SimpleModule();
			islandModule.addDeserializer(
					MsmIsland.class,
					new MsmIslandDeserializer(eR));
			mapper.registerModule(islandModule);

			File elementFile = new File("./src/main/resources/elements.json");
			File islandFile = new File("./src/main/resources/islands.json");
			File monsterFile = new File("./src/main/resources/monsters.json");

			log.info("Trying to parse files:");
			try {
				// ELEMENTS
				log.info("Parsing elements.");
				List<MsmElement> eList = mapper.readValue(elementFile, new TypeReference<List<MsmElement>>() {
				});
				log.info("Elements parsed.");

				log.info("Inserting elements.");
				for (int i = 0; i < eList.size(); i++) {
					eR.save(eList.get(i));
				}
				log.info("Elements inserted.");

				// ISLANDS
				log.info("Parsing islands.");
				List<MsmIsland> iList = mapper.readValue(islandFile, new TypeReference<List<MsmIsland>>() {
				});
				log.info("Islands parsed.");

				log.info("Inserting islands.");
				for (int i = 0; i < iList.size(); i++) {
					iR.save(iList.get(i));
				}
				log.info("Islands Inserted.");

				// MONSTERS
				log.info("Parsing monsters.");
				List<MsmMonster> mList = mapper.readValue(monsterFile, new TypeReference<List<MsmMonster>>() {
				});
				log.info("Monsters parsed.");

				log.info("Inserting monsters.");
				for (int i = 0; i < mList.size(); i++) {
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
