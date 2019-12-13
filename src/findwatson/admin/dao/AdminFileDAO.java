package findwatson.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import findwatson.admin.dto.AdminFileDTO;
import findwatson.utils.Statics;


public class AdminFileDAO {
	private static AdminFileDAO instance;
	private AdminFileDAO () {}
	public synchronized static AdminFileDAO getInstance() {
		if(instance == null){									
			instance = new AdminFileDAO();
		}
		return instance;
	}
	//Connection
	private Connection getConnection() throws Exception{
		return Statics.bds.getConnection();
	}
	
	//파일 업로드 expert
	public int insertImgToExpert(AdminFileDTO dto) throws Exception{
		String sql = "insert into expertPhoto values(expertPhotoSeq.nextval,null,?,?)";try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getFileName());
			pstat.setString(2, dto.getOriFileName());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	//파일 업로드 notice
		public int insertImgToNotice(AdminFileDTO dto) throws Exception{
			String sql = "insert into noticePhoto values(noticePhotoSeq.nextval,null,?,?)";
			try(
					Connection con = this.getConnection();
					PreparedStatement pstat = con.prepareStatement(sql);
					){
				pstat.setString(1, dto.getFileName());
				pstat.setString(2, dto.getOriFileName());
				int result = pstat.executeUpdate();
				con.commit();
				return result;
			}
		}
	//파일 리스트
	public List<AdminFileDTO> selectAll() throws Exception{
		String sql = "select * from files";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			List <AdminFileDTO> list = new ArrayList<>();
			while(rs.next()) {
				int seq = rs.getInt(1);
				int articleSeq = rs.getInt(2);
				String fileName = rs.getString(3);
				String oriFileName = rs.getString(4);
				AdminFileDTO dto = new AdminFileDTO(seq,articleSeq,fileName,oriFileName);
				list.add(dto);
			}
			return list;
		}
	}
	//파일 삭제 article seq로 삭제?
//	public int update(AdminFileDTO dto) throws Exception{
//		
//	}
}
