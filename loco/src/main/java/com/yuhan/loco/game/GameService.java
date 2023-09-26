package com.yuhan.loco.game;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class GameService {
   private final PcRepository pcRepository;
   
   public GameService(PcRepository pcRepository) {
        this.pcRepository = pcRepository;
    }
   
   //연결 확인용
   public void a() {
	   List<PcDB> a = pcRepository.findBySITEAVAILABILITY("Both");
	   System.out.println(a);
   }
   
   //페이징(전체)
   public Page<PcDB> getFullPcList(int page) {
	   Pageable pageable = PageRequest.of(page, 20);
	   return this.pcRepository.findAll(pageable);
   }
}
