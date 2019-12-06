package findwatson.review.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import findwatson.admin.dto.HListDTO;
import findwatson.board.dao.BoardDAO;
import findwatson.configuration.Configuration;
import findwatson.review.dao.ReviewDAO;
import findwatson.review.dao.ReviewFileDAO;
import findwatson.review.dto.ReviewDTO;
import findwatson.review.dto.ReviewFileDTO;
import findwatson.search.dao.HospitalListDAO;

@WebServlet("*.re")
public class reviewController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter pwriter = response.getWriter();
		ReviewDAO dao = ReviewDAO.getInstance();
		ReviewFileDAO fDao = ReviewFileDAO.getInstance();
		
		String id = (String)request.getSession().getAttribute("loginInfo");
		System.out.println("id : " + id);
		String ipAddr = request.getRemoteAddr();
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String cmd = requestURI.substring(contextPath.length());
		System.out.println("cmd : " + cmd);
		
					
		if(cmd.contentEquals("/imgUpload.re")) {//이미지 업로드
			String repositoryName = "reviewImg";
			String uploadPath = request.getServletContext().getRealPath("/" + repositoryName);
			System.out.println(uploadPath);
			File uploadFilePath = new File(uploadPath);
			if(!uploadFilePath.exists()) {
				uploadFilePath.mkdir();
			}
			
			int maxSize = 1024 * 1024 * 100;
			MultipartRequest multi = new MultipartRequest(request,uploadPath, maxSize,"UTF-8",new DefaultFileRenamePolicy());
			
			String fileName = multi.getFilesystemName("reviewImg");
			String oriFileName = multi.getOriginalFileName("reviewImg");
			System.out.println("원래 파일 이름 : " + oriFileName);
			System.out.println("올린 파일 이름 : " + fileName);
			
			try {
				fDao.insert(new ReviewFileDTO(0, 0, fileName, oriFileName));
			}catch(Exception e) {
				e.printStackTrace();
				response.sendRedirect(contextPath + "/error.jsp");
			}
			//서버의 이미지 경로
			String imgPath = contextPath + "/" + repositoryName + "/" + fileName;
			System.out.println("이미지 경로 :  " + imgPath);
			
			JsonObject jObj = new JsonObject();
			jObj.addProperty("imgPath", imgPath);
			pwriter.append(jObj.toString());
		}else if(cmd.contentEquals("/reviewWrite.re")){ //리뷰 등록
			int hosptListSeq = Integer.parseInt(request.getParameter("seq"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			System.out.println(content);
			int score = Integer.parseInt(request.getParameter("rating"));
			System.out.println(score);
			try {
				dao.insert(new ReviewDTO(0,hosptListSeq,score,title,content,"header", id, null, ipAddr, 0));
				response.sendRedirect("hospitalSearchDetail2.re?seq="+hosptListSeq);
				
			}catch(Exception e){
				e.printStackTrace();
				response.sendRedirect("main/error.jsp");
			}
		}else if(cmd.contentEquals("/reviewDelete.re")) {
			int seq = Integer.parseInt(request.getParameter("seq"));
			int hosptSeq = Integer.parseInt(request.getParameter("hspSeq"));
			try {
				BoardDAO.getInstance().deleteReview(seq);
				response.sendRedirect("hospitalSearchDetail2.re?seq=" + hosptSeq);
			}catch(Exception e) {
				e.printStackTrace();
				response.sendRedirect(contextPath + "/error.jsp");
			}
		}else if(cmd.contentEquals("/hospitalSearchDetail2.re")) { //병원 디테일뷰 2 - 디폴트:최신순
			try {    
				//디테일뷰 상단 병원 정보
				int hosptListSeq = Integer.parseInt(request.getParameter("seq"));
				
				HListDTO contents = HospitalListDAO.getInstance().select(hosptListSeq);

				HospitalListDAO.getInstance().plusss(contents.getViewCount(),hosptListSeq);
				request.setAttribute("contents", contents);
				



				//하단 리뷰 리스트
				int cpage = 1;
				String cpageInput = request.getParameter("cpage");
				if(cpageInput != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				String navi = dao.getPageNavi(cpage, hosptListSeq,hosptListSeq);
				request.setAttribute("cpage", cpage);
				request.setAttribute("navi", navi); 
				request.setAttribute("hosptListSeq", hosptListSeq);
				
				int startRecord = cpage*Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
				int endRecord = cpage*Configuration.recordCountPerPage;
				
				List<ReviewDTO> reviewList = dao.selectByPage(hosptListSeq, startRecord, endRecord);
				request.setAttribute("reviewList", reviewList);
				
				request.getRequestDispatcher("search/hospitalSearchDetail2.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
				response.sendRedirect("main/error.jsp");
			}
		}else if(cmd.contentEquals("/hospitalSearchDetail2ByScore.re")) {//병원 디테일뷰 2 - 별점 순
			try {    
				//디테일뷰 상단 병원 정보
				int hosptListSeq = Integer.parseInt(request.getParameter("seq"));
				HListDTO contents = HospitalListDAO.getInstance().select(hosptListSeq);

				HospitalListDAO.getInstance().plusss(contents.getViewCount(),hosptListSeq);
				request.setAttribute("contents", contents);
				
				//여기부터
				int cpage = 1;
				String cpageInput = request.getParameter("cpage");
				if(cpageInput != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				String navi = dao.getPageNavi(cpage, hosptListSeq,hosptListSeq);
				request.setAttribute("cpage", cpage);
				request.setAttribute("navi", navi); 
				
				int startRecord = cpage*Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
				int endRecord = cpage*Configuration.recordCountPerPage;
				
				List<ReviewDTO> reviewList = dao.selectByPageByScore(hosptListSeq, startRecord, endRecord);
				request.setAttribute("reviewList", reviewList);
				request.getRequestDispatcher("search/hospitalSearchDetail2.jsp").forward(request, response);

			}catch(Exception e) {
				e.printStackTrace();
				response.sendRedirect("main/error.jsp");
			}
			
			
		}else if(cmd.contentEquals("/hospitalSearchDetail2ByLike.re")) {//병원 디테일뷰 2 - 좋아요 순
			try {  
				//디테일뷰 상단 병원 정보
				int hosptListSeq = Integer.parseInt(request.getParameter("seq"));
				HListDTO contents = HospitalListDAO.getInstance().select(hosptListSeq);

				HospitalListDAO.getInstance().plusss(contents.getViewCount(),hosptListSeq);
				request.setAttribute("contents", contents);
				
				//여기부터
				int cpage = 1;
				String cpageInput = request.getParameter("cpage");
				if(cpageInput != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				String navi = dao.getPageNavi(cpage, hosptListSeq,hosptListSeq);
				request.setAttribute("cpage", cpage);
				request.setAttribute("navi", navi); 
				
				int startRecord = cpage*Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
				int endRecord = cpage*Configuration.recordCountPerPage;
				
				List<ReviewDTO> reviewList = dao.selectByPageByLike(hosptListSeq, startRecord, endRecord);
				request.setAttribute("reviewList", reviewList);
				request.getRequestDispatcher("search/hospitalSearchDetail2.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
				response.sendRedirect("main/error.jsp");
			}
			
			
		}else if(cmd.contentEquals("/likeIncrement.re")) {
			int reviewSeq = Integer.parseInt(request.getParameter("reviewSeq"));
			try {
				int hspSeq = dao.getHspSeqByRvSeq(reviewSeq);
				dao.incrementLike(reviewSeq);
				response.sendRedirect("hospitalSearchDetail2ByLike.re?seq=" + hspSeq);

			}catch(Exception e) {
				e.printStackTrace();
				response.sendRedirect("main/error.jsp");
			}
		}else {
			response.sendRedirect("main/error.jsp");
		}
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
