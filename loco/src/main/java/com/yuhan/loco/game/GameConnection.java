package com.yuhan.loco.game;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameConnection {
	
	private static DataSource dataSource;
	
	@Autowired
	public GameConnection(DataSource datasource) {
		this.dataSource = datasource;
	}
	
	private static Connection con;
	
	public static Connection getCon() {
		try {
			con = dataSource.getConnection();
			System.out.println("연결 완료");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
