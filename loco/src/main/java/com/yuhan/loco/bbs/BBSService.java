package com.yuhan.loco.bbs;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BBSService {
   private final BBSRepository BBSrepository;
   private final CommentRepository Commentrepository;

   public BBSService(BBSRepository BBSrepository, CommentRepository Commentrepository) {
        this.BBSrepository = BBSrepository;
        this.Commentrepository = Commentrepository;
    }
   public BBSDB create(String title, String writer, String category, String date, Long views, Long comment) {
	   BBSDB bbs = new BBSDB();
	   bbs.setTitle(title);
	   bbs.setWriter(writer);
	   bbs.setCategory(category);
	   bbs.setDate(date);
	   bbs.setViews(views);
	   bbs.setComment(comment);
	   this.BBSrepository.saveAndFlush(bbs);
	   return bbs;
   }
   public void commentcreate(Long postid, String comment, String writer) {
	   CommentDB commdb = new CommentDB();
	   commdb.setPost_id(postid);
	   commdb.setComment(comment);
	   commdb.setWriter(writer);
	   this.Commentrepository.save(commdb);
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
   public int commentup(Long id) {
	   return Commentrepository.updateComment(id);
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
   /*
   public Long commentSave(String writer, Long id, CommentReqDTO comm) {
	   comm.setBbs(bbs);
	   CommentDB comment = comm.toEntity();
	   Commentrepository.save(comment);
	   return comm.getId();
   }*/
   public List<BBSDB> findID(String title) {
	   return this.BBSrepository.findByTitle(title);
   }

   public List<CommentDB> findCommentDB(Long id){
	   return this.Commentrepository.findByPost(id);
   }

}
