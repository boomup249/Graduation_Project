package com.yuhan.loco.bbs;


import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BBSService {
   private final BBSRepository BBSrepository;
   BBSDB bbs = new BBSDB();
   public BBSService(BBSRepository BBSrepository) {
        this.BBSrepository = BBSrepository;
    }
   public BBSDB create(Long id, String title, String writer, String category, String date, Long views, Long comment) {
	   bbs.setId(null);
	   bbs.setTitle(title);
	   bbs.setWriter(writer);
	   bbs.setCategory(category);
	   bbs.setDate(date);
	   bbs.setViews(views);
	   bbs.setComment(comment);
	   this.BBSrepository.saveAndFlush(bbs);
	   return bbs;
   }
   public List<BBSDB> search(){
	   return this.BBSrepository.findAll();
   }
}
