package tools.msm.b_tracking_list.domain;

import org.springframework.data.repository.CrudRepository;

public interface MsmUserRepository extends CrudRepository<MsmUser, String> {
    MsmUser findByUsername(String username);
}
