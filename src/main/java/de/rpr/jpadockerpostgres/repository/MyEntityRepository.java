package de.rpr.jpadockerpostgres.repository;

import de.rpr.jpadockerpostgres.entity.MyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyEntityRepository extends JpaRepository<MyEntity, Long>{
}
