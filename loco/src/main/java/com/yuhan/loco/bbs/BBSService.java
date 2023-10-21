package com.yuhan.loco.bbs;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;

@Service
@Transactional
public class BBSService {
   private final BBSRepository BBSrepository;
   private final CommentRepository Commentrepository;
   BBSDB bbs = new BBSDB();
   public BBSService(BBSRepository BBSrepository, CommentRepository Commentrepository) {
        this.BBSrepository = BBSrepository;
        this.Commentrepository = Commentrepository;
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
   public BBSDB getbyID(Long id) {
	   return BBSrepository.findById(id);
   }
   public Page<BBSDB> search(int page, Pageable pageable){
	   return this.BBSrepository.findAll(pageable);
   }
   /*public List<BBSDB> searchall(){
	   return BBSrepository.findAll();
   }*/
   public int viewerup(Long id) {
	   return BBSrepository.updateView(id);
   }
   public Page<BBSDB> findbytitle(String title, int page){
	   Pageable pageable = PageRequest.of(page, 5);
	   return this.BBSrepository.findByTitleContaining(title, pageable);
   }
   public Page<BBSDB> findbbs(int page, Pageable pageable){
	   return this.BBSrepository.findByCategory("bbs", pageable);
   }
   public Page<BBSDB> findnotice(int page, Pageable pageable){
	   return this.BBSrepository.findByCategory("notice", pageable);
   }
   public Page<BBSDB> findguide(int page, Pageable pageable){
	   return this.BBSrepository.findByCategory("guide", pageable);
   }
   public Page<BBSDB> findparty(int page, Pageable pageable){
	   return this.BBSrepository.findByCategory("party", pageable);
   }
   public Long commentSave(String writer, Long id, CommentReqDTO comm) {
	   comm.setBbs(bbs);
	   CommentDB comment = comm.toEntity();
	   Commentrepository.save(comment);
	   return comm.getId();
   }
}
