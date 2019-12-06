package findwatson.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import findwatson.admin.dto.BanDTO;
import findwatson.admin.dto.ExpertDTO;
import findwatson.admin.dto.HListDTO;
import findwatson.admin.dto.NoticeDTO;
import findwatson.board.dto.BoardDTO;
import findwatson.configuration.Configuration;

public class ManagerDAO {
	private static ManagerDAO instance;
	private BasicDataSource bds = new BasicDataSource();
	//DBCP
	private ManagerDAO () {
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		bds.setUsername("watson");
		bds.setPassword("watson");
		bds.setInitialSize(30);
	}
	public synchronized static ManagerDAO getInstance() {
		if(instance == null){									
			instance = new ManagerDAO();
		}
		return instance;
	}
	//Connection
	private Connection getConnection() throws Exception{
		return bds.getConnection();
	}
	
	// 병원 상세 정보
	public HListDTO hosptInfo(int num)  throws Exception{
		String sql = "select * from hosptList where seq=?";
		try ( Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){ 
			pstat.setInt(1,num);
			try(ResultSet rs = pstat.executeQuery();){

				HListDTO dto = null;
				if(rs.next()) {
					int seq = rs.getInt(1);
					String hosptName = rs.getString(2);
					String postcode = rs.getString(3);
					String city = rs.getString(4);
					String gu = rs.getString(5);
					String phone = rs.getString(6);
					String homepage = rs.getString(7);
					String img = rs.getString(8);
					String medicalAnimal = rs.getString(9);
					String openTime = rs.getString(10);
					Timestamp registDate = rs.getTimestamp(11);
					int viewCount = rs.getInt(12);

					medicalAnimal = (medicalAnimal).replace(";",",");
					dto = new HListDTO(seq, hosptName, postcode, city, gu,
							phone, homepage, img, medicalAnimal, openTime, registDate, viewCount);
				}
				return dto; 
			}
		}
	}
	
