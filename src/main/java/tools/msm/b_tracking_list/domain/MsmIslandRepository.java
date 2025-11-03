package tools.msm.b_tracking_list.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MsmIslandRepository extends CrudRepository<MsmIsland, Long> {
    MsmIsland findByNameI(String nameI);
    List<MsmIsland> findByElementsI(List<MsmElement> elementsI);
    List<MsmIsland> findBySeasonal(boolean seasonal);
    List<MsmIsland> findByHatcherTier(byte hatcherTier);
}
