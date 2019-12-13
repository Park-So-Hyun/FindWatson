package findwatson.donate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import findwatson.donate.dto.DonateDTO;
import findwatson.utils.Statics;


public class DonateDAO {
	private static DonateDAO instance;
	private DonateDAO() {}
	private Connection getConnection() throws Exception{
		return Statics.bds.getConnection();
	}
	public synchronized static DonateDAO getInstance() {
		if(instance == null) {
			instance = new DonateDAO();
		}
		return instance;
	}

	public String getDonateId() throws Exception{
		String sql = "SELECT TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSFF1') FROM DUAL";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			rs.next();
			return rs.getString(1);
		}
	}

	public int setDonateInfo(DonateDTO dto) throws Exception{
		String sql = "insert into donate values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getDonateId());
			pstat.setString(2, dto.getUserId());
			pstat.setString(3, dto.getName());
			pstat.setInt(4, dto.getSupport());
			pstat.setInt(5, dto.getRealPayment());
			pstat.setString(6, dto.getPayMethod());
			pstat.setString(7, dto.getEmail());
			pstat.setString(8, dto.getBirth());
			pstat.setString(9, dto.getPostcode());
			pstat.setString(10, dto.getAddr1());
			pstat.setString(11, dto.getAddr2());
			pstat.setString(12, dto.getPhone());
			pstat.setString(13, dto.getValid());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	//금액이 일치하면 T 바꾸고 아니면 F 유지
	public int updateValid(String merchant_uid) throws Exception{
		String sql = "update donate set valid='T' where donateId=? and realPayment=support";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, merchant_uid);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}		
	}

	//실결제금액 업데이트
	public int updateRealPay(String merchant_uid, int paid_amount) throws Exception{
		String sql = "update donate set realPayment=? where donateId=?";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, paid_amount);
			pstat.setString(2, merchant_uid);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	//결제취소시 기록 삭제
	public int delDonateInfo(String merchant_uid) throws Exception{
		String sql = "delete from donate where donateId=?";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, merchant_uid);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}

	}

	//결제 성공시 고객에게 금액 알림
	public int selectRealPay(String donateId, String name) throws Exception{
		String sql = "select * from donate where donateId=? and name=?";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, donateId);
			pstat.setString(2, name);			
			try(
					ResultSet rs = pstat.executeQuery();
					){
				int result = 0;
				if(rs.next()) {
					result = rs.getInt(5);
				}
				return result;
			}
		}
	}

	//회원용 후원내역보기 : 회원에게 보이는 내용(후원고유번호, 후원한이름, 결제금액, 결제방법)

	public List<DonateDTO> selectMy(String userId) throws Exception{
		String sql = "select * from donate where userId=?";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, userId);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				List<DonateDTO> result = new ArrayList<>();
				while(rs.next()) {
					String donateId = rs.getString(1);
					String name = rs.getString(3);
					int realPayment = rs.getInt(5);
					String payMethod = rs.getString(6);
					result.add(new DonateDTO(donateId, userId, name, 0, realPayment, payMethod, null, null,null, null, null, null, null));					
				}
				return result;
			}
		}
	}

	//관리자용 후원내역보기(모든 내용 확인. valid 요소는 결제 시도한 금액과 실제로 결제한 금액이 같으면 T 다르면 F)
	public List<DonateDTO> selectAll() throws Exception{
		String sql = "select * from donate";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
				List<DonateDTO> result = new ArrayList<>();
				while(rs.next()) {
					String donateId = rs.getString(1);
					String userId = rs.getString(2);
					String name = rs.getString(3);
					int support = rs.getInt(4);
					int realPayment = rs.getInt(5);
					String payMethod = rs.getString(6);
					String email = rs.getString(7);
					String birth = rs.getString(8);
					String postcode = rs.getString(9);
					String addr1 = rs.getString(10);
					String addr2 = rs.getString(11);
					String phone = rs.getString(12);
					String valid = rs.getString(13);
					result.add(new DonateDTO(donateId, userId, name, support, realPayment, payMethod, email, birth, postcode, addr1, addr2, phone, valid));					
				}
				return result;
		}
	}

}
