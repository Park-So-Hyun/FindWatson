package findwatson.board.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import findwatson.board.dto.FilesDTO;
import findwatson.utils.Statics;

public class FilesDAO {
   private static FilesDAO instance;
   private FilesDAO() {}
   private Connection getConnetion() throws Exception{
      return Statics.bds.getConnection();
   }   
   public synchronized static FilesDAO getInstance() {
      if(instance == null) {
         instance = new FilesDAO();
      }
      return instance;
   }
   
//   public List<FilesDTO> getFilesBy(int boardSeq) throws Exception{
//      String sql = "select * from files where boardSeq=?";
//      List<FilesDTO> result = new ArrayList<>();
//      try(
//            Connection con = this.getConnetion();
//            PreparedStatement pstat = con.prepareStatement(sql);
//            ){
//         pstat.setInt(1, boardSeq);
//         try(
//               ResultSet rs = pstat.executeQuery();
//               ){
//            while(rs.next()) {
//               int filesSeq = rs.getInt(1);
//               
//               String fileName = rs.getString(3);
//               String oriFileName = rs.getString(4);
//               
//               result.add(new FilesDTO(filesSeq, boardSeq, fileName, oriFileName));
//            }
//            return result;
//         }
//      }
//   }
   
   //커뮤니티(질문) - 이미지업로드
   public int insert(FilesDTO dto) throws Exception{
      String sql = "insert into commentsPhoto values(commentsPhotoSeq.nextval, null, ?, ?)";
      try(
            Connection con = this.getConnetion();
            PreparedStatement pstat = con.prepareStatement(sql);
            ){
         pstat.setString(1, dto.getFileName());
         pstat.setString(2, dto.getOriFileName());
         int result = pstat.executeUpdate();
         con.commit();
         return result;
      }
   }
   
}
