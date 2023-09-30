package com.yuhan.loco.bbs;

import java.sql.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PostService {
   private final PostRepository Postrepository;
   PostDB post = new PostDB();
   
   public PostService(PostRepository Postrepository) {
        this.Postrepository = Postrepository;
    }
   
   public PostDB create(Long id, String category, String title, String writer, String content, String comment) {
	  post.setId(null);
      post.setCategory(category);
      post.setTitle(title);
      post.setWriter(writer);
      post.setContent(content);
      post.setComment(comment);
      
      System.out.println("----repository----");
      System.out.println(post.getCategory());
      System.out.println(post.getTitle());
      System.out.println(post.getWriter());
      System.out.println(post.getContent());
      System.out.println(post.getComment());
      this.Postrepository.saveAndFlush(post);  
      return post;
   }
   
}
