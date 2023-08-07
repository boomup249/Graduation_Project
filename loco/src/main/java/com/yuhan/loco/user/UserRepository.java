package com.yuhan.loco.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserDB, String>{
	//중복 확인
	boolean existsByEMAIL(String EMAIL);
	boolean existsByID(String ID);
	
	//로그인
	boolean existsByEMAILAndPWD(String EMAIL, String PWD);
	boolean existsByIDAndPWD(String ID, String PWD);
}
