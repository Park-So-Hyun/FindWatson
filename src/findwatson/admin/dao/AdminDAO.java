package findwatson.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import findwatson.admin.dto.BanDTO;
import findwatson.admin.dto.ChartDTO;
import findwatson.admin.dto.ExpertDTO;
import findwatson.admin.dto.HListDTO;
import findwatson.admin.dto.NoticeDTO;
import findwatson.admin.dto.OneByOneCommentDTO;
import findwatson.board.dto.BoardDTO;
import findwatson.board.dto.ObODTO;
import findwatson.configuration.Configuration;
import findwatson.member.dto.MemberDTO;
import findwatson.review.dto.ReviewDTO;

public class AdminDAO {
	private static AdminDAO instance;
	private BasicDataSource bds = new BasicDataSource();
	//DBCP
	private AdminDAO () {
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		bds.setUsername("watson");
		bds.setPassword("watson");
		bds.setInitialSize(30);
	}
	public synchronized static AdminDAO getInstance() {
		if(instance == null){									
			instance = new AdminDAO();
		}
		return instance;
	}
	//Connection
	private Connection getConnection() throws Exception{
		return bds.getConnection();
	}
	//관리자 로그인
	public boolean adminLogin(String id,String pw) throws Exception{
		String sql = "select * from admin where id = ? and pw = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, id);
			pstat.setString(2, pw);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				boolean result = rs.next();
				return result;
			}
		}
	}
	//관리자 비밀번호 변경
	public int adminInfoPwUpdate(String id, String pw) throws Exception{
		String sql = "update admin set pw=? where id=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, pw);
			pstat.setString(2, id);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	//회원목록정보
	public List<MemberDTO> selectAll() throws Exception{
		String sql = "select * from member";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			List<MemberDTO> memberList = new ArrayList<>();
			while(rs.next()) {
				String id = rs.getString(1);
				String pw = rs.getString(2);
				String name = rs.getString(3);
				String birth = rs.getString(4);
				String gender = rs.getString(5);
				String email = rs.getString(6);
				String phone = rs.getString(7);
				String postcode = rs.getString(8);
				String address1 = rs.getString(9);
				String address2 = rs.getString(10);
				String lovePet = rs.getString(11);
				String signPath = rs.getString(12);
				Timestamp date = rs.getTimestamp(13);
				String ipAddr = rs.getString(14);
				MemberDTO dto = new MemberDTO(id,pw,name,birth,gender,email,phone,postcode,address1,address2,lovePet,signPath,date,ipAddr);
				memberList.add(dto);
			}
			return memberList;
		}
	}
	//아이디로 회원 찾기
	public List<MemberDTO> selectById (String id) throws Exception{
		String sql = "select * from member where id like ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, id);
			try(
					ResultSet rs = pstat.executeQuery();
					){	
				List<MemberDTO> memberList = new ArrayList<>();
				while(rs.next()) {

					String pw = rs.getString(2);
					String name = rs.getString(3);
					String birth = rs.getString(4);
					String gender = rs.getString(5);
					String email = rs.getString(6);
					String phone = rs.getString(7);
					String postcode = rs.getString(8);
					String address1 = rs.getString(9);
					String address2 = rs.getString(10);
					String lovePet = rs.getString(11);
					String signPath = rs.getString(12);
					Timestamp date = rs.getTimestamp(13);
					String ipAddr = rs.getString(14);
					MemberDTO dto = new MemberDTO(id,pw,name,birth,gender,email,phone,postcode,address1,address2,lovePet,signPath,date,ipAddr);
					memberList.add(dto);
				}
				return memberList;
			}
		}
	}
	//이름으로 회원 찾기
	public List<MemberDTO> selectByName (String name) throws Exception{
		String sql = "select * from member where name = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, name);
			try(
					ResultSet rs = pstat.executeQuery();
					){	
				List<MemberDTO> memberList = new ArrayList<>();
				while(rs.next()) {
					String id = rs.getString(1);
					String pw = rs.getString(2);

					String birth = rs.getString(4);
					String gender = rs.getString(5);
					String email = rs.getString(6);
					String phone = rs.getString(7);
					String postcode = rs.getString(8);
					String address1 = rs.getString(9);
					String address2 = rs.getString(10);
					String lovePet = rs.getString(11);
					String signPath = rs.getString(12);
					Timestamp date = rs.getTimestamp(13);
					String ipAddr = rs.getString(14);
					MemberDTO dto = new MemberDTO(id,pw,name,birth,gender,email,phone,postcode,address1,address2,lovePet,signPath,date,ipAddr);
					memberList.add(dto);
				}
				return memberList;
			}
		}
	}
	//생년월일로 회원찾기
	public List<MemberDTO> selectByBirth (String birth) throws Exception{
		String sql = "select * from member where birth = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, birth);
			try(
					ResultSet rs = pstat.executeQuery();
					){	
				List<MemberDTO> memberList = new ArrayList<>();
				while(rs.next()) {
					String id = rs.getString(1);
					String pw = rs.getString(2);
					String name = rs.getString(3);

					String gender = rs.getString(5);
					String email = rs.getString(6);
					String phone = rs.getString(7);
					String postcode = rs.getString(8);
					String address1 = rs.getString(9);
					String address2 = rs.getString(10);
					String lovePet = rs.getString(11);
					String signPath = rs.getString(12);
					Timestamp date = rs.getTimestamp(13);
					String ipAddr = rs.getString(14);
					MemberDTO dto = new MemberDTO(id,pw,name,birth,gender,email,phone,postcode,address1,address2,lovePet,signPath,date,ipAddr);
					memberList.add(dto);
				}
				return memberList;
			}
		}
	}
	//성별로 회원찾기
	public List<MemberDTO> selectByGender (String gender) throws Exception{
		String sql = "select * from member where gender = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, gender);
			try(
					ResultSet rs = pstat.executeQuery();
					){	
				List<MemberDTO> memberList = new ArrayList<>();
				while(rs.next()) {
					String id = rs.getString(1);
					String pw = rs.getString(2);
					String name = rs.getString(3);
					String birth = rs.getString(4);

					String email = rs.getString(6);
					String phone = rs.getString(7);
					String postcode = rs.getString(8);
					String address1 = rs.getString(9);
					String address2 = rs.getString(10);
					String lovePet = rs.getString(11);
					String signPath = rs.getString(12);
					Timestamp date = rs.getTimestamp(13);
					String ipAddr = rs.getString(14);
					MemberDTO dto = new MemberDTO(id,pw,name,birth,gender,email,phone,postcode,address1,address2,lovePet,signPath,date,ipAddr);
					memberList.add(dto);
				}
				return memberList;
			}
		}
	}
	//관심동물로 회원찾기
	public List<MemberDTO> selectByLovePet (String lovePet) throws Exception{
		String sql = "select * from member where lovePet = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, lovePet);
			try(
					ResultSet rs = pstat.executeQuery();
					){	
				List<MemberDTO> memberList = new ArrayList<>();
				while(rs.next()) {
					String id = rs.getString(1);
					String pw = rs.getString(2);
					String name = rs.getString(3);
					String birth = rs.getString(4);
					String gender = rs.getString(5);
					String email = rs.getString(6);
					String phone = rs.getString(7);
					String postcode = rs.getString(8);
					String address1 = rs.getString(9);
					String address2 = rs.getString(10);
					String signPath = rs.getString(12);
					Timestamp date = rs.getTimestamp(13);
					String ipAddr = rs.getString(14);
					MemberDTO dto = new MemberDTO(id,pw,name,birth,gender,email,phone,postcode,address1,address2,lovePet,signPath,date,ipAddr);
					memberList.add(dto);
				}
				return memberList;
			}
		}
	}
	//차단ip 추가
	public int insertbanIp(String id, String ipAddr, String reason) throws Exception{
		String sql = "insert into banIp values(?,?,?)";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, id);
			pstat.setString(2, ipAddr);
			pstat.setString(3, reason);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	//차단한 ip 목록
	public List<BanDTO> selectBanList () throws Exception{
		String sql = "select * from banIp";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){				
			List<BanDTO> banList = new ArrayList<>();
			while(rs.next()) {
				String id = rs.getString(1);
				String ip = rs.getString(2);
				String reason = rs.getString(3);		
				BanDTO dto = new BanDTO(id,ip,reason);
				banList.add(dto);
			}
			return banList;
		}
	}
	//차단ip목록 게시판 내의 총 글의 개수
	public int recordBanListTotalCount () throws Exception {
		String sql = "select count(id) from banIp";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//차단ip목록 페이지 네비게이터
	public String getBanListPageNav(int currentPage) throws Exception {
		//게시판 내의 총 글의 개수
		int recordTotalCount = this.recordBanListTotalCount();
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
		if(currentPage == 0) {
			needPrev = false;
			needNext = false;
		}
		StringBuilder sb = new StringBuilder();

		if(needPrev) {sb.append("<a href='adminBanList.admin?cpage="+(currentPage-1)+"'> < </a>");}

		for(int i = startNavi; i <= endNavi;i++) {
			sb.append("<a href='adminBanList.admin?cpage="+i+"'>");
			sb.append(i + " ");
			sb.append("</a>");
		}
		if(needNext) {sb.append("<a href='adminBanList.admin?cpage="+(currentPage+1)+"'> > </a>");}
		return sb.toString();
	}
	//차단ip목록 게시판 목록 10개씩
	public List<BanDTO> BanListByPage(int start, int end) throws Exception{
		String sql = "select * from"
				+ "(select banIp.*, row_number() over (order by id) as rown from banIp)"
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
				List<BanDTO> result = new ArrayList<>();
				while(rs.next()) {
					String id = rs.getString(1);
					String ip = rs.getString(2);
					String reason = rs.getString(3);
					BanDTO dto = new BanDTO(id,ip,reason);
					result.add(dto);
				}
				return result;
			}
		}
	}
	//회원목록 게시판 내의 총 글의 개수
	public int recordMemberListTotalCount () throws Exception {
		String sql = "select count(id) from member";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//회원목록 페이지 네비게이터
	public String getMemberListPageNav(int currentPage) throws Exception {
		//게시판 내의 총 글의 개수
		int recordTotalCount = this.recordMemberListTotalCount();
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
		if(currentPage == 0) {
			needPrev = false;
			needNext = false;
		}
		StringBuilder sb = new StringBuilder();

		if(needPrev) {sb.append("<a href='adminMemberList.admin?cpage="+(currentPage-1)+"'> < </a>");}

		for(int i = startNavi; i <= endNavi;i++) {
			sb.append("<a href='adminMemberList.admin?cpage="+i+"'>");
			sb.append(i + " ");
			sb.append("</a>");
		}
		if(needNext) {sb.append("<a href='adminMemberList.admin?cpage="+(currentPage+1)+"'> > </a>");}
		return sb.toString();
	}
	//회원목록 게시판 목록 10개씩
	public List<MemberDTO> MemberListByPage(int start, int end) throws Exception{
		String sql = "select * from"
				+ "(select member.*, row_number() over (order by name) as rown from member)"
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
				List<MemberDTO> result = new ArrayList<>();
				while(rs.next()) {
					String id = rs.getString(1);
					String pw = rs.getString(2);
					String name = rs.getString(3);
					String birth = rs.getString(4);
					String gender = rs.getString(5);
					String email = rs.getString(6);
					String phone = rs.getString(7);
					String postcode = rs.getString(8);
					String address1 = rs.getString(9);
					String address2 = rs.getString(10);
					String lovePet = rs.getString(11);
					String signPath = rs.getString(12);
					Timestamp date = rs.getTimestamp(13);
					String ipAddr = rs.getString(14);
					MemberDTO dto = new MemberDTO(id,pw,name,birth,gender,email,phone,postcode,address1,address2,lovePet,signPath,date,ipAddr);
					result.add(dto);
				}
				return result;
			}
		}
	}
	//회원목록에서 아이디 삭제
	public int deleteMember (String id)throws Exception{
		String sql = "delete from member where id=?";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);	
				){
			pstat.setString(1, id);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	//관리자 - 전문가 Q&A에 글작성
	public int insertToExpert(ExpertDTO dto)throws Exception{
		String sql = "insert into expert values (expertSeq.nextval,?,?,?,sysdate, 0)";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getWriter());
			pstat.setString(2, dto.getTitle());
			pstat.setString(3, dto.getContent());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}	
	}


	//관리자 - 공지에 글작성
	public int insertToNotice(ExpertDTO dto)throws Exception{
		String sql = "insert into notice values (noticeSeq.nextval,?,?,sysdate, 0)";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getContent());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	// 관리자 - 병원정보 등록
	public int insertHospitalInfo(HListDTO dto) throws Exception {
		String sql = "insert into hosptList values (hosptListSeq.nextval,?,?,?,?,?,?,?,?,?,sysdate,0)";
		try (Connection con = getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setString(1, dto.getHosptName());
			pstat.setInt(2, dto.getPostcode());
			pstat.setString(3, dto.getAddress1());
			pstat.setString(4, dto.getAddress2());
			pstat.setString(5, dto.getPhone());
			pstat.setString(6, dto.getHomepage());
			pstat.setString(7, dto.getImg());
			pstat.setString(8, dto.getMedicalAnimal());
			pstat.setString(9, dto.getOpenTime());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	// 관리자 - 병원정보 수정
	public int updateHospitalInfo(HListDTO dto) throws Exception {
		String sql = "update hosptList set hosptname =?, postCode=?,address1=?,address2=?,phone=?,homepage=?,"
				+ "img=?,medicalAnimal=?,opentime=?,registdate=sysdate where seq=?";
		try (Connection con = getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setString(1, dto.getHosptName());
			pstat.setInt(2, dto.getPostcode());
			pstat.setString(3, dto.getAddress1());
			pstat.setString(4, dto.getAddress2());
			pstat.setString(5, dto.getPhone());
			pstat.setString(6, dto.getHomepage());
			pstat.setString(7, dto.getImg());
			pstat.setString(8, dto.getMedicalAnimal());
			pstat.setString(9, dto.getOpenTime());
			pstat.setInt(10, dto.getSeq());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}


	//관리자통계 - 남자회원 수
	public int recordMemberMTotalCount () throws Exception {
		String sql = "select count(gender) from member where gender='남'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}		
	//관리자통계 - 여자회원 수
	public int recordMemberWTotalCount () throws Exception {
		String sql = "select count(gender) from member where gender='여'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//관리자통계 - 관심동물 새
	public int recordBirdTotalCount () throws Exception {
		String sql = "select count(lovepet) from member where lovepet='새'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//관리자통계 - 관심동물 물고기
	public int recordFishTotalCount () throws Exception {
		String sql = "select count(lovepet) from member where lovepet='물고기'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//관리자통계 - 관심동물 햄스터
	public int recordHamsterTotalCount () throws Exception {
		String sql = "select count(lovepet) from member where lovepet='햄스터'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//관리자통계 - 관심동물 토끼
	public int recordRabbitTotalCount () throws Exception {
		String sql = "select count(lovepet) from member where lovepet='토끼'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//관리자통계 - 관심동물 고슴도치
	public int recordDochiTotalCount () throws Exception {
		String sql = "select count(lovepet) from member where lovepet='고슴도치'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//관리자통계 - 관심동물 파충류
	public int recordReptileTotalCount () throws Exception {
		String sql = "select count(lovepet) from member where lovepet='파충류'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//관리자통계 - 관심동물 곤충류
	public int recordBugTotalCount () throws Exception {
		String sql = "select count(lovepet) from member where lovepet='곤충류'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//관리자통계 - 관심동물 기타
	public int recordOtherTotalCount () throws Exception {
		String sql = "select count(lovepet) from member where lovepet not in ('새','물고기','햄스터','토끼','고슴도치','파충류','곤충류')";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//관리자통계 - 가입경로 직접검색
	public int recordDirectSearchTotalCount () throws Exception {
		String sql = "select count(signpath) from member where signpath='''찾아조 왓슨!'' 직접검색'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//관리자통계 - 가입경로 키워드검색
	public int recordKeywordSearchTotalCount () throws Exception {
		String sql = "select count(signpath) from member where signpath='''특수 동물 병원'' 키워드 검색'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//관리자통계 - 가입경로 지인소개
	public int recordIntroduceTotalCount () throws Exception {
		String sql = "select count(signpath) from member where signpath='지인 소개'";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//관리자통계 - 가입경로 기타
	public int recordOtherSearchTotalCount () throws Exception {
		String sql = "select count(lovepet) from member where signpath not in ('''찾아조 왓슨!'' 직접검색','''특수 동물 병원'' 키워드 검색','지인 소개')";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//관리자통계 - 인기게시물 top5
	public List<ChartDTO> recordTop5 () throws Exception {
		String sql = "select * from (SELECT title,viewcount FROM board union all select title,viewcount from expert order by viewcount desc) where rownum<=5";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			List<ChartDTO> list = new ArrayList<>();
			while(rs.next()) {
				String title = rs.getString(1);
				int viewCount = rs.getInt(2);
				ChartDTO dto = new ChartDTO (title,viewCount);
				list.add(dto);
			}
			return list;
		}
	}
	//공지사항 테이블 시퀀스로 dto가져오기
	public NoticeDTO getNoticeBySeq(int noticeSeq)throws Exception{
		String sql = "select * from notice where seq =?";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, noticeSeq);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				rs.next();
				int seq = rs.getInt(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Timestamp writeDate = rs.getTimestamp(4);
				int viewCount = rs.getInt(5);
				viewCount = increNoticeView(viewCount, seq);

				NoticeDTO dto = new NoticeDTO(seq, title, content, writeDate, viewCount);
				return dto;
			}
		}
	}

	// 조회수 늘리기
	// 공지
	public int increNoticeView(int count, int seq) throws Exception {
		String sql = "update notice set viewCount = ?+ 1 where seq = ?";
		try (
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);)
		{
			pstat.setInt(1, count);
			pstat.setInt(2, seq);
			pstat.executeUpdate();
			con.commit();
			return count+1;
		}

	}

	// 전문가 큐엔에이 테이블 시퀀스로 dto가져오기
	public ExpertDTO getExpertBySeq(int expertSeq) throws Exception {
		String sql = "select * from expert where seq =?";
		try (Connection con = getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setInt(1, expertSeq);
			try (ResultSet rs = pstat.executeQuery();) {
				rs.next();
				int seq = rs.getInt(1);
				String writer = rs.getString(2);
				String title = rs.getString(3);
				String content = rs.getString(4);
				Timestamp writeDate = rs.getTimestamp(5);
				int viewCount = rs.getInt(6);
				viewCount = increExpertView(viewCount, seq);

				ExpertDTO dto = new ExpertDTO(seq, writer, title, content, writeDate, viewCount);
				return dto;
			}
		}
	}

	// 전문가 조회수
	public int increExpertView(int count, int seq) throws Exception {
		String sql = "update expert set viewCount = ?+ 1 where seq = ?";
		try (
				Connection con = getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				) {
			pstat.setInt(1, count);
			pstat.setInt(2, seq);
			pstat.executeUpdate();
			con.commit();
			return count+1;
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

	//1:1문의게시글에 댓글 저장
	public int insertComments(int seq, String comments) throws Exception {
		String sql = "insert into onebyonecomments values(onebyonecommentsSeq.nextval,?,?,sysdate)";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, seq);
			pstat.setString(2, comments);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	//1:1문의게시글에 댓글 보여주기
	public List<OneByOneCommentDTO> commentsList(int seq) throws Exception{
		String sql = "select * from onebyonecomments where onebyoneseq = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, seq);
			ResultSet rs = pstat.executeQuery();
			List<OneByOneCommentDTO> list = new ArrayList<>();
			while(rs.next()) {
				int commentSeq = rs.getInt(1);
				int onebyoneSeq = rs.getInt(2);
				String comment = rs.getString(3);
				Timestamp writeDate = rs.getTimestamp(4);
				OneByOneCommentDTO dto = new OneByOneCommentDTO(commentSeq,onebyoneSeq,comment,writeDate);
				list.add(dto);
			}
			return list;
		}
	}
	// 질문/자유게시판 시퀀스로 dto가져오기
	public BoardDTO getBoardBySeq(int expertSeq, String header) throws Exception {
		String sql = "select * from Board where seq = ? and header = ?";
		try (Connection con = getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setInt(1, expertSeq);
			pstat.setString(2, header);
			try (ResultSet rs = pstat.executeQuery();) {
				rs.next();
				int seq = rs.getInt(1);
				String writer = rs.getString(2);
				String headerInput = header;
				String animalHeader = rs.getString(4);
				String title = rs.getString(5);
				String content = rs.getString(6);
				String ipAddr = rs.getString(7);
				int viewCount = rs.getInt(8);
				Timestamp writeDate = rs.getTimestamp(9);

				BoardDTO dto = new BoardDTO(seq, writer, headerInput, animalHeader, title, content, ipAddr, viewCount,
						writeDate);
				return dto;
			}

		}

	}
	// 질문/자유 게시판 시퀀스로 dto가져오기
		public BoardDTO getBoardBySeq2(int seq) throws Exception {
			String sql = "select * from Board where seq = ?";
			try (Connection con = getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
				pstat.setInt(1, seq);
				try (ResultSet rs = pstat.executeQuery();) {
					rs.next();
					
					String writer = rs.getString(2);
					String headerInput = rs.getString(3);
					String animalHeader = rs.getString(4);
					String title = rs.getString(5);
					String content = rs.getString(6);
					String ipAddr = rs.getString(7);
					int viewCount = rs.getInt(8);
					Timestamp writeDate = rs.getTimestamp(9);

					BoardDTO dto = new BoardDTO(seq, writer, headerInput, animalHeader, title, content, ipAddr, viewCount,
							writeDate);
					return dto;
				}
			}

		}

	//관리자 비밀번호 변경 - 기존 비밀번호와 확인
	public boolean adminPwSameCheck(String oriPwInput) throws Exception{
		String sql = "select * from admin where pw = ?";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1,oriPwInput);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				return rs.next();
			}
		}
	}


	// 커뮤니티
	public int increBoardView(int seq) throws Exception {
		String sql = "update board set viewCount = (select viewCount from board where seq = ?) + 1 where seq = ?";
		try (Connection con = getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setInt(1, seq);
			pstat.setInt(2, seq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}

	}

	// 게시판 글삭제
	public int deleteBoard(int seq) throws Exception {
		String sql = "delete from board where seq=?";
		try (Connection con = getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setInt(1, seq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	// 공지사항 글삭제
	public int deleteNotice(int seq) throws Exception {
		String sql = "delete from notice where seq=?";
		try (Connection con = getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setInt(1, seq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	// 전문가 글 삭제
	public int deleteExpert(int seq) throws Exception {
		String sql = "delete from expert where seq=?";
		try (Connection con = getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setInt(1, seq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	//병원 정보 삭제
	public int deleteHospt(int seq) throws Exception {
		String sql = "delete from hosptList where seq=?";
		try (Connection con = getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setInt(1, seq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	// 병원 정보 뽑기
	public HListDTO getHListBySeq(int seq) throws Exception {
		String sql = "select * from hosptList where seq =?";
		try (Connection con = getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setInt(1, seq);
			try (ResultSet rs = pstat.executeQuery();) {
				rs.next();
				int seqInput = rs.getInt(1);
				String hName = rs.getString(2);
				int postCode = rs.getInt(3);
				String address1 = rs.getString(4);
				String address2 = rs.getString(5);
				String phone = rs.getString(6);
				String homePage = rs.getString(7);
				String img = rs.getString(8);
				String medicalAnimal = rs.getString(9);
				String openTime = rs.getString(10);
				Timestamp registDate = rs.getTimestamp(11);
				int viewCount = rs.getInt(12);

				HListDTO dto = new HListDTO(seqInput, hName, postCode, address1, address2, phone, homePage,
						img, medicalAnimal, openTime, registDate, viewCount);
				return dto;
			}
		}
	}
	//병원리뷰목록 게시판 내의 총 글의 개수
	public int recordHosptReviewListTotalCount () throws Exception {
		String sql = "select count(seq) from hosptReview";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}
	//병원리뷰목록 페이지 네비게이터
	public String getHosptReviewListPageNav(int currentPage) throws Exception {
		//게시판 내의 총 글의 개수
		int recordTotalCount = this.recordHosptReviewListTotalCount();
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
		if(currentPage == 0) {
			needPrev = false;
			needNext = false;
		}
		StringBuilder sb = new StringBuilder();

		if(needPrev) {sb.append("<a href='adminHosptReviewList.admin?cpage="+(currentPage-1)+"'> < </a>");}

		for(int i = startNavi; i <= endNavi;i++) {
			sb.append("<a href='adminHosptReviewList.admin?cpage="+i+"'>");
			sb.append(i + " ");
			sb.append("</a>");
		}
		if(needNext) {sb.append("<a href='adminHosptReviewList.admin?cpage="+(currentPage+1)+"'> > </a>");}
		return sb.toString();
	}
	//병원리뷰목록 게시판 목록 10개씩
	public List<ReviewDTO> hosptReviewListByPage(int start, int end) throws Exception{
		String sql = "select * from"
				+ "(select HosptReview.*, row_number() over (order by seq desc) as rown from HosptReview)"
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
				List<ReviewDTO> result = new ArrayList<>();
				while(rs.next()) {
					int seq = rs.getInt(1);
					int hosptListSeq = rs.getInt(2);
					int score = rs.getInt(3);
					String title = rs.getString(4);
					String content = rs.getString(5);
					String header = rs.getString(6);
					String writer = rs.getString(7);
					Timestamp writeDate = rs.getTimestamp(8);
					String ipAddr = rs.getString(9);
					int likeCount = rs.getInt(10);
					ReviewDTO dto = new ReviewDTO(seq,hosptListSeq,score,title,content,header,writer,writeDate,ipAddr,likeCount);
					result.add(dto);
				}
				return result;
			}
		}
	}
	//병원리뷰 삭제
	public int deleteHosptReview(int seq) throws Exception {
		String sql = "delete from hosptReview where seq=?";
		try (Connection con = getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setInt(1, seq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	//공지 수정
	public int updateNotice(int seq, String title, String content)throws Exception{
		String sql = "update notice set title =?, content =? where seq = ?";
		try(
				Connection con = getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, title);
			pstat.setString(2, content);
			pstat.setInt(3, seq);

			int result = pstat.executeUpdate();
			con.commit();
			return result;

		}
	}
	//전문가 수정
	public int updateExpert(int seq, String title, String content)throws Exception{
		String sql = "update expert set title =?, content =? where seq = ?";
		try(
				Connection con = getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, title);
			pstat.setString(2, content);
			pstat.setInt(3, seq);

			int result = pstat.executeUpdate();
			con.commit();
			return result;

		}
	}
	//댓글저장성공하면 답변완료로 바꿔주기
	public int updateHeader (int seq)throws Exception{
		String sql = "update onebyone set answerok='Y' where seq=?";
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
	
	public OneByOneCommentDTO getLastest(int onebyoneSeq) throws Exception{
		String sql = "select * from onebyonecomments where onebyoneseq=? order by seq desc";
		
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, onebyoneSeq);
			try(
					ResultSet rs = pstat.executeQuery();
					){
					rs.next();
					int seq = rs.getInt(1);
					String comment = rs.getString(3);
					Timestamp writeDate = rs.getTimestamp(4);
					
					return new OneByOneCommentDTO(seq, onebyoneSeq, comment, writeDate);
					
				}
		}
	}
}





