package com.crossover.codeserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crossover.codeserver.entities.SdlcSystem;





@Repository
public interface SdlcSystemRepository extends JpaRepository<SdlcSystem, Long>{

}
