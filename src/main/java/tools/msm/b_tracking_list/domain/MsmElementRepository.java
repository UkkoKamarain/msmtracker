package tools.msm.b_tracking_list.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface MsmElementRepository extends CrudRepository<MsmElement, Long> {
    Optional<MsmElement> findByNameE(String nameE);
}
