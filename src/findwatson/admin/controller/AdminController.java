package findwatson.admin.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import findwatson.admin.dao.AdminDAO;
import findwatson.admin.dao.AdminFileDAO;
import findwatson.admin.dao.ManagerDAO;
import findwatson.admin.dto.AdminFileDTO;
import findwatson.admin.dto.BanDTO;
import findwatson.admin.dto.ChartDTO;
import findwatson.admin.dto.ExpertDTO;
import findwatson.admin.dto.HListDTO;
import findwatson.admin.dto.NoticeDTO;
import findwatson.admin.dto.OneByOneCommentDTO;
import findwatson.board.dao.BoardDAO;
import findwatson.board.dao.ComDAO;
import findwatson.board.dao.ObODAO;
import findwatson.board.dto.BoardDTO;
import findwatson.board.dto.ComDTO;
import findwatson.board.dto.ObODTO;
import findwatson.configuration.Configuration;
import findwatson.member.dto.MemberDTO;
import findwatson.review.dto.ReviewDTO;

@WebServlet("*.admin")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String ipAddr = request.getRemoteAddr();
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String cmd = requestURI.substring(contextPath.length());
		String id = (String)request.getSession().getAttribute("adminInfo");


		AdminDAO dao = AdminDAO.getInstance();
		ManagerDAO Mdao = ManagerDAO.getInstance();
		ObODAO Odao = ObODAO.getInstance();
		AdminFileDAO fDao = AdminFileDAO.getInstance();
		PrintWriter pwriter = response.getWriter();

		System.out.println("cmd - " + cmd);
		try {
			//관리자 로그인
			if(cmd.contentEquals("/login.admin")) {
				String idInput = request.getParameter("id");
				String pwInput = request.getParameter("pw");
				boolean result = dao.adminLogin(idInput, pwInput);
				if(result) {
					request.getSession().setAttribute("adminInfo", idInput);					
				}
				request.setAttribute("result", result);
	
				request.getRequestDispatcher("admin/loginResultView.jsp").forward(request, response);

			}else if(cmd.contentEquals("/adminPwModify.admin")) {//관리자 비밀번호 변경
				String oriPw = request.getParameter("oriPw");
				String newPw = request.getParameter("newPw");
				System.out.println(oriPw + " : " + newPw);

				boolean pwCheck = dao.adminPwSameCheck(oriPw); 
				System.out.println(pwCheck);
				if(pwCheck) { //기존비밀번호와 일치했을때
					dao.adminInfoPwUpdate(id, newPw);
					JsonObject jobj = new JsonObject();
					jobj.addProperty("result", pwCheck);
					pwriter.append(jobj.toString());
				}else { //기존비밀번호와 일치하지 않을때
					JsonObject jobj = new JsonObject();
					jobj.addProperty("result", pwCheck);
					pwriter.append(jobj.toString());
				}
			}else if(cmd.contentEquals("/logout.admin")) {//관리자 로그아웃
				request.getSession().invalidate();
				response.sendRedirect("main/startAdmin.jsp");
			}else if(cmd.contentEquals("/adminMemberList.admin")) {//회원목록 전체
				//네비
				int cpage = 1;
				String param = request.getParameter("cpage");

				if(param!=null) {
					cpage = Integer.parseInt(param);	
				}

				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage-1);	
				int end = cpage * Configuration.recordCountPerPage;

				List<MemberDTO> list = dao.MemberListByPage(start, end);

				String pageNavi = dao.getMemberListPageNav(cpage);

				request.setAttribute("pageNavi", pageNavi);
				request.setAttribute("list", list);

				//

				request.getRequestDispatcher("/admin/adminMemberList.jsp").forward(request, response);

			}else if(cmd.contentEquals("/adminBanList.admin")) {//차단한 ip 목록

				//List<BanDTO> list = dao.selectBanList();
				//request.setAttribute("list", list);
				int cpage = 1;
				String param = request.getParameter("cpage");

				if(param!=null) {
					cpage = Integer.parseInt(param);	
				}

				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage-1);	
				int end = cpage * Configuration.recordCountPerPage;

				List<BanDTO> list = dao.BanListByPage(start, end);

				String pageNavi = dao.getBanListPageNav(cpage);

				request.setAttribute("pageNavi", pageNavi);
				request.setAttribute("list", list);

				//
				request.getRequestDispatcher("/admin/adminBanList.jsp").forward(request, response);
			}else if(cmd.contentEquals("/expertWrite.admin")){ //전문가 Q&A 글쓰기
				String title = Configuration.protectXSS(request.getParameter("boardTitle"));
				String content = request.getParameter("content");
				System.out.println(content);

				dao.insertToExpert(new ExpertDTO(0, id, title, content, null, 0));

				response.sendRedirect(contextPath + "/boardExpert.admin");
			}else if(cmd.contentEquals("/expertWriteImgUpload.admin")) {//전문가 Q&A 글쓰기-이미지 업로드
				String repositoryName = "expertImg";
				String uploadPath = request.getServletContext().getRealPath( "/" + repositoryName);
				System.out.println(uploadPath);
				File uploadFilePath = new File(uploadPath);
				if(!uploadFilePath.exists()) {
					uploadFilePath.mkdir();
				}

				int maxSize = 1024 * 1024 * 100;
				MultipartRequest multi = new MultipartRequest(request,uploadPath, maxSize,"UTF-8",new DefaultFileRenamePolicy());

				String fileName = multi.getFilesystemName("expertImg");
				String oriFileName = multi.getOriginalFileName("expertImg");
				System.out.println("원래 파일 이름 : " + oriFileName);
				System.out.println("올린 파일 이름 : " + fileName);

				fDao.insertImgToExpert(new AdminFileDTO(0, 0, fileName, oriFileName));

				//서버의 이미지 경로
				String imgPath = contextPath + "/" + repositoryName + "/" + fileName;
				System.out.println(imgPath);

				JsonObject jObj = new JsonObject();
				jObj.addProperty("imgPath", imgPath);
				pwriter.append(jObj.toString());
			}else if(cmd.contentEquals("/noticeWrite.admin")) {//공지사항 글쓰기
				String title = Configuration.protectXSS(request.getParameter("boardTitle"));
				String content = request.getParameter("content");
				System.out.println(content);

				dao.insertToNotice(new ExpertDTO(0, id, title, content, null, 0));

				response.sendRedirect(contextPath + "/boardNotice.admin");
			}else if(cmd.contentEquals("/noticeWriteImgUpload.admin")) {//공지사항 글쓰기 - 이미지 업로드
				String repositoryName = "noticeImg";
				String uploadPath = request.getServletContext().getRealPath("/" + repositoryName);
				System.out.println(uploadPath);
				File uploadFilePath = new File(uploadPath);
				if(!uploadFilePath.exists()) {
					uploadFilePath.mkdir();
				}
				int maxSize = 1024 * 1024 * 100;
				MultipartRequest multi = new MultipartRequest(request,uploadPath, maxSize,"UTF-8",new DefaultFileRenamePolicy());

				String fileName = multi.getFilesystemName("noticeImg");
				String oriFileName = multi.getOriginalFileName("noticeImg");
				System.out.println("원래 파일 이름 : " + oriFileName);
				System.out.println("올린 파일 이름 : " + fileName);

				fDao.insertImgToNotice(new AdminFileDTO(0, 0, fileName, oriFileName));

				//서버의 이미지 경로
				String imgPath = contextPath + "/" + repositoryName + "/" + fileName;
				System.out.println(imgPath);

				JsonObject jObj = new JsonObject();
				jObj.addProperty("imgPath", imgPath);
				pwriter.append(jObj.toString());
			}else if(cmd.contentEquals("/boardExpert.admin")){// 전문가Q&A 글 목록 출력
				String pageCategory = "boardExpert.bo";
				int cpage = 1;
				String page = request.getParameter("cpage");
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
				int end = cpage * Configuration.recordCountPerPage;
				List<ExpertDTO> list = BoardDAO.getInstance().selectByPageExpert(start, end);

				String pageNavi = BoardDAO.getInstance().getPageNavi(cpage,pageCategory, "전문가");
				request.setAttribute("list", list);
				request.setAttribute("pageNavi", pageNavi);
				request.getRequestDispatcher("admin/adminBoardExpert.jsp").forward(request, response);

			}else if(cmd.contentEquals("/boardNotice.admin")){// 공지사항 글 목록 출력
				String pageCategory = "boardNotice.admin";
				int cpage = 1;
				String page = request.getParameter("cpage");
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
				int end = cpage * Configuration.recordCountPerPage;

				List<NoticeDTO> list = BoardDAO.getInstance().selectByPageNotice(start, end);

				String pageNavi =  BoardDAO.getInstance().getPageNavi(cpage,pageCategory,"공지");

				request.setAttribute("list", list);
				request.setAttribute("pageNavi", pageNavi);
				request.getRequestDispatcher("admin/adminBoardNotice.jsp").forward(request, response);

			}else if(cmd.contentEquals("/admin/adminSearchNotice.admin")) {//공지사항에서 회원아이디 검색
				System.out.println("공지사항검색 진입성공");
				String idInput = request.getParameter("search");
				List<MemberDTO> list = dao.selectById("%"+idInput+"%");
				request.setAttribute("list", list);
				request.getRequestDispatcher("/admin/adminMemberList.jsp").forward(request, response);


			}else if(cmd.contentEquals("/boardFree.admin")){//자유게시판 글 출력
				String pageCategory = "boardFree.admin";
				int cpage = 1;
				String page = request.getParameter("cpage");
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
				int end = cpage * Configuration.recordCountPerPage;

				List<BoardDTO> list = BoardDAO.getInstance().selectByPage(start, end, "자유");
				String pageNavi = BoardDAO.getInstance().getPageNavi(cpage,pageCategory,"자유");

				request.setAttribute("list", list);
				request.setAttribute("pageNavi", pageNavi);
				request.getRequestDispatcher("admin/adminBoardFree.jsp").forward(request, response);
			}else if(cmd.contentEquals("/boardQuestion.admin")) { //질문게시판 글 출력
				String pageCategory = "boardQuestion.admin";
				int cpage = 1;
				String page = request.getParameter("cpage");
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
				int end = cpage * Configuration.recordCountPerPage;

				List<BoardDTO> list = BoardDAO.getInstance().selectByPage(start, end, "질문");
				String pageNavi = BoardDAO.getInstance().getPageNavi(cpage,pageCategory,"질문");

				request.setAttribute("list", list);
				request.setAttribute("pageNavi", pageNavi);
				request.getRequestDispatcher("admin/adminBoardQuestion.jsp").forward(request, response);

				// 자유게시판 카테고리별 
			}else if(cmd.contentEquals("/searchFree.admin")) {
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
				request.getRequestDispatcher("/admin/adminBoardFree.jsp").forward(request, response);

				// 질문게시판
			}else if(cmd.contentEquals("/searchQuestion.admin")) {
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
				request.getRequestDispatcher("/admin/adminBoardQuestion.jsp").forward(request, response);



			}else if(cmd.contentEquals("/admin/adminDeleteMember.admin")) {//회원 차단기능
				String idInput = request.getParameter("id");
				String ipAddrInput = request.getParameter("ip");
				String reason = request.getParameter("reason");

				dao.insertbanIp(idInput, ipAddrInput, reason);
				int result = dao.deleteMember(idInput);
				if(result > 0) {
					System.out.println("아이디 삭제 성공");
					response.sendRedirect(contextPath + "/adminMemberList.admin");
				}
			}else if(cmd.contentEquals("/admin/adminSearchMember.admin")) {//회원목록에서 회원아이디 검색
				System.out.println("회원아이디검색 진입성공");
				String idInput = request.getParameter("search");
				List<MemberDTO> list = dao.selectById(idInput);
				request.setAttribute("list", list);
				request.getRequestDispatcher("/admin/adminMemberList.jsp").forward(request, response);

				// 병원 정보 입력
			} else if(cmd.contentEquals("/hosptInfoInsert.admin")) { //병원 정보 등록
				String repositoryName = "hospitalImg";
				String uploadPath = request.getServletContext().getRealPath("/" + repositoryName);
				System.out.println(uploadPath);
				File uploadFilePath = new File(uploadPath);
				if(!uploadFilePath.exists()) {
					uploadFilePath.mkdir();
				}

				int maxSize = 1024 * 1024 * 10; // 10MB 용량 제한
				MultipartRequest multi = new MultipartRequest(request, uploadPath, maxSize, "UTF8", new DefaultFileRenamePolicy());

				String name = Configuration.protectXSS(multi.getParameter("name"));
				int postcode = Integer.parseInt(multi.getParameter("postcode"));
				String address1 = Configuration.protectXSS(multi.getParameter("address1"));
				String address2 = Configuration.protectXSS(multi.getParameter("address2"));
				String phone = multi.getParameter("phone");
				String homepage = Configuration.protectXSS(multi.getParameter("homepage"));
				String[] medicalAnimalArr = multi.getParameterValues("medicalAnimal");
				String[] openTimeArr = multi.getParameterValues("openTime");
				String image = contextPath + "/" + repositoryName + "/" + multi.getFilesystemName("image");

				String medicalAnimal = Arrays.toString(medicalAnimalArr).replace("{","").replace("}","").replace("[","").replace("]","").replace(", ",";");
				if(medicalAnimal.contentEquals("null")) {
					medicalAnimal = "";
				}

				String openTime = Arrays.toString(openTimeArr).replace("{","").replace("}","").replace("[","").replace("]","").replace(", ",";");
				if(openTime.contentEquals("null")) {
					openTime = "";
				}
				System.out.println("animal : " + openTime);

				//System.out.println(uploadPath);
				//System.out.println(name+"/"+postcode+"/"+address1+"/"+address2+"/"+phone+"/"+homepage+"/"+medicalAnimal.length+"/"+openTime.length+"/"+image);

				HListDTO Hdto = new HListDTO(0,name,postcode,address1,address2,phone,homepage,image,medicalAnimal,openTime,null,0);
				int result = dao.insertHospitalInfo(Hdto);
				if(result > 0) {
					System.out.println("병원 정보 입력 성공");
					response.sendRedirect("hosptInfoList.admin");
				}


			}else if(cmd.contentEquals("/hosptInfoModify.admin")) {//병원 정보 수정
				String repositoryName = "hospitalImgModify";
				String uploadPath = request.getServletContext().getRealPath("/" + repositoryName);

				File uploadFilePath = new File(uploadPath);
				if(!uploadFilePath.exists()) {
					uploadFilePath.mkdir();
				}

				int maxSize = 1024 * 1024 * 10; // 10MB 용량 제한
				MultipartRequest multi = new MultipartRequest(request, uploadPath, maxSize, "UTF8", new DefaultFileRenamePolicy());

				String name = Configuration.protectXSS(multi.getParameter("name"));
				int postcode = Integer.parseInt(multi.getParameter("postcode"));
				int seq = Integer.parseInt(multi.getParameter("seq"));
				HListDTO dto = dao.getHListBySeq(seq);
				String address1 = Configuration.protectXSS(multi.getParameter("address1"));
				String address2 = Configuration.protectXSS(multi.getParameter("address2"));
				String phone = multi.getParameter("phone");
				String homepage = Configuration.protectXSS(multi.getParameter("homepage"));
				String[] medicalAnimalArr = multi.getParameterValues("medicalAnimal");
				String[] openTimeArr = multi.getParameterValues("openTime");
				String image = contextPath + "/" + repositoryName + "/" + multi.getFilesystemName("image");

				String medicalAnimal = Arrays.toString(medicalAnimalArr).replace("{","").replace("}","").replace("[","").replace("]","").replace(", ",";");
				if(medicalAnimal.contentEquals("null")) {
					medicalAnimal = "";
				}

				String openTime = Arrays.toString(openTimeArr).replace("{","").replace("}","").replace("[","").replace("]","").replace(", ",";");
				if(openTime.contentEquals("null")) {
					openTime = "";
				}
				System.out.println("animal : " + openTime);

				//System.out.println(uploadPath);
				//System.out.println(name+"/"+postcode+"/"+address1+"/"+address2+"/"+phone+"/"+homepage+"/"+medicalAnimal.length+"/"+openTime.length+"/"+image);

				HListDTO Hdto = new HListDTO(seq,name,postcode,address1,address2,phone,homepage,image,medicalAnimal,openTime,null,dto.getViewCount());
				int result = dao.updateHospitalInfo(Hdto);
				if(result > 0) {
					System.out.println("병원 정보 입력 성공");
					response.sendRedirect("hosptInfoList.admin");
				}

			
			}else if(cmd.contentEquals("/hosptInfoList.admin")) {// 병원 리스트 출력

				int cpage = 1;
				String page = request.getParameter("cpage");

				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}

				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
				int end = cpage * Configuration.recordCountPerPage;

				List<HListDTO> list = Mdao.hosptListByPage(start, end);
				String pageNavi = Mdao.getHosptListPageNav(cpage);

				request.setAttribute("list", list);
				request.setAttribute("pageNavi", pageNavi);
				request.getRequestDispatcher("admin/adminHosptList.jsp").forward(request, response);

				// 병원 정보 상세
			} else if(cmd.contentEquals("/hosptInfoDetailView.admin")) {
				int seq = Integer.parseInt(request.getParameter("seq"));
				String imglocation = "/FindWatson/hospitalImg";
				HListDTO dto = Mdao.hosptInfo(seq);

				request.setAttribute("imglocation", imglocation);
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("admin/adminHosptDetailView.jsp").forward(request, response);

				
			}else if(cmd.contentEquals("/adminHosptReviewList.admin")) {
				int cpage = 1;
				String param = request.getParameter("cpage");

				if(param!=null) {
					cpage = Integer.parseInt(param);	
				}

				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage-1);	
				int end = cpage * Configuration.recordCountPerPage;

				List<ReviewDTO> list = dao.hosptReviewListByPage(start, end);

				String pageNavi = dao.getHosptReviewListPageNav(cpage);

				request.setAttribute("pageNavi", pageNavi);
				request.setAttribute("list", list);

				//
				request.getRequestDispatcher("/admin/adminHosptReviewList.jsp").forward(request, response);
				
			//병원 리뷰 삭제	
			}else if (cmd.contentEquals("/memberReviewRemove.admin")) {
				int seq = Integer.parseInt(request.getParameter("seq"));
				dao.deleteHosptReview(seq);
				response.sendRedirect(contextPath + "/adminHosptReviewList.admin");
	
				// 1:1 문의 게시글 출력

			} else if(cmd.contentEquals("/adminOneByOne.admin")) {

				int cpage = 1;
				String page = request.getParameter("cpage");
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
				int end = cpage * Configuration.recordCountPerPage;

				List<ObODTO> list = Odao.ObOByPage(start, end);
				String pageNavi = Odao.getObOPageNav(cpage);
				
				request.setAttribute("cpage", cpage);
				request.setAttribute("list", list);
				request.setAttribute("pageNavi", pageNavi);
				request.getRequestDispatcher("admin/adminBoardObO.jsp").forward(request, response);
				// 1:1 문의게시판 디테일 뷰
			} else if(cmd.contentEquals("/adminObODetailView.admin")) {
				int cpage = 1;
				String page = request.getParameter("cpage");
				System.out.println(page);
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				
				int ObOSeq = Integer.parseInt(request.getParameter("seq"));
				ObODTO dto = Odao.getObOBySeq(ObOSeq);
				request.setAttribute("dto", dto);
				//이미 잇는 댓글
				List<OneByOneCommentDTO> resultList = dao.commentsList((ObOSeq));
				request.setAttribute("cpage", cpage);
				request.setAttribute("commentList", resultList);
				request.getRequestDispatcher("admin/adminObODetailView.jsp").forward(request, response);

			}else if(cmd.contentEquals("/adminComment.admin")) {//1:1문의 댓글 남기기
				
				int onebyoneSeq = Integer.parseInt(request.getParameter("seq"));
				String comment = request.getParameter("comment");
				
				int result = dao.insertComments(onebyoneSeq, comment);
				int updateHeader = dao.updateHeader(onebyoneSeq);
				
				OneByOneCommentDTO data = dao.getLastest(onebyoneSeq);
				JsonObject obj = new JsonObject();
				if (result>0) {
					obj.addProperty("comment", data.getComment());
					obj.addProperty("date", data.getFormedDate());
				}else {					
					obj.addProperty("result", false);				
				}
				response.getWriter().append(obj.toString());
				
				
				
			}else if(cmd.contentEquals("/adminMemberChart.admin")) {//회원통계


				System.out.println("회원차트 진입성공");
				//회원정보
				int totalCount = dao.recordMemberListTotalCount();
				int memberMCount = dao.recordMemberMTotalCount();
				int memberWCount = dao.recordMemberWTotalCount();
				request.setAttribute("totalCount", totalCount);
				request.setAttribute("memberMCount", memberMCount);
				request.setAttribute("memberWCount", memberWCount);
				//관심동물
				int bird = dao.recordBirdTotalCount();
				int fish = dao.recordFishTotalCount();
				int hamster = dao.recordHamsterTotalCount();
				int rabbit = dao.recordRabbitTotalCount();
				int dochi = dao.recordDochiTotalCount();
				int reptile = dao.recordReptileTotalCount();
				int bug = dao.recordBugTotalCount();
				int other = dao.recordOtherTotalCount();
				request.setAttribute("bird", bird);
				request.setAttribute("fish", fish);
				request.setAttribute("hamster", hamster);
				request.setAttribute("rabbit", rabbit);
				request.setAttribute("dochi", dochi);
				request.setAttribute("reptile", reptile);
				request.setAttribute("bug", bug);
				request.setAttribute("other", other);
				//가입경로
				int directSearch = dao.recordDirectSearchTotalCount();
				int keywordSearch = dao.recordKeywordSearchTotalCount();
				int introduce = dao.recordIntroduceTotalCount();
				int otherSearch = dao.recordOtherSearchTotalCount();
				request.setAttribute("directSearch", directSearch);
				request.setAttribute("keywordSearch", keywordSearch);
				request.setAttribute("introduce", introduce);
				request.setAttribute("otherSearch", otherSearch);
				//인기게시물 top5
				List<ChartDTO> top5List = dao.recordTop5();
				request.setAttribute("top5List", top5List);
				request.getRequestDispatcher("/admin/adminMemberChart.jsp").forward(request, response);

			}else if(cmd.contentEquals("/adminNoticeDetailView.admin")) { //관리자 - 공지에서 글 클릭했을때

				int noticeSeq = Integer.parseInt(request.getParameter("seq"));
				NoticeDTO dto = dao.getNoticeBySeq(noticeSeq);
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("admin/adminNoticeDetailView.jsp").forward(request, response);
			}else if(cmd.contentEquals("/adminExpertDetailView.admin")) {//관리자 - 전문가 Q&A에서 글 클릭했을때
				int expertSeq = Integer.parseInt(request.getParameter("seq"));
				ExpertDTO dto = dao.getExpertBySeq(expertSeq);
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("admin/adminExpertDetailView.jsp").forward(request, response);
			}else if(cmd.contentEquals("/adminFreeDetailView.admin")) {//관리자 - 자유게시판에서 글 클릭했을때
				int freeSeq = Integer.parseInt(request.getParameter("seq"));
				dao.increBoardView(freeSeq);
				BoardDTO dto = dao.getBoardBySeq(freeSeq,"자유");
				request.setAttribute("dto", dto);
				
				//댓글 불러오기
				int cpage = 1;
				String page = request.getParameter("cpage");
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
				int end = cpage * Configuration.recordCountPerPage;
				String pageNavi = ComDAO.getInstance().getPageNaviCmtFreeAdmin(cpage, freeSeq);
				List<ComDTO> list = ComDAO.getInstance().selectByPage(freeSeq, start, end);
				request.setAttribute("pageNavi", pageNavi);
				request.setAttribute("list", list);
				
				request.getRequestDispatcher("admin/adminFreeDetailView.jsp").forward(request, response);
			}else if(cmd.contentEquals("/adminQuestionDetailView.admin")) {//관리자 - 질문게시판에서 글 클릭했을때
				int questionSeq = Integer.parseInt(request.getParameter("seq"));
				dao.increBoardView(questionSeq);
				BoardDTO dto = dao.getBoardBySeq(questionSeq,"질문");
				request.setAttribute("dto", dto);
				
				//댓글 불러오기
				int cpage = 1;
				String page = request.getParameter("cpage");
				if(page != null) {
					cpage = Integer.parseInt(request.getParameter("cpage"));
				}
				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
				int end = cpage * Configuration.recordCountPerPage;
				String pageNavi = ComDAO.getInstance().getPageNaviCmtQuestionAdmin(cpage, questionSeq);
				List<ComDTO> list = ComDAO.getInstance().selectByPage(questionSeq, start, end);
				request.setAttribute("pageNavi", pageNavi);
				request.setAttribute("list", list);
				
				request.getRequestDispatcher("admin/adminQuestionDetailView.jsp").forward(request, response);
			}else if(cmd.contentEquals("/questionCommentRemove.admin")) {//관리자-질문 댓글삭제
				int seq = Integer.parseInt(request.getParameter("seq"));
				int boardSeq = Integer.parseInt(request.getParameter("brdSeq"));
				ComDAO.getInstance().delete(seq);
				
				response.sendRedirect("adminQuestionDetailView.admin?seq="+boardSeq);
			}else if(cmd.contentEquals("/freeCommentRemove.admin")) {//관리자-자유 댓글삭제
				int seq = Integer.parseInt(request.getParameter("seq"));
				int boardSeq = Integer.parseInt(request.getParameter("brdSeq"));
				ComDAO.getInstance().delete(seq);
				
				response.sendRedirect("adminFreeDetailView.admin?seq="+boardSeq);
			}else if(cmd.contentEquals("/boardRemove.admin")) { //커뮤니티게시판 글삭제
				int seq = Integer.parseInt(request.getParameter("seq"));
				dao.deleteBoard(seq);
				response.sendRedirect(contextPath + "/boardQuestion.admin");
			}else if(cmd.contentEquals("/expertRemove.admin")) {//전문가 게시판 글삭제
				int seq = Integer.parseInt(request.getParameter("seq"));
				System.out.println(seq);
				dao.deleteExpert(seq);
				response.sendRedirect(contextPath + "/boardExpert.admin");
			}else if(cmd.contentEquals("/noticeRemove.admin")) {//공지사항 글삭제
				int seq = Integer.parseInt(request.getParameter("seq"));
				dao.deleteNotice(seq);
				response.sendRedirect(contextPath + "/boardNotice.admin");
			}else if(cmd.contentEquals("/start.admin")) {
				response.sendRedirect(contextPath + "/main/adminLogin.jsp");
			}else if(cmd.contentEquals("/hosptRemove.admin")) { // 병원 정보 삭제
				int seq = Integer.parseInt(request.getParameter("seq"));
				dao.deleteHospt(seq);
				response.sendRedirect("hosptInfoList.admin");
			}else if(cmd.contentEquals("/hosptModify.admin")) {//병원 정보 수정
				int seq = Integer.parseInt(request.getParameter("seq"));
				HListDTO dto = dao.getHListBySeq(seq);
				request.setAttribute("dto",dto);
				request.getRequestDispatcher("admin/adminModifyHospt.jsp").forward(request, response);
			}else if(cmd.contentEquals("/noticeModify.admin")) { //공지 수정 폼으로 이동
				int seq = Integer.parseInt(request.getParameter("seq"));
				NoticeDTO dto = dao.getNoticeBySeq(seq);
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("admin/noticeModify.jsp").forward(request, response);
			}else if(cmd.contentEquals("/noticeModifyProc.admin")) { //공지 수정 프로세스
				String title = Configuration.protectXSS(request.getParameter("boardTitle"));
				String content = request.getParameter("content");
				int seq= Integer.parseInt(request.getParameter("seq"));
				
				System.out.println(content);
				dao.updateNotice(seq, title, content);
				response.sendRedirect(contextPath + "/adminNoticeDetailView.admin?seq="+seq);
			}else if(cmd.contentEquals("/expertModify.admin")) {//전문가 Q&A 수정 폼으로 이동
				int seq = Integer.parseInt(request.getParameter("seq"));
				ExpertDTO dto = dao.getExpertBySeq(seq);
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("admin/expertModify.jsp").forward(request, response);
			}else if(cmd.contentEquals("/expertModifyProc.admin")) {//전문가 Q&A 수정 프로세스
				String title = Configuration.protectXSS(request.getParameter("boardTitle"));
				String content = request.getParameter("content");
				int seq= Integer.parseInt(request.getParameter("seq"));
				
				dao.updateExpert(seq, title, content);
				response.sendRedirect(contextPath + "/adminExpertDetailView.admin?seq="+seq);
			}else{
				response.sendRedirect(contextPath + "/error.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(contextPath + "/error.jsp");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
