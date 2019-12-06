package findwatson.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import findwatson.admin.dao.AdminDAO;
import findwatson.admin.dto.ExpertDTO;
import findwatson.admin.dto.NoticeDTO;
import findwatson.board.dao.BoardDAO;
import findwatson.board.dao.ComDAO;
import findwatson.board.dao.FilesDAO;
import findwatson.board.dto.BoardDTO;
import findwatson.board.dto.ComDTO;
import findwatson.board.dto.FilesDTO;
import findwatson.configuration.Configuration;


@WebServlet("*.bo")
public class BoardController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter pwriter = response.getWriter();
		FilesDAO fDao = FilesDAO.getInstance();
		BoardDAO dao = BoardDAO.getInstance();

		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String cmd = requestURI.substring(contextPath.length());


	 
	    String id = (String)request.getSession().getAttribute("loginInfo");
	    String ipAddr = request.getRemoteAddr();
	    

		try {
			// 자유게시판 글 목록 출력
			if(cmd.contentEquals("/boardFree.bo")) {
				String pageCategory = "boardFree.bo";
				int cpage = 1;
				String page = request.getParameter("cpage");
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				int start = cpage * Configuration.recordCountPerPage - Configuration.recordCountPerPage - 1;
				int end = cpage * Configuration.recordCountPerPage;

				List<BoardDTO> list = dao.selectByPage(start, end, "자유");
				String pageNavi = dao.getPageNavi(cpage,pageCategory,"자유");

				request.setAttribute("list", list);
				request.setAttribute("pageNavi", pageNavi);
				
				request.getRequestDispatcher("board/boardFree.jsp").forward(request, response);

				// 질문게시판 글 목록 출력
			} else if(cmd.contentEquals("/boardQuestion.bo")) {
				String pageCategory = "boardQuestion.bo";
				int cpage = 1;
				String page = request.getParameter("cpage");
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				int start = cpage * Configuration.recordCountPerPage - Configuration.recordCountPerPage - 1;
				int end = cpage * Configuration.recordCountPerPage;

				List<BoardDTO> list = dao.selectByPage(start, end, "질문");
				String pageNavi = dao.getPageNavi(cpage,pageCategory,"질문");

				request.setAttribute("list", list);
				request.setAttribute("pageNavi", pageNavi);
				request.getRequestDispatcher("board/boardQuestion.jsp").forward(request, response);

				// 전문가Q&A 글 목록 출력			
			} else if(cmd.contentEquals("/boardExpert.bo")) {
				String pageCategory = "boardExpert.bo";
				int cpage = 1;
				String page = request.getParameter("cpage");
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				int start = cpage * Configuration.recordCountPerPage - Configuration.recordCountPerPage - 1;
				int end = cpage * Configuration.recordCountPerPage;

				List<ExpertDTO> list = dao.selectByPageExpert(start, end);
				String pageNavi = dao.getPageNavi(cpage,pageCategory,"전문가");

				request.setAttribute("list", list);
				request.setAttribute("pageNavi", pageNavi);
				request.getRequestDispatcher("board/boardExpert.jsp").forward(request, response);

				// 공지사항 글 목록 출력
			} else if(cmd.contentEquals("/boardNotice.bo")) {
				String pageCategory = "boardNotice.bo";
				int cpage = 1;
				String page = request.getParameter("cpage");
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				int start = cpage * Configuration.recordCountPerPage - Configuration.recordCountPerPage - 1;
				int end = cpage * Configuration.recordCountPerPage;

				List<NoticeDTO> list = dao.selectByPageNotice(start, end);
				String pageNavi = dao.getPageNavi(cpage,pageCategory,"공지");

				request.setAttribute("list", list);
				request.setAttribute("pageNavi", pageNavi);
				request.getRequestDispatcher("board/boardNotice.jsp").forward(request, response);

				// 커뮤니티(질문) - 글쓰기
			} else if(cmd.contentEquals("/communityQuestionWrite.bo")) {

				String header = request.getParameter("header");
				String animalHeader = request.getParameter("animalHeader");
				String questionTitle = Configuration.protectXSS(request.getParameter("questionTitle"));
				String content = request.getParameter("content");

				dao.insert(new BoardDTO(0,id, header, animalHeader, questionTitle, content, ipAddr, 0, null));
				response.sendRedirect("boardQuestion.bo");

				//커뮤니티(질문) - 이미지 업로드
			} else if(cmd.contentEquals("/imgUploadQuestion.bo")) {
				String repositoryName = "boardQuestImgRepository";
				String uploadPath = request.getServletContext().getRealPath("/" + repositoryName);
				

		        File uploadFilePath = new File(uploadPath);
		        if(!uploadFilePath.exists()) {
		           uploadFilePath.mkdir();
		        }
		         
		        int maxSize = 1024 * 1024 * 100;
		        MultipartRequest multi = new MultipartRequest(request,uploadPath, maxSize,"UTF-8",new DefaultFileRenamePolicy());
		         
		        String fileName = multi.getFilesystemName("comQuestionImg");
		        String oriFileName = multi.getOriginalFileName("comQuestionImg");
		        
				
		        fDao.insert(new FilesDTO(0, 0, fileName, oriFileName));
		        
		        //서버의 이미지 경로
		        String imgPath = contextPath + "/" + repositoryName + "/" + fileName;
		        
		         
		        JsonObject jObj = new JsonObject();
		        jObj.addProperty("imgPath", imgPath);
		        pwriter.append(jObj.toString());
		        
		    // 커뮤니티(자유) - 글쓰기
			} else if(cmd.contentEquals("/communityFreeWrite.bo")) {
				String header = request.getParameter("header");
				String animalHeader = request.getParameter("animalHeader");
				String questionTitle = Configuration.protectXSS(request.getParameter("freeTitle"));
				String content = request.getParameter("content");
				

				dao.insert(new BoardDTO(0,id, header, animalHeader, questionTitle, content, ipAddr, 0, null));
				response.sendRedirect("boardFree.bo");

				// 커뮤니티(자유) - 이미지 업로드
			} else if(cmd.contentEquals("/imgUploadFree.bo")) {
				String repositoryName = "boardFreeImgRepository";
				String uploadPath = request.getServletContext().getRealPath("/" + repositoryName);

				File uploadFilePath = new File(uploadPath);
				if(!uploadFilePath.exists()) {
					uploadFilePath.mkdir();
				}

				int maxSize = 1024 * 1024 * 100;
				MultipartRequest multi = new MultipartRequest(request,uploadPath, maxSize,"UTF-8",new DefaultFileRenamePolicy());

				String fileName = multi.getFilesystemName("comFreeImg");
				String oriFileName = multi.getOriginalFileName("comFreeImg");
				

				fDao.insert(new FilesDTO(0, 0, fileName, oriFileName));

				// 서버의 이미지 경로
				String imgPath = contextPath + "/" + repositoryName + "/" + fileName;
				

				JsonObject jObj = new JsonObject();
				jObj.addProperty("imgPath", imgPath);
				pwriter.append(jObj.toString());


			// 자유게시판
			}else if(cmd.contentEquals("/searchFree.bo")) {
				String category = request.getParameter("category");
				String keyword = request.getParameter("keyword");
				// 네비게이터 받아오는 부분 
				int currentPage =1;
				String page = request.getParameter("currentPage");
				if(page != null) {
					currentPage = Integer.parseInt(page);
				}
				int start = currentPage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage-1);
				int end = currentPage * Configuration.recordCountPerPage;

				List<BoardDTO> list = new ArrayList<>();

				list = BoardDAO.getInstance().selectByOptionFree(category, keyword, start, end);

				request.setAttribute("list", list);

				// navi 값 보내기 
				String pageNavi = BoardDAO.getInstance().getPageNaviTotalFree(currentPage, category, keyword );
				request.setAttribute("pageNavi", pageNavi);
				request.setAttribute("keyword", keyword);
				request.getRequestDispatcher("/board/boardFree.jsp").forward(request, response);

			// 질문게시판
			}else if(cmd.contentEquals("/searchOne.bo")) {
				String category = request.getParameter("category");
				String keyword = request.getParameter("keyword");
				// 네비게이터 받아오는 부분 
				int currentPage =1;
				String page = request.getParameter("currentPage");
				if(page != null) {
					currentPage = Integer.parseInt(page);
				}
				int start = currentPage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage-1);
				int end = currentPage * Configuration.recordCountPerPage;

				List<BoardDTO> list = new ArrayList<>();

				list = BoardDAO.getInstance().selectByOptionOne(category, keyword, start, end);

				request.setAttribute("list", list);

				// navi 값 보내기 
				String pageNavi = BoardDAO.getInstance().getPageNaviTotalOne(currentPage, category, keyword );
				request.setAttribute("pageNavi", pageNavi);
				request.setAttribute("keyword", keyword);
				request.getRequestDispatcher("/board/boardQuestion.jsp").forward(request, response);



			
			}else if(cmd.contentEquals("/noticeDetail.bo")) {
				//공지사항 글 읽기
				int seq = Integer.parseInt(request.getParameter("seq"));
				AdminDAO adao = AdminDAO.getInstance();
				NoticeDTO dto = adao.getNoticeBySeq(seq);
				request.setAttribute("dto", dto);
								
				request.getRequestDispatcher("board/boardNoticeDetailView.jsp").forward(request, response);
				
			}else if(cmd.contentEquals("/freeDetail.bo")) {
				//자유 게시판 글읽기
				int cpage = 1;
				String page = request.getParameter("cpage");
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
				int end = cpage * Configuration.recordCountPerPage;
				
				int seq = Integer.parseInt(request.getParameter("seq"));
				dao.increViewCnt(seq);
				AdminDAO adao = AdminDAO.getInstance();
				BoardDTO dto = adao.getBoardBySeq(seq, "자유");
				request.setAttribute("dto", dto);
				request.setAttribute("loginInfo", id);
				
				String pageNavi = ComDAO.getInstance().getPageNaviCmtFree(cpage, seq);
				List<ComDTO> list = ComDAO.getInstance().selectByPage(seq, start, end);
				request.setAttribute("pageNavi", pageNavi);
				request.setAttribute("list", list);
				request.getRequestDispatcher("board/freeDetailView.jsp").forward(request, response);

				
			}else if(cmd.contentEquals("/questionDetail.bo")) {
				//질문 게시판 글읽기
				int cpage = 1;
				String page = request.getParameter("cpage");
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
				int end = cpage * Configuration.recordCountPerPage;
				
				int seq = Integer.parseInt(request.getParameter("seq"));
				dao.increViewCnt(seq);
				AdminDAO adao = AdminDAO.getInstance();
				BoardDTO dto = adao.getBoardBySeq(seq, "질문");
				request.setAttribute("dto", dto);
				request.setAttribute("loginInfo", id);
				
				String pageNavi = ComDAO.getInstance().getPageNaviCmtQuestion(cpage, seq);
				List<ComDTO> list = ComDAO.getInstance().selectByPage(seq, start, end);
				request.setAttribute("pageNavi", pageNavi);
				request.setAttribute("list", list);
				request.getRequestDispatcher("board/questionDetailView.jsp").forward(request, response);

				
			}else if(cmd.contentEquals("/boardRemove.bo")){
				//게시판 글삭제
				int seq = Integer.parseInt(request.getParameter("seq"));
				AdminDAO adao = AdminDAO.getInstance();
				adao.deleteBoard(seq);
				response.sendRedirect("boardFree.bo");
			}else if(cmd.contentEquals("/boardModify.bo")) { //게시판 글 수정 눌렀을때 
				int seq = Integer.parseInt(request.getParameter("seq"));
				
				AdminDAO adao = AdminDAO.getInstance();
				BoardDTO dto = adao.getBoardBySeq2(seq);
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("board/boardModify.jsp").forward(request, response);
			}else if(cmd.contentEquals("/boardModifyProc.bo")) { //게시판 글 수정 프로세스
				int seq = Integer.parseInt(request.getParameter("seq"));
				String animalHeader = request.getParameter("animalHeader");
				String title = Configuration.protectXSS(request.getParameter("title"));
				String content = request.getParameter("content");

				dao.update(seq, title, content, animalHeader);
				String header = AdminDAO.getInstance().getBoardBySeq2(seq).getHeader();
				
				if(header.contentEquals("자유")) {
					response.sendRedirect("boardFree.bo");
				}else{ //질문게시판으로 이동
					response.sendRedirect("boardQuestion.bo");
				}
			}else if(cmd.contentEquals("/expertDetail.bo")) {  //전문가 디테일 뷰
				int seq = Integer.parseInt(request.getParameter("seq"));
				AdminDAO adao = AdminDAO.getInstance();
				ExpertDTO dto = adao.getExpertBySeq(seq);
				request.setAttribute("dto", dto);
								
				request.getRequestDispatcher("board/boardExpertDetailView.jsp").forward(request, response);
			}else if(cmd.contentEquals("/freeCommentWrite.bo")) { //자유게시판 댓글 작성
				String comment = Configuration.protectXSS(request.getParameter("comment"));
				int boardSeq = Integer.parseInt(request.getParameter("boardSeq"));
				ComDTO dto = new ComDTO(0,boardSeq, id, comment,ipAddr,null);
				ComDAO.getInstance().insert(dto);
				
				JsonObject jobj = new JsonObject();
				jobj.addProperty("writer", dto.getWriter());
				jobj.addProperty("comment", comment);
				//jobj.addProperty("date", dto.getDate()); 
				
				pwriter.append(jobj.toString());
			}else if(cmd.contentEquals("/questionCommentWrite.bo")) { //질문게시판 댓글 작성
				String comment = Configuration.protectXSS(request.getParameter("comment"));
				int boardSeq = Integer.parseInt(request.getParameter("boardSeq"));
				ComDTO dto = new ComDTO(0,boardSeq, id, comment,ipAddr,null);
				ComDAO.getInstance().insert(dto);
				
				JsonObject jobj = new JsonObject();
				jobj.addProperty("writer", dto.getWriter());
				jobj.addProperty("comment", comment);
				//jobj.addProperty("date", dto.getDate()); 
				
				pwriter.append(jobj.toString());
			}else if(cmd.contentEquals("/freeCommentRemove.bo")) { //자유게시판 댓글 삭제
				int seq = Integer.parseInt(request.getParameter("seq"));
				int boardSeq = Integer.parseInt(request.getParameter("brdSeq"));
				ComDAO.getInstance().delete(seq);
				
				response.sendRedirect("freeDetail.bo?seq="+boardSeq);
			}else if(cmd.contentEquals("/questionCommentRemove.bo")) { //질문게시판 댓글 삭제
				int seq = Integer.parseInt(request.getParameter("seq"));
				int boardSeq = Integer.parseInt(request.getParameter("brdSeq"));
				ComDAO.getInstance().delete(seq);
				
				response.sendRedirect("questionDetail.bo?seq="+boardSeq);
			}else {
				// 등록되지 않은 경로로 입장시

				response.sendRedirect("main/error.jsp");
			}
		} catch(Exception e) {
					e.printStackTrace();
			response.sendRedirect("main/error.jsp");
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}