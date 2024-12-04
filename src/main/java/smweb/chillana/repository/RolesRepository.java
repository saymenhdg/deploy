package smweb.chillana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smweb.chillana.model.RolesModel;

public interface RolesRepository extends JpaRepository<RolesModel,Integer> {
    RolesModel findByRole(String role);
}
