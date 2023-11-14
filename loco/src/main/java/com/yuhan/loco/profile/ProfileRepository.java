package com.yuhan.loco.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<profileDB,String> {
	profileDB findByID(String ID);

}
