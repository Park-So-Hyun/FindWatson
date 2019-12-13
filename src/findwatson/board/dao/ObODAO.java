package findwatson.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import findwatson.admin.dto.BanDTO;
import findwatson.board.dto.ObODTO;
import findwatson.configuration.Configuration;
import findwatson.utils.Statics;

public class ObODAO {
	private static ObODAO instance;

	private ObODAO() {}

	private Connection getConnection() throws Exception {
		return Statics.bds.getConnection();
	}

	public synchronized static ObODAO getInstance() {
		if (instance == null) {
			instance = new ObODAO();
		}
		return instance;
	}

	// 1:1 문의 게시판 내의 총 글의 개수
	public int recordObOTotalCount() throws Exception {
		String sql = "select count(seq) from oneByOne";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}

	// 1:1 문의 페이지 네비게이터
	public String getObOPageNav(int currentPage) throws Exception {
		// 게시판 내의 총 글의 개수
		int recordTotalCount = this.recordObOTotalCount();
		// 한 페이지에 몇개의 글을 보여줄건지
		// int recordCountPerPage = 10;
		// 한 페이지에서 몇개의 네비게이터를 보여줄건지
		// int naviCountPerPage = 10;
		// 총 몇개의 페이지인지
		int pageTotalCount = 0;
		if (recordTotalCount % Configuration.recordCountPerPage > 0) {
			// 총 글의 개수를 페이지당 보여줄 개수로 나누었을 때, 나머지가 생기면 총페이지의 개수 +1
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage + 1;
		} else {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage;
		}

		// 현재 페이지 값이 비정상 값일 때, 조정하는 보안 코드
		if (currentPage < 1) {
			currentPage = 1;
		} else if (currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}

		// 현재 내가 위치하고 있는 페이지에 따라 네비게이터 시작 페이지 값을 구하는 공식
		int startNavi = ((currentPage - 1) / Configuration.naviCountPerPage) * Configuration.naviCountPerPage + 1;
		int endNavi = startNavi + Configuration.naviCountPerPage - 1;

		// 페이지 끝값이 비정상 값일 때, 조정하는 보안 코드
		if (endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		System.out.println("현재 페이지 번호 : " + currentPage);
		System.out.println("네비게이터 시작 번호 : " + startNavi);
		System.out.println("네비게이터 끝 번호 : " + endNavi);

		boolean needPrev = true;
		boolean needNext = true;

		if (currentPage == 1) {
			needPrev = false;
		}
		if (currentPage == pageTotalCount) {
			needNext = false;
		}
		if(currentPage == 0) {
			needPrev = false;
			needNext = false;
		}
		StringBuilder sb = new StringBuilder();

		if (needPrev) {
			sb.append("<a href='adminOneByOne.admin?cpage=" + (currentPage - 1) + "'> < </a>");
		}

		for (int i = startNavi; i <= endNavi; i++) {
			sb.append("<a href='adminOneByOne.admin?cpage=" + i + "'>");
			sb.append(i + " ");
			sb.append("</a>");
		}
		if (needNext) {
			sb.append("<a href='adminOneByOne.admin?cpage=" + (currentPage + 1) + "'> > </a>");
		}
		return sb.toString();
	}

	// 1:1 문의 페이지 목록 10개씩
	public List<ObODTO> ObOByPage(int start, int end) throws Exception {
		String sql = "select * from" + "(select oneByOne.*, row_number() over (order by seq desc) as rown from oneByOne)"
				+ " where rown between ? and ?";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setInt(1, start);
			pstat.setInt(2, end);
			try (ResultSet rs = pstat.executeQuery();) {
				List<ObODTO> result = new ArrayList<>();
				while (rs.next()) {
					int seq = rs.getInt(1);
					String writer = rs.getString(2);
					String anserOK = rs.getString(3);
					if(anserOK.contentEquals("Y")) {
						anserOK = "답변완료"; 
					} else {
						anserOK = "답변대기중";
					}
					String header = rs.getString(4);
					String title = rs.getString(5);
					String content = rs.getString(6);
					String tiAddr = rs.getString(7);
					Timestamp writeDate = rs.getTimestamp(8);

					ObODTO dto = new ObODTO(seq,writer,anserOK,header,title,content,tiAddr,writeDate);
					result.add(dto);
				}
				return result;
			}
		}
	}
	// 1:1 문의게시판 디테일 뷰
	public ObODTO getObOBySeq(int ObOSeq) throws Exception {
		String sql = "select * from oneByOne where seq =?";
		try (Connection con = getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setInt(1, ObOSeq);
			try (ResultSet rs = pstat.executeQuery();) {
				rs.next();
				int seq = rs.getInt(1);
				String writer = rs.getString(2);
				String anserOK = rs.getString(3);
				if(anserOK.contentEquals("Y")) {
					anserOK = "답변완료"; 
				} else {
					anserOK = "답변대기중";
				}
				String header = rs.getString(4);
				String title = rs.getString(5);
				String content = rs.getString(6);
				String tiAddr = rs.getString(7);
				Timestamp writeDate = rs.getTimestamp(8);

				ObODTO dto = new ObODTO(seq, writer, anserOK, header, title, content, tiAddr, writeDate);
				return dto;
			}
		}
	}







