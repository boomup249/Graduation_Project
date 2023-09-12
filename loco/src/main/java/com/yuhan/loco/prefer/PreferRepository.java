package com.yuhan.loco.prefer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferRepository extends JpaRepository<PreferDB, String>{
	PreferDB findByID(String ID);
}
