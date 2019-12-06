package findwatson.search.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import findwatson.admin.dto.HListDTO;
import findwatson.configuration.Configuration;


public class HospitalListDAO {
	private static HospitalListDAO instance;
	private BasicDataSource bds = new BasicDataSource();
	private HospitalListDAO() {
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		bds.setUsername("watson");
		bds.setPassword("watson");
		bds.setInitialSize(30);
	}

	public synchronized static HospitalListDAO getInstance() {
		if(instance == null) {
			instance = new HospitalListDAO();
		}
		return instance;
	}

	private Connection getConnection() throws Exception{
		return bds.getConnection();
	}


	public List<String> selectGu(String city) throws Exception{
		String sql = "select gu from gutable where city=?";
		List<String> result = new ArrayList<>();
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, city);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				while(rs.next()) {
					result.add(rs.getString(1));
				}
				return result;
			}
		}
	}

	////// 옵션별 검색 결과 //////

	// 검색에 따른 목록 전체 반환 
	public List<HListDTO> searchByOption(String input_city, String input_gu, String input_animal, String input_time) throws Exception{

		String sql = "select * from (select hosptList.*, row_number() over (order by seq desc) as rank from hosptList) "
				+ "where (address1 like ?) and (address1 like ?) and (medicalAnimal like ?) and (opentime like ?)";
		try (
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				//PreparedStatement pstat = new LoggableStatement(con, sql)
				){
		
			pstat.setString(1, "%" + input_city + "%");
			pstat.setString(2, "%" + input_gu + "%");
			pstat.setString(3, "%" + input_animal + "%");
			pstat.setString(4, "%" + input_time + "%");
	
			//System.out.println(((LoggableStatement)pstat).getQueryString());
			try(
					ResultSet rs = pstat.executeQuery();

					){
				List<HListDTO> list = new ArrayList<HListDTO>();
				while(rs.next()) {
					
					int seq = rs.getInt(1);
					String hosptName = rs.getString(2);
					int postcode = rs.getInt(3);
					String city = rs.getString(4);
					String gu = rs.getString(5);
					String phone = rs.getString(6);
					String homepage = rs.getString(7);
					String img = rs.getString(8);
					String medicalAnimal = rs.getString(9);
					String openTime = rs.getString(10);
					Timestamp registDate = rs.getTimestamp(11);
					int viewCount = rs.getInt(12);
					medicalAnimal = (medicalAnimal).replace(";",", ");
					openTime = (openTime).replace(";",", ");
					HListDTO dto = new HListDTO(seq, hosptName, postcode, city, gu,
							phone, homepage, img, medicalAnimal, openTime, registDate, viewCount);
					list.add(dto);
				
				}
			
				return list;
			}	
		}
	}


	// 제목 누르면 글 보여주기 

	public HListDTO select(int num)  throws Exception{
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
					int postcode = rs.getInt(3);
					String city = rs.getString(4);
					String gu = rs.getString(5);
					String phone = rs.getString(6);
					String homepage = rs.getString(7);
					String img = rs.getString(8);
					String medicalAnimal = rs.getString(9);
					String openTime = rs.getString(10);
					Timestamp registDate = rs.getTimestamp(11);
					int viewCount = rs.getInt(12);

					medicalAnimal = (medicalAnimal).replace(";",", ");
					openTime = (openTime).replace(";",", ");
					dto = new HListDTO(seq, hosptName, postcode, city, gu,
							phone, homepage, img, medicalAnimal, openTime, registDate, viewCount);
					
				}
				return dto; 
			}
		}
	}
	// count 증가하기 

	public int plusss(int viewcount, int seq) throws Exception {
		String sql = "update hosptList set viewCount=?+1 where seq=?";

		try (
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, viewcount);
			pstat.setInt(2, seq);
			pstat.executeUpdate();
			con.commit();
		}
	
		return 1;
	}

	/////// 상단 전체 검색창 ////////


	// 검색에 따른 전체 목록 개수 반환 
	public int selectByAllUp(String keyword) throws Exception{

		String sql = "select * from board where (hosptname like ?) or (address1 like ?) or (address2 like ?) "
				+ "or (medicalanimal like ?) or (opentime like ?) ";

		try (
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				//PreparedStatement pstat = new LoggableStatement(con, sql)
				){
			pstat.setString(1, "%" + keyword + "%");
			pstat.setString(2, "%" + keyword + "%");
			pstat.setString(3, "%" + keyword + "%");
			pstat.setString(4, "%" + keyword + "%");
			pstat.setString(5, "%" + keyword + "%");
	
			try(
					ResultSet rs = pstat.executeQuery();

					){
				List<HListDTO> list = new ArrayList<HListDTO>();
				while(rs.next()) {
					int seq = rs.getInt(1);
					String hosptName = rs.getString(2);
					int postcode = rs.getInt(3);
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
					list.add(dto);
				}
				return list.size();
			}	
		}
	}


	// 검색에 따른 목록을 페이지 수에 맞춰 반환 
	public List<HListDTO> selectByPageTotal(String keyword, int start, int end) throws Exception{

		String sql = "select * from (select hosptList.*, row_number() over (order by seq desc) as rank from hosptList) "
				+ "where (hosptname like ?) or (address1 like ?) or (address2 like ?) or (medicalanimal like ?) "
				+ "or (opentime like ?) and (rank between ? and ?)";

		try (
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				//PreparedStatement pstat = new LoggableStatement(con, sql)
				){
			pstat.setString(1, "%" + keyword + "%");
			pstat.setString(2, "%" + keyword + "%");
			pstat.setString(3, "%" + keyword + "%");
			pstat.setString(4, "%" + keyword + "%");
			pstat.setString(5, "%" + keyword + "%");
			pstat.setInt(6, start);
			pstat.setInt(7, end);
			try(
					ResultSet rs = pstat.executeQuery();

					){
				List<HListDTO> list = new ArrayList<HListDTO>();
				while(rs.next()) {
					int seq = rs.getInt(1);
					String hosptName = rs.getString(2);
					int postcode = rs.getInt(3);
					String city = rs.getString(4);
					String gu = rs.getString(5);
					String phone = rs.getString(6);
					String homepage = rs.getString(7);
					String img = rs.getString(8);
					String medicalAnimal = rs.getString(9);
					String openTime = rs.getString(10);
					Timestamp registDate = rs.getTimestamp(11);
					int viewCount = rs.getInt(12);
					medicalAnimal = (medicalAnimal).replace(";",", ");
					openTime = (openTime).replace(";",", ");
					HListDTO dto = new HListDTO(seq, hosptName, postcode, city, gu,
							phone, homepage, img, medicalAnimal, openTime, registDate, viewCount);
					list.add(dto);
				}
				return list;
			}	
		}
	}

	
}