	//병원목록 10개씩
	public List<HListDTO> hosptListByPage(int start, int end) throws Exception{
		String sql = "select * from"
				+ "(select hosptList.*, row_number() over (order by seq desc) as rown from hosptList)"
				+ " where rown between ? and ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, start);
			pstat.setInt(2, end);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				List<HListDTO> result = new ArrayList<>();
				while(rs.next()) {
					int seq = rs.getInt(1);
					String hosptName = rs.getString(2);
					String postcode = rs.getString(3);
					String city = rs.getString(4);
					String gu = rs.getString(5);
					String phone = rs.getString(6);
					String homepage = rs.getString(7);
					String img = rs.getString(8);
					String medicalAnimal = rs.getString(9);
					String openTime = rs.getString(10);
					Timestamp registDate = rs.getTimestamp(11);
					int viewCount = rs.getInt(12);
					HListDTO dto = new HListDTO(seq, hosptName, postcode, city, gu,
							phone, homepage, img, medicalAnimal, openTime, registDate, viewCount);
					result.add(dto);
				}
				return result;
			}
		}
	}
	//병원목록 총 글의 개수
	public int recordHosptListTotalCount () throws Exception {
		String sql = "select count(seq) from hosptList";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//병원목록 페이지 네비게이터
	public String getHosptListPageNav(int currentPage) throws Exception {
		//게시판 내의 총 글의 개수
		int recordTotalCount = this.recordHosptListTotalCount();
		//한 페이지에 몇개의 글을 보여줄건지
		//int recordCountPerPage = 10;
		//한 페이지에서 몇개의 네비게이터를 보여줄건지
		//int naviCountPerPage = 10;
		//총 몇개의 페이지인지
		int pageTotalCount = 0;
		if(recordTotalCount % Configuration.recordCountPerPage > 0) {
			//총 글의 개수를 페이지당 보여줄 개수로 나누었을 때, 나머지가 생기면 총페이지의 개수 +1
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage + 1;
		}else {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage;
		}

		//현재 페이지 값이 비정상 값일 때, 조정하는 보안 코드
		if(currentPage < 1) {
			currentPage = 1;
		}else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}

		//현재 내가 위치하고 있는 페이지에 따라 네비게이터 시작 페이지 값을 구하는 공식
		int startNavi = ((currentPage-1) / Configuration.naviCountPerPage) * Configuration.naviCountPerPage + 1;
		int endNavi = startNavi + Configuration.naviCountPerPage - 1;

		//페이지 끝값이 비정상 값일 때, 조정하는 보안 코드
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		System.out.println("현재 페이지 번호 : " + currentPage);
		System.out.println("네비게이터 시작 번호 : " + startNavi);
		System.out.println("네비게이터 끝 번호 : " + endNavi);

		boolean needPrev = true;
		boolean needNext = true;

		if(currentPage == 1) {
			needPrev = false;
		}
		if(currentPage == pageTotalCount) {
			needNext = false;
		}
		StringBuilder sb = new StringBuilder();

		if(needPrev) {sb.append("<a href='hosptInfoList.admin?cpage="+(currentPage-1)+"'> < </a>");}

		for(int i = startNavi; i <= endNavi;i++) {
			sb.append("<a href='hosptInfoList.admin?cpage="+i+"'>");
			sb.append(i + " ");
			sb.append("</a>");
		}
		if(needNext) {sb.append("<a href='hosptInfoList.admin?cpage="+(currentPage+1)+"'> > </a>");}
		return sb.toString();
	}
	//병원 정보 등록
	public int insertHospital(HListDTO dto) throws Exception{
		String sql = "insert into hosptList values(hosptListSeq.nextval,?,?,?,?,?,?,?,?,?,sysdate,?)";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getHosptName());
			pstat.setString(2, dto.getPostcode());
			pstat.setString(3, dto.getAddress1());
			pstat.setString(4, dto.getAddress2());
			pstat.setString(5, dto.getPhone());
			pstat.setString(6, dto.getHomepage());
			pstat.setString(7, dto.getImg());
			pstat.setString(8, dto.getMedicalAnimal());
			pstat.setString(9, dto.getOpenTime());
			pstat.setInt(10, dto.getViewCount());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	//병원정보 수정
	public int modifyHospital (HListDTO dto, int seq)throws Exception {
		String sql = "update hosptList set HosptName=?,postcode=?,address1=?,address2=?,phone=?,homepage=?,img=?,medicalAnimal=?,openTime=?,registDate=?,viewCount=? where seq=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getHosptName());
			pstat.setString(2, dto.getPostcode());
			pstat.setString(3, dto.getAddress1());
			pstat.setString(3, dto.getAddress2());
			pstat.setString(4, dto.getPhone());
			pstat.setString(5, dto.getHomepage());
			pstat.setString(7, dto.getImg());
			pstat.setString(8, dto.getMedicalAnimal());
			pstat.setString(9, dto.getOpenTime());
			pstat.setTimestamp(10, dto.getRegistDate());
			pstat.setInt(11, dto.getViewCount());
			pstat.setInt(13, seq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	//병원정보 삭제
	public int delHospital(int seq) throws Exception{
		String sql = "delete hosptList where seq = ?";
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
	//공지사항 목록 10개씩
	public List<NoticeDTO> noticeListByPage(int start, int end) throws Exception{
		String sql = "select * from"
				+ "(select notice.*, row_number() over (order by seq) as rown from notice)"
				+ " where rown between ? and ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, start);
			pstat.setInt(2, end);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				List<NoticeDTO> result = new ArrayList<>();
				while(rs.next()) {
					int seq = rs.getInt(1);
					String title = rs.getString(2);
					String content = rs.getString(3);
					Timestamp writeDate = rs.getTimestamp(4);
					int viewCount = rs.getInt(5);
					NoticeDTO dto = new NoticeDTO (seq,title,content,writeDate,viewCount);
					result.add(dto);
				}
				return result;
			}
		}
	}
	//공지사항목록 총 글의 개수
	public int recordNoticeListTotalCount () throws Exception {
		String sql = "select count(seq) from notice";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//공지사항목록 페이지 네비게이터
	public String getNoticeListPageNav(int currentPage) throws Exception {
		//게시판 내의 총 글의 개수
		int recordTotalCount = this.recordNoticeListTotalCount();
		//한 페이지에 몇개의 글을 보여줄건지
		//int recordCountPerPage = 10;
		//한 페이지에서 몇개의 네비게이터를 보여줄건지
		//int naviCountPerPage = 10;
		//총 몇개의 페이지인지
		int pageTotalCount = 0;
		if(recordTotalCount % Configuration.recordCountPerPage > 0) {
			//총 글의 개수를 페이지당 보여줄 개수로 나누었을 때, 나머지가 생기면 총페이지의 개수 +1
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage + 1;
		}else {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage;
		}

		//현재 페이지 값이 비정상 값일 때, 조정하는 보안 코드
		if(currentPage < 1) {
			currentPage = 1;
		}else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}

		//현재 내가 위치하고 있는 페이지에 따라 네비게이터 시작 페이지 값을 구하는 공식
		int startNavi = ((currentPage-1) / Configuration.naviCountPerPage) * Configuration.naviCountPerPage + 1;
		int endNavi = startNavi + Configuration.naviCountPerPage - 1;

		//페이지 끝값이 비정상 값일 때, 조정하는 보안 코드
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		System.out.println("현재 페이지 번호 : " + currentPage);
		System.out.println("네비게이터 시작 번호 : " + startNavi);
		System.out.println("네비게이터 끝 번호 : " + endNavi);

		boolean needPrev = true;
		boolean needNext = true;

		if(currentPage == 1) {
			needPrev = false;
		}
		if(currentPage == pageTotalCount) {
			needNext = false;
		}
		StringBuilder sb = new StringBuilder();

		if(needPrev) {sb.append("<a href='adminBoardNotice.manager?cpage="+(currentPage-1)+"'> < </a>");}

		for(int i = startNavi; i <= endNavi;i++) {
			sb.append("<a href='adminBoardNotice.manager?cpage="+i+"'>");
			sb.append(i + " ");
			sb.append("</a>");
		}
		if(needNext) {sb.append("<a href='adminBoardNotice.manager?cpage="+(currentPage+1)+"'> > </a>");}
		return sb.toString();
	}
	//공지사항 쓰기
	public int writeNotice(NoticeDTO dto) throws Exception{
		String sql = "insert into notice values(notice_seq.nextval,?,?,?,?)";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getContent());
			pstat.setTimestamp(3, dto.getWriteDate());
			pstat.setInt(4, dto.getViewCount());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	//공지사항 수정
	public int modifyNotice (NoticeDTO dto, int seq)throws Exception {
		String sql = "update notice set title=?,content=? where seq=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getContent());
			pstat.setInt(3, seq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	//공지사항 삭제
	public int delNotice(int seq) throws Exception {
		String sql = "delete from notice where seq=?";
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
	//1:1문의 게시글 목록
	public List<BoardDTO> oneByOneList() throws Exception{
		String sql = "select * from OneByOne";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			List<BoardDTO> list = new ArrayList<>();
			while (rs.next()) {
				int seq = rs.getInt(1);
				String writer = rs.getString(2);
				String header = rs.getString(3);
				String animalHeader = rs.getString(4);
				String title = rs.getString(5);
				String content = rs.getString(6);
				String ipAddr = rs.getString(7);
				int viewCount = rs.getInt(8);
				Timestamp writeDate = rs.getTimestamp(9);
				BoardDTO dto = new BoardDTO(seq,writer,header,animalHeader,title,content,ipAddr,viewCount,writeDate);
				list.add(dto);
			}
			return list;
		}
	}
	//전문가목록 10개씩
	public List<ExpertDTO> expertListByPage(int start, int end) throws Exception{
		String sql = "select * from"
				+ "(select expert.*, row_number() over (order by seq) as rown from expert)"
				+ " where rown between ? and ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, start);
			pstat.setInt(2, end);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				List<ExpertDTO> result = new ArrayList<>();
				while(rs.next()) {
					int seq = rs.getInt(1);
					String writer = rs.getString(2);
					String title = rs.getString(3);
					String content = rs.getString(4);
					Timestamp writeDate = rs.getTimestamp(5);
					int viewCount = rs.getInt(6);
					ExpertDTO dto = new ExpertDTO(seq,writer,title,content,writeDate,viewCount);
					result.add(dto);
				}
				return result;
			}
		}
	}
	//전문가목록 총 글의 개수
	public int recordExpertListTotalCount () throws Exception {
		String sql = "select count(seq) from expert";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//공지사항목록 페이지 네비게이터
	public String getExpertListPageNav(int currentPage) throws Exception {
		//게시판 내의 총 글의 개수
		int recordTotalCount = this.recordExpertListTotalCount();
		//한 페이지에 몇개의 글을 보여줄건지
		//int recordCountPerPage = 10;
		//한 페이지에서 몇개의 네비게이터를 보여줄건지
		//int naviCountPerPage = 10;
		//총 몇개의 페이지인지
		int pageTotalCount = 0;
		if(recordTotalCount % Configuration.recordCountPerPage > 0) {
			//총 글의 개수를 페이지당 보여줄 개수로 나누었을 때, 나머지가 생기면 총페이지의 개수 +1
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage + 1;
		}else {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage;
		}

		//현재 페이지 값이 비정상 값일 때, 조정하는 보안 코드
		if(currentPage < 1) {
			currentPage = 1;
		}else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}

		//현재 내가 위치하고 있는 페이지에 따라 네비게이터 시작 페이지 값을 구하는 공식
		int startNavi = ((currentPage-1) / Configuration.naviCountPerPage) * Configuration.naviCountPerPage + 1;
		int endNavi = startNavi + Configuration.naviCountPerPage - 1;

		//페이지 끝값이 비정상 값일 때, 조정하는 보안 코드
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		System.out.println("현재 페이지 번호 : " + currentPage);
		System.out.println("네비게이터 시작 번호 : " + startNavi);
		System.out.println("네비게이터 끝 번호 : " + endNavi);

		boolean needPrev = true;
		boolean needNext = true;

		if(currentPage == 1) {
			needPrev = false;
		}
		if(currentPage == pageTotalCount) {
			needNext = false;
		}
		StringBuilder sb = new StringBuilder();

		if(needPrev) {sb.append("<a href='adminBoardExpert.manager?cpage="+(currentPage-1)+"'> < </a>");}

		for(int i = startNavi; i <= endNavi;i++) {
			sb.append("<a href='adminBoardExpert.manager?cpage="+i+"'>");
			sb.append(i + " ");
			sb.append("</a>");
		}
		if(needNext) {sb.append("<a href='adminBoardExpert.manager?cpage="+(currentPage+1)+"'> > </a>");}
		return sb.toString();
	}
	//1:1문의 게시글 삭제
	public int delOneByOne(int seq) throws Exception {
		String sql = "delete from OneByOne where seq=?";
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
	//전문가 Q&A 글쓰기
	public int writeExpert(ExpertDTO dto) throws Exception{
		String sql = "insert into Expert values(expertSeq.nextval,?,?,?,?,?)";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getWriter());
			pstat.setString(2, dto.getTitle());
			pstat.setString(3, dto.getContent());
			pstat.setTimestamp(4, dto.getWriteDate());
			pstat.setInt(5, dto.getViewCount());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	//전문가 Q&A 목록
	public List<ExpertDTO> expertList() throws Exception{
		String sql = "select * from expert";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			List<ExpertDTO> list = new ArrayList<>();
			while (rs.next()) {
				int seq = rs.getInt(1);
				String writer = rs.getString(2);
				String title = rs.getString(3);
				String content = rs.getString(4);
				Timestamp writeDate = rs.getTimestamp(5);
				int viewCount = rs.getInt(6);
				ExpertDTO dto = new ExpertDTO(seq,writer,title,content,writeDate,viewCount);
				list.add(dto);
			}
			return list;
		}
	}
	//전문가 Q&A 수정
	public int modifyExpert (ExpertDTO dto, int seq)throws Exception {
		String sql = "update expert set title=?,content=? where seq=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getContent());
			pstat.setInt(3, seq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	//전문가 Q&A 삭제
	public int delExpert(int seq) throws Exception {
		String sql = "delete from expert where seq=?";
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
	//질문게시판 목록
	public List<BoardDTO> qBoardList() throws Exception{
		String sql = "select * from board where header='질문'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			List<BoardDTO> list = new ArrayList<>();
			while (rs.next()) {
				int seq = rs.getInt(1);
				String writer = rs.getString(2);
				String header = rs.getString(3);
				String animalHeader = rs.getString(4);
				String title = rs.getString(5);
				String content = rs.getString(6);
				String ipAddr = rs.getString(7);
				int viewCount = rs.getInt(8);
				Timestamp writeDate = rs.getTimestamp(9);
				BoardDTO dto = new BoardDTO(seq,writer,header,animalHeader,title,content,ipAddr,viewCount,writeDate);
				list.add(dto);
			}
			return list;
		}
	}
	//질문게시판 글 아이디로 찾기
	public List<BoardDTO> qBoardById (String id) throws Exception{
		String sql = "select * from board where id = ?, header='질문'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, id);
			try(
					ResultSet rs = pstat.executeQuery();
					){	
				List<BoardDTO> list = new ArrayList<>();
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
					BoardDTO dto = new BoardDTO(seq,writer,header,animalHeader,title,content,ipAddr,viewCount,writeDate);
					list.add(dto);

				}
				return list;
			}
		}
	}
	//커뮤니티관리 게시판 글 삭제
	public int delBoard(int seq) throws Exception {
		String sql = "delete from board where seq=?";
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
	//자유게시판 목록
	public List<BoardDTO> fBoardList() throws Exception{
		String sql = "select * from board where header='자유'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			List<BoardDTO> list = new ArrayList<>();
			while (rs.next()) {
				int seq = rs.getInt(1);
				String writer = rs.getString(2);
				String header = rs.getString(3);
				String animalHeader = rs.getString(4);
				String title = rs.getString(5);
				String content = rs.getString(6);
				String ipAddr = rs.getString(7);
				int viewCount = rs.getInt(8);
				Timestamp writeDate = rs.getTimestamp(9);
				BoardDTO dto = new BoardDTO(seq,writer,header,animalHeader,title,content,ipAddr,viewCount,writeDate);
				list.add(dto);
			}
			return list;
		}
	}
	//자유게시판 글 아이디로 찾기
	public List<BoardDTO> fBoardById (String id) throws Exception{
		String sql = "select * from board where id = ?, header='자유'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, id);
			try(
					ResultSet rs = pstat.executeQuery();
					){	
				List<BoardDTO> list = new ArrayList<>();
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
					BoardDTO dto = new BoardDTO(seq,writer,header,animalHeader,title,content,ipAddr,viewCount,writeDate);
					list.add(dto);

				}
				return list;
			}
		}
	}
}
