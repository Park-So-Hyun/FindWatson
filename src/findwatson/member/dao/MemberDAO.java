package findwatson.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import findwatson.admin.utils.Util;
import findwatson.member.dto.MemberDTO;

public class MemberDAO {
	private BasicDataSource bds = new BasicDataSource();
	private static MemberDAO instance = new MemberDAO();


	public MemberDAO() {
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		bds.setUsername("watson");
		bds.setPassword("watson");
		bds.setInitialSize(30);
	}

	public synchronized static MemberDAO getInstance() {
		if(instance == null) {
			instance = new MemberDAO();
		}
		return instance;
	}

	private Connection getConnection() throws Exception{
		return bds.getConnection();
	}

	public boolean loginOk(String id, String pw) throws Exception{
		String sql = "select * from member where id=? and pw=?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, id);
			pstat.setString(2, Util.encrypt(pw));
			try(
					ResultSet rs = pstat.executeQuery();
					){
				boolean result = rs.next(); 
				return result;
			}


		}
	}
	public boolean idCheck(String id) throws Exception {
		String sql = "select * from member where id=?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, id);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				boolean result = rs.next();
				return result;
			}


		}
	}
	public int insert(MemberDTO dto)throws Exception{
		String sql = "insert into member values (?,?,?,?,?,?,?,?,?,?,?,?,sysdate,'로그인x')";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1,dto.getId());
			pstat.setString(2, Util.encrypt(dto.getPw()));
			pstat.setString(3, dto.getName());
			pstat.setString(4, dto.getBirth());
			pstat.setString(5, dto.getGender());
			pstat.setString(6, dto.getEmail());
			pstat.setString(7, dto.getPhone());
			pstat.setString(8, dto.getPostcode());
			pstat.setString(9, dto.getAddress1());
			pstat.setString(10, dto.getAddress2());
			pstat.setString(11, dto.getLovePet());
			pstat.setString(12, dto.getSignPath());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	public int delete(String id) throws Exception{
		String sql = "delete from member where id=?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, id);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	public MemberDTO selectMyInfo(String id)throws Exception{
		String sql = "select * from member where id=?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, id);
			try(ResultSet rs = pstat.executeQuery();){
				MemberDTO dto = new MemberDTO();
				if(rs.next()){
					dto.setId(rs.getString(1));
					dto.setPw(rs.getString(2));
					dto.setName(rs.getString(3));
					dto.setBirth(rs.getString(4));
					dto.setGender(rs.getString(5));
					dto.setEmail(rs.getString(6));
					dto.setPhone(rs.getString(7));
					dto.setPostcode(rs.getString(8));
					dto.setAddress1(rs.getString(9));
					dto.setAddress2(rs.getString(10));
					dto.setLovePet(rs.getString(11));
					dto.setSignPath(rs.getString(12));
				}
				return dto;
			}

		}
	}
	public int modify
	(String pw, String name, String birth, String gender, String email, String phone, String postcode, String address1, String address2, 
			String lovePet, String id)throws Exception {
		String sql = "update member set pw=?,name=?, birth=?, gender=?, email=?, phone=?, postcode=?, address1=?, address2=?, lovePet=? where id=?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){

			pstat.setString(1,Util.encrypt(pw));
			pstat.setString(2, name);
			pstat.setString(3, birth);
			pstat.setString(4, gender);
			pstat.setString(5, email);
			pstat.setString(6, phone);
			pstat.setString(7, postcode);
			pstat.setString(8, address1);
			pstat.setString(9, address2);
			pstat.setString(10, lovePet);
			pstat.setString(11, id);

			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	//로그인시 아이피 주소 member테이블에 업데이트
	public int updateMemberIp(String id, String ipAddr)throws Exception{
		String sql = "update member set ipAddr = ? where id= ?";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, ipAddr);
			pstat.setString(2, id);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	//차단목록에 있나 확인하기
	public boolean banCheck(String ipAddr)throws Exception{
		String sql ="select * from banIp where ipAddr = ?";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, ipAddr);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				return rs.next();
			}
		}
	}
	//아이피로 차단사유 찾기
	public String banReasonByIp(String ipAddr)throws Exception{
		String sql ="select reason from banIp where ipAddr =?";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, ipAddr);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				rs.next();
				return rs.getString(1);
			}
		}
	}
	//id찾기
	public boolean idFind(String name, String birth, String email, int phone) throws Exception{
		String sql = "select id from member where name=? and birth=? and email=? and phone=?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, name);
			pstat.setString(2, birth);
			pstat.setString(3, email);
			pstat.setInt(4, phone);
			try(ResultSet rs = pstat.executeQuery();){
				boolean result = rs.next();

				return result;
			}

		}

	}//id찾기-id가지고오기
	public String idFindGet(String name, String birth, String email, int phone) throws Exception{
		String sql = "select id from member where name=? and birth=? and email=? and phone=?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, name);
			pstat.setString(2, birth);
			pstat.setString(3, email);
			pstat.setInt(4, phone);
			try(ResultSet rs = pstat.executeQuery();){
				rs.next();
				return rs.getString(1);
			}

		}

	}
	//비밀번호찾기
	public boolean pwFind(String name, String id, String birth, String email, int phone)throws Exception{
		String sql = "select pw from member where name=? and id=? and birth=? and email=? and phone=?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, name);
			pstat.setString(2 ,id);
			pstat.setString(3, birth);
			pstat.setString(4, email);
			pstat.setInt(5, phone);
			try(ResultSet rs = pstat.executeQuery();){
				boolean result = rs.next();
				return result;
			}
		}
	}
	//비밀번호찾기-pw가지고오기
	public int pwFindGet(String id, String pw)throws Exception{
		String sql = "update member set pw=? where id=?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1 ,Util.encrypt(pw));
			pstat.setString(2, id);
				int result = pstat.executeUpdate();
				con.commit();
				return result;
			
		}
	}
	//1:1 글쓰기 저장
	public int insertOneByOne(String id, String title, String content, String header) throws Exception{
		String sql = "insert into onebyone values (onebyoneSeq.nextval,?,'N',?,?,?,'아이피',sysdate)";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, id);
			pstat.setString(2, header);
			pstat.setString(3, title);
			pstat.setString(4, content);
			
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	
	public int deleteOneByOne(int seq) throws Exception{
		String sql = "delete from onebyone where seq=?";
		
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
