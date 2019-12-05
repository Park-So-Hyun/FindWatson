package findwatson.main.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import findwatson.admin.dto.NoticeDTO;
import findwatson.board.dao.BoardDAO;
import findwatson.board.dto.BoardDTO;
import findwatson.member.dao.MemberDAO;


@WebServlet("*.main")
public class MainController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String URI = request.getRequestURI(); 
		String ctxpath = request.getContextPath(); 
		String cmd = URI.substring(ctxpath.length()); 
		String ipAddr = request.getRemoteAddr();		
		
		try {
			if(cmd.contentEquals("/toMain.main")) {
				//차단된 아아피인지 확인하기 + 메인에 띄울 공지, 최신글 받아오기 request
				MemberDAO dao = MemberDAO.getInstance();
				boolean result = dao.banCheck(ipAddr);
				
				if(result){
					//차단된 아이피인 경우
					String reason =dao.banReasonByIp(ipAddr);
					request.setAttribute("reason", reason);
					request.getRequestDispatcher("main/banIndex.jsp").forward(request, response);
				}else {
					//인덱스로 + 메인에 띄울 공지, 최신글 받아오기 request 가지고 가기
					
					//1. 공지 받아오기(최근 5개)
					BoardDAO bdao = BoardDAO.getInstance();
					List<NoticeDTO> noticeList = new ArrayList<>();
					noticeList = bdao.getMainNotice();
					request.setAttribute("noticeList", noticeList);
										
					//2. 자유게시판 (최근5개)
					List<BoardDTO> freeList = new ArrayList<>();
					freeList = bdao.getMainFree();
					request.setAttribute("freeList", freeList);
					
					request.getRequestDispatcher("main/index.jsp").forward(request, response);
				}	
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
