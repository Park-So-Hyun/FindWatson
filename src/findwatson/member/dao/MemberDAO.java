package findwatson.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

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
			pstat.setString(2, pw);
			ResultSet rs = pstat.executeQuery();
			boolean result = rs.next(); 
					return result;
		}

		
	}
	public int insert(MemberDTO dto)throws Exception{
		String sql = "insert into member values (?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1,dto.getId());
			pstat.setString(2, dto.getPw());
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
}
