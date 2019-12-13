package findwatson.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import findwatson.board.dto.ComDTO;
import findwatson.configuration.Configuration;
import findwatson.utils.Statics;

public class ComDAO {
	private static ComDAO instance;
	private ComDAO() {}
	private Connection getConnetion() throws Exception{
		return Statics.bds.getConnection();
	}	
	public synchronized static ComDAO getInstance() {
		if(instance == null) {
			instance = new ComDAO();
		}
		return instance;
	}
	
	  public List<ComDTO> selectAll(int boardSeq) throws Exception{
	      String sql = "select * from comments where boardSeq=?";
	      List<ComDTO> result = new ArrayList<>();
	      try(
	            Connection con = this.getConnetion();
	            PreparedStatement pstat = con.prepareStatement(sql);
	            ){
	         pstat.setInt(1, boardSeq);
	         try(
	               ResultSet rs = pstat.executeQuery();
	               ){
	            while(rs.next()) {
	               int comSeq = rs.getInt(1);
	               String writer = rs.getString(3);
	               String content = rs.getString(4);
	               String ipAddr = rs.getString(5);
	               Timestamp writeDate = rs.getTimestamp(6);
	               
	               result.add(new ComDTO(comSeq, boardSeq, writer, content, ipAddr, writeDate));
	            }
	            return result;
	         }
	      }
	   }
	  public List<ComDTO> selectByPage(int boardSeq, int start, int end) throws Exception{
	      String sql = "select * from (select comments.*, row_number() over(order by seq desc) rown "
	      		+ "from comments where boardSeq=?) where rown between ? and ?";
	      List<ComDTO> result = new ArrayList<>();
	      try(
	            Connection con = this.getConnetion();
	            PreparedStatement pstat = con.prepareStatement(sql);
	            ){
	         pstat.setInt(1, boardSeq);
	         pstat.setInt(2, start);
	         pstat.setInt(3, end);
	         try(
	               ResultSet rs = pstat.executeQuery();
	               ){
	            while(rs.next()) {
	               int comSeq = rs.getInt(1);
	               String writer = rs.getString(3);
	               String content = rs.getString(4);
	               String ipAddr = rs.getString(5);
	               Timestamp writeDate = rs.getTimestamp(6);
	               
	               result.add(new ComDTO(comSeq, boardSeq, writer, content, ipAddr, writeDate));
	            }
	            return result;
	         }
	      }
	   }
	  //댓글 페이지 네비게이터 - 자유
	  public String getPageNaviCmtFree(int currentPage, int boardSeq) throws Exception {

			int recordTotalCount = this.getCmtSize(boardSeq);
		
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
			if(needPrev) {sb.append("<a href = '${pageContext.request.contextPath}/freeDetail.bo?cpage="+(startNavi -1)+"'> < </a>");}
			// 값을 서버에서 만들어서 + 붙여서 프론트로 나가는거니까 
			// 서버에서 이미 앵커태그가 붙어야한다 
			for(int i = startNavi; i <= endNavi; i++) {
				sb.append("<a href = 'freeDetail.bo?seq="+boardSeq+"&cpage="+i+"'> ");
				sb.append(i + " ");
				sb.append("</a>");
			}
			if(needNext) sb.append("<a href = '${pageContext.request.contextPath}/freeDetail.bo?cpage="+(endNavi+1)+"'> > </a>");

			return sb.toString();
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		}
	  
	  //댓글 페이지 네비게이터 - 질문
	  public String getPageNaviCmtQuestion(int currentPage, int boardSeq) throws Exception {

			int recordTotalCount = this.getCmtSize(boardSeq);
		
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
			if(needPrev) {sb.append("<a href = '${pageContext.request.contextPath}/questionDetail.bo?cpage="+(startNavi -1)+"'> < </a>");}
			// 값을 서버에서 만들어서 + 붙여서 프론트로 나가는거니까 
			// 서버에서 이미 앵커태그가 붙어야한다 
			for(int i = startNavi; i <= endNavi; i++) {
				sb.append("<a href = 'questionDetail.bo?seq="+boardSeq+"&cpage="+i+"'> ");
				sb.append(i + " ");
				sb.append("</a>");
			}
			if(needNext) sb.append("<a href = '${pageContext.request.contextPath}/questionDetail.bo?cpage="+(endNavi+1)+"'> > </a>");

			return sb.toString();
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		}
	  //댓글 페이지 네비게이터 - 질문(관리자)
	  public String getPageNaviCmtQuestionAdmin(int currentPage, int boardSeq) throws Exception {

			int recordTotalCount = this.getCmtSize(boardSeq);
		
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
			if(needPrev) {sb.append("<a href = '${pageContext.request.contextPath}/adminQuestionDetailView.admin?cpage="+(startNavi -1)+"'> < </a>");}
			// 값을 서버에서 만들어서 + 붙여서 프론트로 나가는거니까 
			// 서버에서 이미 앵커태그가 붙어야한다 
			for(int i = startNavi; i <= endNavi; i++) {
				sb.append("<a href = 'adminQuestionDetailView.admin?seq="+boardSeq+"&cpage="+i+"'> ");
				sb.append(i + " ");
				sb.append("</a>");
			}
			if(needNext) sb.append("<a href = '${pageContext.request.contextPath}/adminQuestionDetailView.admin?cpage="+(endNavi+1)+"'> > </a>");

			return sb.toString();
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		}
	  
