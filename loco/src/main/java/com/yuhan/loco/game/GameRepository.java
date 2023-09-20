package com.yuhan.loco.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface GameRepository extends JpaRepository<GameDB, String>{
}
