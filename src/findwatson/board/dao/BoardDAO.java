package findwatson.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import findwatson.admin.dto.ExpertDTO;

import findwatson.admin.dto.NoticeDTO;
import findwatson.board.dto.BoardDTO;
import findwatson.configuration.Configuration;
import findwatson.utils.Statics;

public class BoardDAO {

	private static BoardDAO instance;
	private BoardDAO() {}
	private Connection getConnection() throws Exception{
		return Statics.bds.getConnection();
	}	
	public synchronized static BoardDAO getInstance() {
		if(BoardDAO.instance == null) {
			BoardDAO.instance = new BoardDAO();
		}
		return BoardDAO.instance;
	}
	//글 목록 조회
	public List<BoardDTO> selectAll() throws Exception{
		String sql = "select * from board order by seq desc";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){

			List<BoardDTO> result = new ArrayList<>();
			while(rs.next()) {
				int boardSeq = rs.getInt(1);
				String writer = rs.getString(2);
				String header = rs.getString(3);
				String Aheader = rs.getString(4);
				String title = rs.getString(5);
				String content = rs.getString(6);
				String ipAddr = rs.getString(7);
				int viewCount = rs.getInt(8);
				Timestamp writeDate = rs.getTimestamp(9);

				result.add(new BoardDTO(boardSeq, writer, header, Aheader, title, content, ipAddr, viewCount, writeDate));
			}
			return result;
		}
	}


	//// 자유게시판 /////

	// 카테고리별 검색에 따른 전체 목록 개수 반환 
	public int selectListSizeFree(String category, String keyword) throws Exception{
		String sql="sql";

		if(category.contentEquals("title")) {
			sql = "select *  from board where (header='자유') and (title like ?) order by seq desc";
		}else if(category.contentEquals("writer")) {
			sql = "select *  from board where (header='자유') and (writer like ?) order by seq desc";	
		}else if(category.contentEquals("animalheader")) {
			sql = "select *  from board where (header='자유') and (animalheader like ?) order by seq desc";	
		}

		try (
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			
			pstat.setString(1, "%" + keyword + "%");

			try(
					ResultSet rs = pstat.executeQuery();

					){
				List<BoardDTO> list = new ArrayList<BoardDTO>();
				while(rs.next()) {

					int seq = rs.getInt(1);
					String writer = rs.getString(2);
					String header = rs.getString(3);

					String animalHeader = rs.getString(4);
					String title = rs.getString(5);
					String content = rs.getString(6);
					String ipAddr = rs.getString(7);
					int viewCount = rs.getInt(8);
					Timestamp writeDate = rs.getTimestamp(9);

					BoardDTO dto = new BoardDTO(seq, writer, header, animalHeader, title,
							content, ipAddr, viewCount, writeDate);
					list.add(dto);


				}
				return list.size();
			}	
		}
	}


	// 카테고리별 검색에 따른 목록을 페이지 수에 맞춰 반환 
	public List<BoardDTO> selectByOptionFree(String category, String keyword, int start, int end) throws Exception{
		String sql = "sql";
		if(category.contentEquals("title")) {
			sql = "select * from (select board.*, row_number() over (order by seq desc) "
					+ "as rank from board  where (header='자유') and (title like ?)) where (rank between ? and ?) order by seq desc";
		}else if(category.contentEquals("writer")) {
			sql = "select * from (select board.*, row_number() over (order by seq desc) "
					+ "as rank from board  where (header='자유') and (writer like ?)) where (rank between ? and ?) order by seq desc";
		}else if(category.contentEquals("animalheader")) {
			sql = "select * from (select board.*, row_number() over (order by seq desc) "
					+ "as rank from board  where (header='자유') and (animalheader like ?)) where (rank between ? and ?) order by seq desc";
		}

		try (
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);

				){
			pstat.setString(1, "%" + keyword + "%");
			pstat.setInt(2, start);
			pstat.setInt(3, end);

			try(
					ResultSet rs = pstat.executeQuery();

					){
				List<BoardDTO> list = new ArrayList<BoardDTO>();
				while(rs.next()) {

					int seq = rs.getInt(1);
					String writer = rs.getString(2);
					String header = rs.getString(3);

					String animalHeader = rs.getString(4);
					String title = rs.getString(5);
					String content = rs.getString(6);
					String ipAddr = rs.getString(7);
					int viewCount = rs.getInt(8);
					Timestamp writeDate = rs.getTimestamp(9);
					BoardDTO dto = new BoardDTO(seq, writer, header, animalHeader, title,
							content, ipAddr, viewCount, writeDate);
					list.add(dto);

				}
				return list;
			}	
		}
	}

	// option에 따른 검색 결과 페이지 네비게이터
	public String getPageNaviTotalFree(int currentPage, String category, String keyword) throws Exception {

		int recordTotalCount = this.selectListSizeFree(category, keyword);
	
		int pageTotalCount = 0;

		if(recordTotalCount % Configuration.recordCountPerPage > 0) {

			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage + 1;

		}else{
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage;
		}

		if(currentPage < 1) {
			currentPage = 1;	

		}else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}

		int startNavi = (currentPage - 1) /  Configuration.naviCountPerPage  * Configuration.naviCountPerPage + 1;

		int endNavi = startNavi + Configuration.naviCountPerPage - 1;

		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		boolean needPrev = true;
		boolean needNext = true;

		if(startNavi == 1) {
			needPrev = false;
		}
		if(endNavi == pageTotalCount) {
			needNext = false;
		}

		StringBuilder sb = new StringBuilder();
		if(needPrev) {sb.append("<a href='searchFree.bo?currentPage="+(startNavi - 1)+"&keyword="+keyword+"&category="+category+"'> < </a>");}
		// 값을 서버에서 만들어서 + 붙여서 프론트로 나가는거니까 
		// 서버에서 이미 앵커태그가 붙어야한다 
		for(int i = startNavi; i <= endNavi; i++) {
			sb.append("<a href = 'searchFree.bo?currentPage="+i+"&keyword="+keyword+"&category="+category+"'> ");
			sb.append(i + " ");
			sb.append("</a>");
		}
		if(needNext) sb.append("<a href='searchFree.bo?currentPage=" + (endNavi + 1) + "&keyword="+keyword+"&category="+category+"'> > </a>");

		return sb.toString();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}

	//// 질문게시판 /////	


	// 카테고리별 검색에 따른 전체 목록 개수 반환 
	public int selectListSizeOne(String category, String keyword) throws Exception{
		String sql="sql";

		if(category.contentEquals("title")) {
			sql = "select *  from board where (header='질문') and (title like ?) order by seq desc";
		}else if(category.contentEquals("writer")) {
			sql = "select *  from board where (header='질문') and (writer like ?) order by seq desc";	
		}else if(category.contentEquals("animalheader")) {
			sql = "select *  from board where (header='질문') and (animalheader like ?) order by seq desc";	
		}

		try (
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, "%" + keyword + "%");

			try(
					ResultSet rs = pstat.executeQuery();

					){
				List<BoardDTO> list = new ArrayList<BoardDTO>();
				while(rs.next()) {

					int seq = rs.getInt(1);

					String writer = rs.getString(2);
					String header = rs.getString(3);

					String animalHeader = rs.getString(4);
					String title = rs.getString(5);
					String content = rs.getString(6);
					String ipAddr = rs.getString(7);
					int viewCount = rs.getInt(8);
					Timestamp writeDate = rs.getTimestamp(9);

					BoardDTO dto = new BoardDTO(seq, writer, header, animalHeader, title,
							content, ipAddr, viewCount, writeDate);
					list.add(dto);


				}

				return list.size();
			}	
		}
	}


	// 카테고리별 검색에 따른 목록을 페이지 수에 맞춰 반환 
	public List<BoardDTO> selectByOptionOne(String category, String keyword, int start, int end) throws Exception{
		String sql = "sql";
		if(category.contentEquals("title")) {
			sql = "select * from (select board.*, row_number() over (order by seq desc) "
					+ "as rank from board  where (header='질문') and (title like ?)) where (rank between ? and ?) order by seq desc";
		}else if(category.contentEquals("writer")) {
			sql = "select * from (select board.*, row_number() over (order by seq desc) "
					+ "as rank from board  where (header='질문') and (writer like ?)) where (rank between ? and ?) order by seq desc";
		}else if(category.contentEquals("animalheader")) {
			sql = "select * from (select board.*, row_number() over (order by seq desc) "
					+ "as rank from board  where (header='질문') and (animalheader like ?)) where (rank between ? and ?) order by seq desc";
		}


		try (
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, "%" + keyword + "%");
			pstat.setInt(2, start);
			pstat.setInt(3, end);

			try(
					ResultSet rs = pstat.executeQuery();

					){
				List<BoardDTO> list = new ArrayList<BoardDTO>();
				while(rs.next()) {

					int seq = rs.getInt(1);

					String writer = rs.getString(2);
					String header = rs.getString(3);

					String animalHeader = rs.getString(4);
					String title = rs.getString(5);
					String content = rs.getString(6);
					String ipAddr = rs.getString(7);
					int viewCount = rs.getInt(8);
					Timestamp writeDate = rs.getTimestamp(9);

					BoardDTO dto = new BoardDTO(seq, writer, header, animalHeader, title,
							content, ipAddr, viewCount, writeDate);
					list.add(dto);

				}
				return list;
			}	
		}
	}

	// option에 따른 검색 결과 페이지 네비게이터
	public String getPageNaviTotalOne(int currentPage, String category, String keyword) throws Exception {

		int recordTotalCount = this.selectListSizeOne(category, keyword);
		int pageTotalCount = 0;

		if(recordTotalCount % Configuration.recordCountPerPage > 0) {

			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage + 1;

		}else{
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage;
		}

		if(currentPage < 1) {
			currentPage = 1;	

		}else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		int startNavi = (currentPage - 1) /  Configuration.naviCountPerPage  * Configuration.naviCountPerPage + 1;

		int endNavi = startNavi + Configuration.naviCountPerPage - 1;

		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		boolean needPrev = true;
		boolean needNext = true;

		if(startNavi == 1) {
			needPrev = false;
		}
		if(endNavi == pageTotalCount) {
			needNext = false;
		}

		StringBuilder sb = new StringBuilder();
		if(needPrev) {sb.append("<a href='searchOne.bo?currentPage="+(startNavi - 1)+"&keyword="+keyword+"&category="+category+"'> < </a>");}
		// 값을 서버에서 만들어서 + 붙여서 프론트로 나가는거니까 
		// 서버에서 이미 앵커태그가 붙어야한다 
		for(int i = startNavi; i <= endNavi; i++) {
			sb.append("<a href = 'searchOne.bo?currentPage="+i+"&keyword="+keyword+"&category="+category+"'> ");
			sb.append(i + " ");
			sb.append("</a>");
		}
		if(needNext) sb.append("<a href='searchOne.bo?currentPage=" + (endNavi + 1) + "&keyword="+keyword+"&category="+category+"'> > </a>");

		return sb.toString();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	}	

	//글쓰기
	public int insert(BoardDTO dto) throws Exception{
		String sql = "insert into board values(boardSeq.nextval,?,?,?,?,?,?,0,sysdate)";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getWriter());
			pstat.setString(2, dto.getHeader());
			pstat.setString(3, dto.getAnimalHeader());
			pstat.setString(4, dto.getTitle());
			pstat.setString(5, dto.getContent());
			pstat.setString(6, dto.getIpAddr());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	//글삭제
	public int delete(int seq) throws Exception{
		String sql = "delete from board where boardSeq=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, seq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

		//글 수정  제목, 내용, 날짜
		public int update(int seq, String title, String content, String animalHeader) throws Exception{
			String sql = "update board set title=?, content=?,animalHeader =?, writeDate=sysdate where seq=?";
			try(
					Connection con = getConnection();
					PreparedStatement pstat = con.prepareStatement(sql);
					){
				pstat.setString(1, title);
				pstat.setString(2, content);
				pstat.setString(3, animalHeader);
				pstat.setInt(4, seq);
				int result = pstat.executeUpdate();
				con.commit();
				return result;
			}
		}

	private int getTotalBoard(String board) throws Exception{
		String sql = "select count(*) from board where header = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, board);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				rs.next();
				return rs.getInt(1);
			}

		}
	}
	private int getTotalExpert() throws Exception{
		String sql = "select count(*) from expert";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			rs.next();
			return rs.getInt(1);
		}
	}
	private int getTotalNotice() throws Exception{
		String sql = "select count(*) from notice";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			rs.next();
			return rs.getInt(1);
		}
	}
	
	public String getPageNavi(int current, String category,String header) throws Exception{
	int recordTotalCount =0;
		if(header.contentEquals("전문가")) {
		recordTotalCount = this.getTotalExpert();
	}else if(header.contentEquals("공지")) {
		recordTotalCount = this.getTotalNotice();
	}else {
		recordTotalCount = this.getTotalBoard(header);
	}
		int pageTotalCount = 0;
		if(recordTotalCount % Configuration.recordCountPerPage > 0) {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage + 1;
		}else {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage;
		}
		int currentPage = current;
		if(currentPage < 1) {
			currentPage = 1;
		}else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		int startNavi = (currentPage - 1) / Configuration.naviCountPerPage * Configuration.naviCountPerPage + 1;
		int endNavi = startNavi + (Configuration.naviCountPerPage - 1);
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		boolean needPrev = true;
		if(startNavi == 1) {
			needPrev = false;
		}
		boolean needNext = true;
		if(endNavi == pageTotalCount) {
			needNext = false;
		}
		StringBuilder sb = new StringBuilder();

		//이부분 servlet 기능에 따라 수정하기
		if(needPrev) sb.append("<a href='"+category+"?cpage=" + (startNavi - 1) + "'>< </a>");

		for(int i = startNavi; i <= endNavi; i++) {
			sb.append("<a href='"+category+"?cpage="+ i +"'>");
			sb.append(i + " ");
			sb.append("</a>");
		}

		if(needNext) sb.append("<a href='"+category+"?cpage=" + (endNavi + 1) + "'>> </a>");

		return sb.toString();
	}

	//커뮤니티 - 질문 /자유(게시글끊어서 가져오기)
	public List<BoardDTO> selectByPage(int start, int end, String header) throws Exception{
		String sql = "select * from (select board.*, row_number() over(order by seq desc) rown from board where header =?) where rown between ? and ?";
		List<BoardDTO> result = new ArrayList<>();
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, header);
			pstat.setInt(2, start);
			pstat.setInt(3, end);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				while(rs.next()) {
					int boardSeq = rs.getInt(1);
					String writer = rs.getString(2);
					String headerInput = header;
					String animalHeader = rs.getString(4);
					String title = rs.getString(5);
					String content = rs.getString(6);
					String ipAddr = rs.getString(7);
					int viewCount = rs.getInt(8);
					Timestamp writeDate = rs.getTimestamp(9);

					result.add(new BoardDTO(boardSeq, writer, headerInput,animalHeader,title, content, ipAddr, viewCount, writeDate));
				}
				return result;
			}

		}
	}

	// 전문가Q&A - (게시글끊어서 가져오기)
	public List<ExpertDTO> selectByPageExpert(int start, int end) throws Exception{
		String sql = "select * from (select expert.*, row_number() over(order by seq desc) rown from expert) where rown between ? and ?";
		List<ExpertDTO> result = new ArrayList<>();
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, start);
			pstat.setInt(2, end);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				while(rs.next()) {
					int seq = rs.getInt(1);
					String writer = rs.getString(2);
					String title = rs.getString(3);
					String content = rs.getString(4);
					Timestamp writeDate = rs.getTimestamp(5);
					int viewCount = rs.getInt(6);

					result.add(new ExpertDTO(seq, writer, title, content, writeDate, viewCount));
				}
				return result;
			}

		}
	}

	// 공지사항 - (게시글끊어서 가져오기)
	public List<NoticeDTO> selectByPageNotice(int start, int end) throws Exception{
		String sql = "select * from (select notice.*, row_number() over(order by seq desc) rown from notice) where rown between ? and ?";
		List<NoticeDTO> result = new ArrayList<>();
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, start);
			pstat.setInt(2, end);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				while(rs.next()) {
					int seq = rs.getInt(1);
					String title = rs.getString(2);
					String content = rs.getString(3);
					Timestamp writeDate = rs.getTimestamp(4);
					int viewCount = rs.getInt(5);

					result.add(new NoticeDTO(seq, title, content, writeDate, viewCount));
				}
				return result;
			}

		}
	}

	public int increViewCnt(int seq) throws Exception{ //뷰카운트 증가
		String sql = "update board set viewCount=(select viewCount from board where seq=?)+1 where seq=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, seq);
			pstat.setInt(2, seq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	
	//메인에 나오는 공지 5개
	public List<NoticeDTO> getMainNotice() throws Exception{
		String sql = "select * from (select notice.*, row_number() over(order by seq desc) rown from notice)\r\n" + 
				"where rown <= 5 order by seq desc";
		List<NoticeDTO> result = new ArrayList<>();
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			while(rs.next()) {
				
				int seq = rs.getInt(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Timestamp writeDate = rs.getTimestamp(4);
				int viewCount = rs.getInt(5);
				
				result.add(new NoticeDTO(seq, title, content, writeDate, viewCount));
			}
			return result;
		}
	}
	//메인에 나오는 자유게시판 5개
	public List<BoardDTO> getMainFree() throws Exception{
		String sql = "select * from (select board.*, row_number() over(order by seq desc) rown from board)\r\n" + 
				"where rown <= 5 and header='자유' order by seq desc";
		
		List<BoardDTO> result = new ArrayList<>();
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			while(rs.next()) {
				int seq = rs.getInt(1);
				String writer = rs.getString(2);
				String header = rs.getString(3);
				String animalHeader = rs.getString(4);
				String title = rs.getString(5);
				String content = rs.getString(6);
				String ipAddr = rs.getString(7);
				int viewCount = rs.getInt(8);
				Timestamp writeDate = rs.getTimestamp(9);
				
				result.add(new BoardDTO(seq, writer, header, animalHeader, title, content, ipAddr, viewCount, writeDate));
			}
			return result;
		}
	}
	//리뷰삭제
	public int deleteReview(int seq)throws Exception{
		String sql = "delete from hosptReview where seq =?";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, seq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	
	


}