	  //댓글 페이지 네비게이터 - 자유(관리자)
	  public String getPageNaviCmtFreeAdmin(int currentPage, int boardSeq) throws Exception {

			int recordTotalCount = this.getCmtSize(boardSeq);
		
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
			if(needPrev) {sb.append("<a href = '${pageContext.request.contextPath}/adminFreeDetailView.admin?cpage="+(startNavi -1)+"'> < </a>");}
			// 값을 서버에서 만들어서 + 붙여서 프론트로 나가는거니까 
			// 서버에서 이미 앵커태그가 붙어야한다 
			for(int i = startNavi; i <= endNavi; i++) {
				sb.append("<a href = 'adminFreeDetailView.admin?seq="+boardSeq+"&cpage="+i+"'> ");
				sb.append(i + " ");
				sb.append("</a>");
			}
			if(needNext) sb.append("<a href = '${pageContext.request.contextPath}/adminFreeDetailView.admin?cpage="+(endNavi+1)+"'> > </a>");

			return sb.toString();
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		}
	  
	  //글 시퀀스 주면 댓글 몇개인지 꺼내는 메서드
	  public int getCmtSize(int boardSeq)throws Exception{
		  String sql = "select count(*) from comments where boardSeq =?";
		  try(
				Connection con = getConnetion();
				  PreparedStatement pstat = con.prepareStatement(sql);
				  ){
			  pstat.setInt(1, boardSeq);
			  try(
					ResultSet rs = pstat.executeQuery();
					  ){
				  rs.next();
				  return rs.getInt(1);
			  }
		  }
	  }
	   
	   public int insert(ComDTO dto) throws Exception{
	      String sql = "insert into comments values(commentsSeq.nextval, ?, ?, ?, ?, sysdate)";
	      try(
	            Connection con = this.getConnetion();
	            PreparedStatement pstat = con.prepareStatement(sql);
	            ){
	         
	         pstat.setInt(1, dto.getBoardSeq());
	         pstat.setString(2, dto.getWriter());
	         pstat.setString(3, dto.getContent());
	         pstat.setString(4, dto.getIpAddr());
	         
	         
	         int result = pstat.executeUpdate();
	         con.commit();
	         return result;
	      }
	   }
	   

	   public int update(ComDTO dto) throws Exception{
	      String sql = "update comments set content=?, writeDate=sysdate where comSeq=?";
	      try(
	            Connection con = this.getConnetion();
	            PreparedStatement pstat = con.prepareStatement(sql);
	            ){
	         pstat.setString(1, dto.getContent());
	         pstat.setInt(2, dto.getComSeq());
	         int result = pstat.executeUpdate();
	         con.commit();
	         return result;
	      }
	   }
	   
	   public int delete(int comSeq) throws Exception{
	      String sql = "delete from comments where seq=?";
	      try(
	            Connection con = this.getConnetion();
	            PreparedStatement pstat = con.prepareStatement(sql);
	            ){
	         pstat.setInt(1, comSeq);
	         int result = pstat.executeUpdate();
	         con.commit();
	         return result;
	      }
	   }
	   
	   //게시글 삭제 메서드 실행하고 댓글 삭제 메서드 실행하기
	   public int deleteNoBoard(int boardSeq) throws Exception{
	      String sql = "delete from comments where boardSeq=?";
	      try(
	            Connection con = this.getConnetion();
	            PreparedStatement pstat = con.prepareStatement(sql);
	            ){
	         pstat.setInt(1, boardSeq);
	         int result = pstat.executeUpdate();
	         con.commit();
	         return result;
	      }
	   }
	   
	   
	   //내가 쓴 가장 최신 댓글
	    public ComDTO selectNew(int board_num, String writer) throws Exception{

	         String sql = "select * from (select comments.*, row_number() over(order by seq desc) rown from comments where  boardSeq=?) where writer=? and rown = 1";
	            
	            try(
	                  Connection con = this.getConnetion();
	                  PreparedStatement pstat = con.prepareStatement(sql);
	                  ){
	               pstat.setInt(1, board_num);
	               pstat.setString(2, writer);
	               try(

	               ResultSet rs = pstat.executeQuery();
	               ){
	            rs.next();
	            int comSeq = rs.getInt(1);
	            int boardSeq = rs.getInt(2);
	            String writer2 = rs.getString(3);
	            String content = rs.getString(4);
	            String ipAddr = rs.getString(5);
	            Timestamp writeDate = rs.getTimestamp(6);
	            
	            ComDTO result = new ComDTO(comSeq, boardSeq, writer2, content, ipAddr, writeDate);
	            return result;            
	         }
	      }
	   
	   }

}
