package tools.msm.b_tracking_list.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MsmMonsterRepository extends CrudRepository<MsmMonster, Long> {
    List<MsmMonster> findByNameM(String nameM);
    List<MsmMonster> findByElementsM(List<MsmElement> elementsM);
}
