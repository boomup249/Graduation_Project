package com.yuhan.loco.game;

import java.sql.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
	//전역
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	
	
	//count
	public int count() {
		int cnt = 0;
		
		//connetion
		Connection con = GameConnection.getCon();
		
		try {
			String sql = "select count(*) from game";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(stmt != null) {
					stmt.close();
				}
				
				if(con != null) {
					con.close();
				}
			}catch (SQLException e) {
				System.out.println("count close할 때 문제 발생");
			}
		}
		
		System.out.println(cnt);
		return cnt;
	}
	
	//select
	public GameDTO select(int index) {
		GameDTO gameDTO = new GameDTO();
		
		//connetion
		Connection con = GameConnection.getCon();
		
		try {
			String sql = "select * from game where seq = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, index);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				gameDTO.setSeq(rs.getInt("seq"));
				gameDTO.setGameName(rs.getString("name"));
				gameDTO.setGameUrl(rs.getString("url"));
				
				//출력
				System.out.println("----gamedto(service)----");
				System.out.println(gameDTO.getSeq());
				System.out.println(gameDTO.getGameName());
				System.out.println(gameDTO.getGameUrl());
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					stmt.close();
				}
				
				if(con != null) {
					con.close();
				}
			}catch (SQLException e) {
				System.out.println("select close할 때 문제 발생");
			}
		}
		
		return gameDTO;
	}
	
}
