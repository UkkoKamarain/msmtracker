package tools.msm.b_tracking_list.domain;

import org.springframework.data.repository.CrudRepository;

public interface MsmElementRepository extends CrudRepository<MsmElement, Long> {
    MsmElement findByNameE(String nameE);
}
