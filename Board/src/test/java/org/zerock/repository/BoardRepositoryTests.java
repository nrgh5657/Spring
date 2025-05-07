package org.zerock.repository;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.dto.BoardVO;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml","file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardRepositoryTests {
	
	@Autowired
	private BoardRepository boardRepository;
	@Test
	public void test() {
		log.info("boardRepository>>" + boardRepository);
	}
	@Test
	public void selectAlltest() {
		List<BoardVO> list = boardRepository.selectAllBoards();
		 for(BoardVO vo : list)
		log.info(vo);
	}
	
	@Test
	public void selectOneByNumTest() {
		BoardVO vo = boardRepository.selectOneByNum(25);	
		log.info("vo>>" + vo);
	}
	@Test
	public void insertBoardTest() {
		BoardVO vo = new BoardVO();
		vo.setName("까미");
		vo.setPass("1234");
		vo.setEmail("test@test.com");		
		vo.setTitle("우리강아지");
		vo.setContent("성질 더러운 강아지");
		boardRepository.insertBoard(vo);
	}
	@Test
	public void updateBoardTest() {
		BoardVO vo = new BoardVO();
		vo.setName("뽀잉");
		vo.setPass("1234");
		vo.setEmail("aaa@aaa.com");
		vo.setTitle("우리강아지");
		vo.setContent("성질착한 고양이");
		vo.setNum(41);
		boardRepository.updateBoard(vo);
	}
	
	@Test
	public void deleteBoardTest() {
		boardRepository.deleteBoard(43);
	}
	
	@Test
	public void updateReadCountTest() {
		boardRepository.updateReadCount(46);
	}
	
}





