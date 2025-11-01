package tools.msm.b_tracking_list.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MsmElementRepository extends CrudRepository<MsmElement, Long> {
    List<MsmElement> findByNameE(String nameE);
}
