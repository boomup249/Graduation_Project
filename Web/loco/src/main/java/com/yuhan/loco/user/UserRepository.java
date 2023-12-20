package com.yuhan.loco.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserDB, String>{
	//중복 확인
	boolean existsByEMAIL(String EMAIL);
	boolean existsByID(String ID);

	//findby
	UserDB findByEMAIL(String EMAIL);
	UserDB findByID(String ID);

}
