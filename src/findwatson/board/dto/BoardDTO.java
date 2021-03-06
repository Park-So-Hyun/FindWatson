package findwatson.board.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class BoardDTO {
   private int seq;
   private String writer;
   private String header;
   private String animalHeader;
   private String title;
   private String content;
   private String ipAddr;
   private int viewCount;
   private Timestamp writeDate;
   
   public String getDate() throws Exception{
      long exDate = System.currentTimeMillis();
      long processedWrite_date = writeDate.getTime();
      long sec = (exDate - processedWrite_date)/1000;
      int min = (int)(sec/60.0) + 1;
      int hour = (int)(sec/360.0) + 1;
      
      if(sec < 60) {
         return sec + "초 전";
      }else if(min < 60){
         return min + "분 전";
      }else if(hour < 24) {
         return "오늘";
      }else {
         SimpleDateFormat sdf = new SimpleDateFormat("20yy.MM.dd.");
         return sdf.format(processedWrite_date);
      }
   }
   
   public BoardDTO() {
      super();
   }

   public BoardDTO(int seq, String writer, String header, String animalHeader, String title, String content,
         String ipAddr, int viewCount, Timestamp writeDate) {
      super();
      this.seq = seq;
      this.writer = writer;
      this.header = header;
      this.animalHeader = animalHeader;
      this.title = title;
      this.content = content;
      this.ipAddr = ipAddr;
      this.viewCount = viewCount;
      this.writeDate = writeDate;
   }
   
   public int getSeq() {
      return seq;
   }

   public void setSeq(int seq) {
      this.seq = seq;
   }

   public String getWriter() {
      return writer;
   }

   public void setWriter(String writer) {
      this.writer = writer;
   }

   public String getHeader() {
      return header;
   }

   public void setHeader(String header) {
      this.header = header;
   }

   public String getAnimalHeader() {
      return animalHeader;
   }

   public void setAnimalHeader(String animalHeader) {
      this.animalHeader = animalHeader;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getIpAddr() {
      return ipAddr;
   }

   public void setIpAddr(String ipAddr) {
      this.ipAddr = ipAddr;
   }

   public int getViewCount() {
      return viewCount;
   }

   public void setViewCount(int viewCount) {
      this.viewCount = viewCount;
   }

   public Timestamp getWriteDate() {
      return writeDate;
   }

   public void setWriteDate(Timestamp writeDate) {
      this.writeDate = writeDate;
   } 
}
