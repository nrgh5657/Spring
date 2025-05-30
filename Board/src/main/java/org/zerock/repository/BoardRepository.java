package org.zerock.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.zerock.common.DBManager;
import org.zerock.dto.BoardVO;

@Repository
public class BoardRepository {

	@Autowired
	private DataSource dataSource;
	
	public List<BoardVO> selectAllBoards(){
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		List<BoardVO> boards = new ArrayList<>();
	        String sql = "SELECT num, pass, name, email, title, content, readcount, writedate FROM board ORDER BY num DESC";

	        try {conn = dataSource.getConnection();
	             pstmt = conn.prepareStatement(sql);
	             rs = pstmt.executeQuery();

	            while (rs.next()) {
	                BoardVO board = new BoardVO();
	                board.setNum(rs.getInt("num"));
	                board.setPass(rs.getString("pass"));
	                board.setName(rs.getString("name"));
	                board.setEmail(rs.getString("email"));
	                board.setTitle(rs.getString("title"));
	                board.setContent(rs.getString("content"));
	                board.setReadCount(rs.getInt("readcount"));
	                board.setWriteDate(rs.getDate("writedate"));
	                boards.add(board);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();
	        }finally {
	        	DBManager.close(conn, pstmt, rs);	        }

	        return boards;
	}
	
	public BoardVO selectOneByNum(int num){
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		String sql = "SELECT * FROM board where num =?";
		BoardVO  bVo= new BoardVO();

	        try {conn = dataSource.getConnection();
	             pstmt = conn.prepareStatement(sql);
	             pstmt.setInt(1, num);
	             rs = pstmt.executeQuery();

	            if (rs.next()) {
	                bVo.setNum(rs.getInt("num"));
	                bVo.setPass(rs.getString("pass"));
	                bVo.setName(rs.getString("name"));
	                bVo.setEmail(rs.getString("email"));
	                bVo.setTitle(rs.getString("title"));
	                bVo.setContent(rs.getString("content"));
	                bVo.setReadCount(rs.getInt("readcount"));
	                bVo.setWriteDate(rs.getDate("writedate"));
	
	            }
	        }catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	        	DBManager.close(conn, pstmt, rs);
	        }

	        return bVo;
	}
	public void insertBoard(BoardVO vo){
		Connection conn =null;
		PreparedStatement pstmt =null;
		String sql = "insert into board(num, name, pass, email, title, content) "
				+ "values(board_seq.nextval,?,?,?,?,?)";
		BoardVO  bVo= new BoardVO();
		
		try {
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getName());
		pstmt.setString(2, vo.getPass());
		pstmt.setString(3, vo.getEmail());
		pstmt.setString(4, vo.getTitle());
		pstmt.setString(5, vo.getContent());
		pstmt.executeQuery();
		

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		
	}
	public void updateBoard(BoardVO vo) {
		String sql = "update board set name=?, email=?, pass=?, title=?, content=? where num=?";

		Connection conn = null;
		PreparedStatement pstmt = null;
	
		try {
			//1. DB연결
			conn = dataSource.getConnection();
			//2. sql전송
			pstmt = conn.prepareStatement(sql);
			//3. sql 맵핑
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPass());
			pstmt.setString(4, vo.getTitle());
			pstmt.setString(5, vo.getContent());
			pstmt.setInt(6, vo.getNum());
			//4. sql 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
	}
	public void deleteBoard(int num) {
		String sql = "delete from board where num=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			//1. DB연결
			conn = dataSource.getConnection();
			//2. sql전송
			pstmt = conn.prepareStatement(sql);
			//3. sql 맵핑
			pstmt.setInt(1, num);
			//4. sql 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
	}
	public void updateReadCount(int num) {
		String sql = "update board set readcount=readcount+1 where num=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn=dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();	
		}finally {
			DBManager.close(conn, pstmt);
		}
	}
	
	public BoardVO checkPassword(String pass, int num) {
		String sql = "select *from board where pass =? and num=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO vo = null;
	
		try {
			//1. DB연결
			conn = dataSource.getConnection();
			//2. sql전송
			pstmt = conn.prepareStatement(sql);
			//3. sql 맵핑
			pstmt.setString(1, pass);
			pstmt.setInt(2, num);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new BoardVO();
				vo.setNum(rs.getInt("num"));
				vo.setName(rs.getString("name"));
				vo.setPass(rs.getString("pass"));
				vo.setEmail(rs.getString("email"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setReadCount(rs.getInt("readcount"));
				vo.setWriteDate(rs.getTimestamp("writedate"));
			}
			

			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return vo;
	}	
	
}
