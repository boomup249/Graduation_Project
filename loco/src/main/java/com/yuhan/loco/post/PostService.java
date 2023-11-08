package com.yuhan.loco.post;


import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;

@Service
@Transactional
public class PostService {
   private final PostRepository Postrepository;

   public PostService(PostRepository Postrepository) {
        this.Postrepository = Postrepository;
    }

   public PostDB create(Long bbs_id, String category, String title, String writer, String content) {
	  PostDB post = new PostDB();
	  post.setBbs_id(bbs_id);
      post.setCategory(category);
      post.setTitle(title);
      post.setWriter(writer);
      post.setContent(content);

      System.out.println("----repository----");
      System.out.println(post.getCategory());
      System.out.println(post.getTitle());
      System.out.println(post.getWriter());
      System.out.println(post.getContent());
      this.Postrepository.saveAndFlush(post);
      return post;
   }
   public PostDB getByID(Long id) {
       return Postrepository.findByBbs(id);
   }
}
