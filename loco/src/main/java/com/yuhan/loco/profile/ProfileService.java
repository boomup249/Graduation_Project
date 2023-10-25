package com.yuhan.loco.profile;

import java.io.IOException;
import java.sql.Date;

import javax.mail.Multipart;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


import com.yuhan.loco.user.UserDB;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProfileService {
	private final ProfileRepository profileRepository;
	profileDB profileDB = new profileDB();
	
	public ProfileService(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}
	
   public profileDB findUser(String id) {
       profileDB profiledb = this.profileRepository.findByID(id);
       return profiledb;
   }
	
   public profileDB create(String id, String nickname, byte[] img, String description) {
	   profileDB.setID(id);
	   profileDB.setNICKNAME(nickname);
	   profileDB.setIMG(img);
	   profileDB.setDESCRIPTION(description);
	   
      this.profileRepository.save(profileDB);   
      return profileDB;
   }
	
	public profileDB saveMypage(String id,String nickname, String des) {
		profileDB = this.findUser(id);
		profileDB.setNICKNAME(nickname);
		profileDB.setDESCRIPTION(des);
		profileRepository.save(profileDB);
		
		return profileDB;
	}
	

    public void saveImage(String userId, byte[] imageBytes) throws IOException {;
        profileDB profiledb = new profileDB();
        profiledb.setID(userId);
        profiledb.setIMG(imageBytes);
        profileRepository.save(profiledb);
    }
	
    public String convertByteToBase64(byte[] imageData) {
        byte[] encoded = Base64.encodeBase64(imageData, false);
        return "data:image/jpeg;base64," + new String(encoded);
    }
    public byte[] loadImageAsBytes() throws IOException {
        // 리소스 경로 설정 (classpath: 를 사용하여 클래스패스 상의 파일을 찾음)
        Resource resource = new ClassPathResource("static/imgs/header/normal.png");

        // 파일을 byte[]로 읽어오기
        byte[] imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());

        return imageBytes;
    }

}