	// 1:1 문의 게시판 내의 총 글의 개수   회원 자기목록만
	public int recordMyObOTotalCount(String id) throws Exception {
		String sql = "select count(seq) from oneByOne where writer = ?";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setString(1, id);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				rs.next();
				return rs.getInt(1);
			}
		}
	}

	// 1:1 문의 페이지 네비게이터        회원 자기목록만
	public String getMyObOPageNav(int currentPage,String id) throws Exception {
		// 게시판 내의 총 글의 개수
		int recordTotalCount = this.recordMyObOTotalCount(id);
		// 한 페이지에 몇개의 글을 보여줄건지
		// int recordCountPerPage = 10;
		// 한 페이지에서 몇개의 네비게이터를 보여줄건지
		// int naviCountPerPage = 10;
		// 총 몇개의 페이지인지
		int pageTotalCount = 0;
		if (recordTotalCount % Configuration.recordCountPerPage > 0) {
			// 총 글의 개수를 페이지당 보여줄 개수로 나누었을 때, 나머지가 생기면 총페이지의 개수 +1
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage + 1;
		} else {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage;
		}

		// 현재 페이지 값이 비정상 값일 때, 조정하는 보안 코드
		if (currentPage < 1) {
			currentPage = 1;
		} else if (currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}

		// 현재 내가 위치하고 있는 페이지에 따라 네비게이터 시작 페이지 값을 구하는 공식
		int startNavi = ((currentPage - 1) / Configuration.naviCountPerPage) * Configuration.naviCountPerPage + 1;
		int endNavi = startNavi + Configuration.naviCountPerPage - 1;

		// 페이지 끝값이 비정상 값일 때, 조정하는 보안 코드
		if (endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		System.out.println("현재 페이지 번호 : " + currentPage);
		System.out.println("네비게이터 시작 번호 : " + startNavi);
		System.out.println("네비게이터 끝 번호 : " + endNavi);

		boolean needPrev = true;
		boolean needNext = true;

		if (currentPage == 1) {
			needPrev = false;
		}
		if (currentPage == pageTotalCount) {
			needNext = false;
		}
		StringBuilder sb = new StringBuilder();

		if (needPrev) {
			sb.append("<a href='mypageOneByOneList.member?cpage=" + (currentPage - 1) + "'> < </a>");
		}

		for (int i = startNavi; i <= endNavi; i++) {
			sb.append("<a href='mypageOneByOneList.member?cpage=" + i + "'>");
			sb.append(i + " ");
			sb.append("</a>");
		}
		if (needNext) {
			sb.append("<a href='mypageOneByOneList.member?cpage=" + (currentPage + 1) + "'> > </a>");
		}
		return sb.toString();
	}

	// 1:1 문의 페이지 목록 10개씩           회원 자기목록만
	public List<ObODTO> myObOByPage(int start, int end, String id) throws Exception {
		String sql = "select * from" + "(select oneByOne.*, row_number() over (order by seq desc) as rown from oneByOne where writer = ?)"
				+ " where rown between ? and ?";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setString(1, id);
			pstat.setInt(2, start);
			pstat.setInt(3, end);
			try (ResultSet rs = pstat.executeQuery();) {
				List<ObODTO> result = new ArrayList<>();
				while (rs.next()) {
					int seq = rs.getInt(1);
					String writer = rs.getString(2);
					String anserOK = rs.getString(3);
					if(anserOK.contentEquals("Y")) {
						anserOK = "답변완료"; 
					} else {
						anserOK = "답변대기중";
					}
					String header = rs.getString(4);
					String title = rs.getString(5);
					String content = rs.getString(6);
					String tiAddr = rs.getString(7);
					Timestamp writeDate = rs.getTimestamp(8);

					ObODTO dto = new ObODTO(seq,writer,anserOK,header,title,content,tiAddr,writeDate);
					result.add(dto);
				}
				return result;
			}
		}
	}
}
