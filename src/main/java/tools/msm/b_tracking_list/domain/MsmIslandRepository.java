package tools.msm.b_tracking_list.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface MsmIslandRepository extends CrudRepository<MsmIsland, Long> {
    Optional<MsmIsland> findByNameI(String nameI);
    List<MsmIsland> findByElementsI(List<MsmElement> elementsI);
    List<MsmIsland> findBySeasonal(boolean seasonal);
    List<MsmIsland> findByHatcherTier(byte hatcherTier);
}
