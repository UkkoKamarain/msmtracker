package tools.msm.b_tracking_list;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
		// CREATING SAMPLE ELEMENTS (placeholder)
		MsmElement stone = new MsmElement("Stone");
		MsmElement water = new MsmElement("Water");
		MsmElement cold = new MsmElement("Cold");
		MsmElement plant = new MsmElement("Plant");
		MsmElement air = new MsmElement("Air");


		// Islands
		MsmIsland plantIsland = new MsmIsland("Plant", Arrays.asList(stone,water,cold,plant));
		MsmIsland coldIsland = new MsmIsland("Cold", Arrays.asList(air,water,cold,plant));

		// Monsters
		MsmMonster noggin = new MsmMonster("Noggin",
		"Noggin + Quad",
		Arrays.asList(plantIsland),
		Arrays.asList(stone),
		Arrays.asList(Duration.ofSeconds(5),Duration.ofSeconds(3),Duration.ofSeconds(4),Duration.ofSeconds(3)));
		MsmMonster mammott = new MsmMonster("Mammott",
		"Mammott + Quad",
		Arrays.asList(plantIsland,coldIsland),
		Arrays.asList(cold),
		Arrays.asList(Duration.parse("PT2M"),Duration.parse("PT1M30S"),Duration.parse("PT1M48S"),Duration.parse("PT1M18S")));

		// Logins
		MsmUser user1 = new MsmUser("user", "$2a$10$IiyD/u5KKXV6EVyZKETumONM7p5hqQvRqcfNNj/Z5513OS2E.ZCoC", "USER");
		MsmUser user2 = new MsmUser("admin", "$2a$10$1PKicp/C8HewhB8Ga/J./OjMz0A132x7GhfBcClyYOXAmdVW1HTQy","ADMIN");

		return (args) -> {
			// users
			uR.save(user1);
			uR.save(user2);

			// elements
			eR.save(stone);
			eR.save(water);
			eR.save(plant);
			eR.save(cold);
			eR.save(air);

			// islands
			iR.save(plantIsland);
			iR.save(coldIsland);

			// monsters
			mR.save(noggin);
			mR.save(mammott);
		};
	}

}
