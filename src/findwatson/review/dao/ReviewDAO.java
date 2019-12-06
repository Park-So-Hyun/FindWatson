package findwatson.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import findwatson.configuration.Configuration;
import findwatson.review.dto.ReviewDTO;

public class ReviewDAO {
	
	private BasicDataSource bds = new BasicDataSource();
	private static ReviewDAO instance;
	
	ReviewDAO(){
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		bds.setUsername("watson");
		bds.setPassword("watson");
		bds.setInitialSize(10);
	}
	
	private Connection getConnection() throws Exception{
		return bds.getConnection();
	}
	public synchronized static ReviewDAO getInstance() {
		if(instance == null) {
			instance = new ReviewDAO();
		}
		return instance;
	}
	
	//리뷰 등록
	public int insert(ReviewDTO dto)throws Exception{ 
		String sql = "insert into hosptReview values (hosptReviewSeq.nextval, ?,?,?,?,?,?,sysdate,?,0)";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, dto.getHosptListSeq());
			pstat.setInt(2, dto.getScore());
			pstat.setString(3, dto.getTitle());
			pstat.setString(4, dto.getContent());
			pstat.setString(5, dto.getHeader()); 
			pstat.setString(6, dto.getWriter());
			pstat.setString(7, dto.getIpAddr());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	//리뷰 몇개있는지 리턴 - 인자값 병원 시퀀스
	public int getArticleCount(int hosptReviewSeq) throws Exception{
		String sql = "select count(*) from hosptReview where hosptListSeq = ?";
		try(
			Connection con = getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, hosptReviewSeq);
			try(
					ResultSet rs = pstat.executeQuery();	
					){
				rs.next();
				int result = rs.getInt(1);
				con.commit();
				return result;
			}
		}
	}
	
	//모든 리뷰 출력
	public List<ReviewDTO> selectAll() throws Exception{
		String sql = "select * from hosptReview order by seq desc";
		List<ReviewDTO> list = new ArrayList<>();
		try(
			Connection con = getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
			ResultSet rs = pstat.executeQuery();
				){
			while(rs.next()) {
				//(hosptReviewSeq.nextval, 0,0,'title','content','bird','writer',sysdate,'localhost',0,0)
				int seq = rs.getInt(1);
				int articleSeq = rs.getInt(2);
				int score = rs.getInt(3);
				String title = rs.getString(4);
				String content = rs.getString(5);
				String header = rs.getString(6);
				String writer = rs.getString(7);
				Timestamp writeDate = rs.getTimestamp(8);
				String ipAddr = rs.getString(9);
				int likeCount = rs.getInt(10);
				
				ReviewDTO dto = new ReviewDTO(seq, articleSeq, score, title, content, header, writer,
						writeDate, ipAddr, likeCount);
				list.add(dto);
			}
			return list;
		}	
	}
	
	//(디폴트 최신순)페이지당 리뷰 출력 - 인자값 병원 시퀀스, 시작페이지번호 , 끝페이지 번호
	public List<ReviewDTO> selectByPage(int hosptListSeqInput, int start, int end) throws Exception{
		String sql = "select * from (select row_number() over (order by seq desc)as rown, hosptreview.* from hosptreview) "
				+ "where rown between ? and ?  and hosptListSeq = ?";
		List<ReviewDTO> list = new ArrayList<>();
		try(
			Connection con = getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, start);
			pstat.setInt(2, end);
			pstat.setInt(3, hosptListSeqInput);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				while(rs.next()) {
					int seq = rs.getInt(2);
					int hosptListSeq = hosptListSeqInput;
					int score = rs.getInt(4);
					String title = rs.getString(5);
					String content = rs.getString(6);
					String header = rs.getString(7);
					String writer = rs.getString(8);
					Timestamp writeDate = rs.getTimestamp(9);
					String ipAddr = rs.getString(10);
					int likeCount = rs.getInt(11);
					
					ReviewDTO dto = new ReviewDTO(seq, hosptListSeq, score, title, content, header, writer,
							writeDate, ipAddr, likeCount);
//					System.out.println(seq+" : "+hosptListSeq+" : "+ score+" : "+ title+" : "+ content+" : "+ header+" : "+ writer+" : "+
//							writeDate+" : "+ ipAddr+" : "+ likeCount);
					list.add(dto);
				}
			}
		}	
		return list;
	}
	
	//(별점순)페이지당 리뷰 출력 - 인자값 병원 시퀀스, 시작페이지번호 , 끝페이지 번호
		public List<ReviewDTO> selectByPageByScore(int hosptListSeqInput, int start, int end) throws Exception{
			String sql = "select * from (select row_number() over (order by score desc, writeDate)as rown, hosptreview.* "
					+ "from hosptreview where hosptListSeq = ?) where rown between ? and ?";
			List<ReviewDTO> list = new ArrayList<>();
			try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
					){
				pstat.setInt(1, hosptListSeqInput);
				pstat.setInt(2, start);
				pstat.setInt(3, end);
				try(
						ResultSet rs = pstat.executeQuery();
						){
					while(rs.next()) {
						int seq = rs.getInt(2);
						int hosptListSeq = hosptListSeqInput;
						int score = rs.getInt(4);
						String title = rs.getString(5);
						String content = rs.getString(6);
						String header = rs.getString(7);
						String writer = rs.getString(8);
						Timestamp writeDate = rs.getTimestamp(9);
						String ipAddr = rs.getString(10);
						int likeCount = rs.getInt(11);
						
						ReviewDTO dto = new ReviewDTO(seq, hosptListSeq, score, title, content, header, writer,
								writeDate, ipAddr, likeCount);
						list.add(dto);
					}
				}
			}	
			return list;
		}
		//(좋아요순)페이지당 리뷰 출력 - 인자값 병원 시퀀스, 시작페이지번호 , 끝페이지 번호
				public List<ReviewDTO> selectByPageByLike(int hosptListSeqInput, int start, int end) throws Exception{
					String sql = "select * from (select row_number() over (order by likeCount desc)as rown, hosptreview.*"
							+ " from hosptreview ) where rown between ? and ?  and hosptListSeq = ?";
					List<ReviewDTO> list = new ArrayList<>();
					try(
						Connection con = getConnection();
						PreparedStatement pstat = con.prepareStatement(sql);
							){
						pstat.setInt(1, start);
						pstat.setInt(2, end);
						pstat.setInt(3, hosptListSeqInput);
						try(
								ResultSet rs = pstat.executeQuery();
								){
							while(rs.next()) {
								int seq = rs.getInt(2);
								int hosptListSeq = hosptListSeqInput;
								int score = rs.getInt(4);
								String title = rs.getString(5);
								String content = rs.getString(6);
								String header = rs.getString(7);
								String writer = rs.getString(8);
								Timestamp writeDate = rs.getTimestamp(9);
								String ipAddr = rs.getString(10);
								int likeCount = rs.getInt(11);
								
								ReviewDTO dto = new ReviewDTO(seq, hosptListSeq, score, title, content, header, writer,
										writeDate, ipAddr, likeCount);
								list.add(dto);
							}
						}
					}	
					return list;
				}

		//페이지 네비게이터 출력
	public String getPageNavi(int currentPage, int hosptListSeq)throws Exception{
		int recordTotalCount = this.getArticleCount(hosptListSeq);
		
		int pageTotalCount  = 0;
		int adv = recordTotalCount % Configuration.recordCountPerPage;
		
		if(adv > 0) {
			pageTotalCount = (recordTotalCount / Configuration.recordCountPerPage) + 1;
		}else {
			pageTotalCount = (recordTotalCount / Configuration.recordCountPerPage);
		}
		
		if(currentPage < 1) {
			currentPage = 1;
		}else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		
		int startNavi = (currentPage - 1)/Configuration.naviCountPerPage *Configuration.naviCountPerPage +1;
		int endNavi = startNavi + (Configuration.naviCountPerPage -1);
		
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = startNavi ; i <= endNavi ; i++) {
			sb.append("<a href='hospitalSearchDetail2.re?seq="+hosptListSeq+"&cpage="+i+"' id=page"+i+">");
			sb.append(i + " ");
			sb.append("</a>");
		}
		
		return sb.toString();
	}
	
	public int incrementLike(int reviewSeq) throws Exception{
		String sql = "update hosptReview set likeCount = 1 + (select likeCount from hosptReview "
				+ "where seq = ?) where seq=?";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql); 
				){
			pstat.setInt(1, reviewSeq);
			pstat.setInt(2, reviewSeq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	
	public int getHspSeqByRvSeq(int rvSeq)throws Exception{
		String sql = "select hosptListSeq from hosptReview where seq=?";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql); 
				){
			pstat.setInt(1, rvSeq);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				rs.next();
				return rs.getInt(1);
			}
		}
	}
	
	
}
