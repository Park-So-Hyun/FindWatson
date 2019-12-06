package findwatson.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import findwatson.admin.dao.AdminDAO;
import findwatson.admin.dao.ManagerDAO;
import findwatson.admin.dto.ExpertDTO;
import findwatson.admin.dto.HListDTO;
import findwatson.admin.dto.NoticeDTO;
import findwatson.board.dto.BoardDTO;
import findwatson.configuration.Configuration;
import findwatson.search.dao.HospitalListDAO;


@WebServlet("*.manager")
public class ManagerController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String cmd = requestURI.substring(contextPath.length());
		ManagerDAO dao = ManagerDAO.getInstance();
		try {
			if(cmd.contentEquals("/admin/adminBoardHosptList.manager")) {
			
				
				
				
				
				int cpage = 1;
				String param = request.getParameter("cpage");

				if(param!=null) {
					cpage = Integer.parseInt(param);	
				}

				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage-1);	
				int end = cpage * Configuration.recordCountPerPage;

				List<HListDTO> list = dao.hosptListByPage(start, end);

				String pageNavi = dao.getHosptListPageNav(cpage);

				request.setAttribute("pageNavi", pageNavi);
				request.setAttribute("list", list);

				request.getRequestDispatcher("/admin/adminBoardHosptList.jsp").forward(request, response);
		
				
				
				
				
				
				
				
				


			}else if(cmd.contentEquals("/admin/adminInsertHospt.manager")) {
				//여기 안씀
				System.out.println("병원등록 진입 성공");
				String name = request.getParameter("name");
				String postcode = request.getParameter("postcode");
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				String phone = request.getParameter("phone");
				String homepage = request.getParameter("homepage");
				String img = "이미지 아직 안함";
				String [] medicalAnimal = request.getParameterValues("medicalAnimal");
				String [] openTime = request.getParameterValues("openTime");
				
				System.out.println(name + " " +postcode+ " " +address1+ " " +address2+ " " +phone+ " " +homepage+ " " + 
				medicalAnimal[0]+ " " +medicalAnimal[1]+ " " +openTime[0]+ " " +openTime[1]);
	
				String animal = Arrays.toString(medicalAnimal).replace("{","").replace("}","").replace("[","").replace("]","").replace(", ",";");
				System.out.println(animal.contentEquals("null"));
				if(animal.contentEquals("null")) {
					animal = "";
				}
				System.out.println("animal : " + animal);
				

				String time = Arrays.toString(openTime).replace("{","").replace("}","").replace("[","").replace("]","").replace(", ",";");
				//System.out.println( time.equals("null"));
				if(time.contentEquals("null")) {
					time = "";
				}
				System.out.println("time : " + time);
				
				HListDTO dto = new HListDTO(0,name,postcode,address1,address2,phone,homepage,img,animal,time,null,0);
				int result = dao.insertHospital(dto);
				System.out.println(result);
				if(result > 0) {
					System.out.println("db저장 성공");
					
				}
				else {
					System.out.println("db저장 실패");
				}
				
				//
				

			}
			else if(cmd.contentEquals("/adminModifyHospt.manager")) {
				String repositoryName = "hospitalImg";
				String uploadPath = request.getServletContext().getRealPath("/" + repositoryName);

				File uploadFilePath = new File(uploadPath);
				if(!uploadFilePath.exists()) {
					uploadFilePath.mkdir();
				}

				int maxSize = 1024 * 1024 * 10; // 10MB 용량 제한
				MultipartRequest multi = new MultipartRequest(request, uploadPath, maxSize, "UTF8", new DefaultFileRenamePolicy());

				String name = multi.getParameter("name");
				String postcode = multi.getParameter("postcode");
				String address1 = multi.getParameter("address1");
				String address2 = multi.getParameter("address2");
				String phone = multi.getParameter("phone");
				String homepage = multi.getParameter("homepage");
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

				HListDTO Hdto = new HListDTO(0,name,postcode,address1,address2,phone,homepage,image,medicalAnimal,openTime,null,0);
				AdminDAO.getInstance().updateHospitalInfo(Hdto);
					System.out.println("병원 정보 입력 성공");
					response.sendRedirect("hosptInfoList.admin");

			}
			
			//공지사항 네비 적용된 목록
			else if(cmd.contentEquals("/admin/adminBoardNotice.manager")) {
				System.out.println("공지사항 진입 성공");


				int cpage = 1;
				String param = request.getParameter("cpage");

				if(param!=null) {
					cpage = Integer.parseInt(param);	
				}

				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage-1);	
				int end = cpage * Configuration.recordCountPerPage;

				List<NoticeDTO> list = dao.noticeListByPage(start, end);

				String pageNavi = dao.getNoticeListPageNav(cpage);

				request.setAttribute("pageNavi", pageNavi);
				request.setAttribute("list", list);

				request.getRequestDispatcher("/admin/adminBoardNotice.jsp").forward(request, response);
			}else if(cmd.contentEquals("/admin/adminBoardExpert.manager")){
				System.out.println("전문가 진입 성공");


				int cpage = 1;
				String param = request.getParameter("cpage");

				if(param!=null) {
					cpage = Integer.parseInt(param);	
				}

				int start = cpage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage-1);	
				int end = cpage * Configuration.recordCountPerPage;

				List<ExpertDTO> list = dao.expertListByPage(start, end);

				String pageNavi = dao.getExpertListPageNav(cpage);

				request.setAttribute("pageNavi", pageNavi);
				request.setAttribute("list", list);

				request.getRequestDispatcher("/admin/adminBoardExpert.jsp").forward(request, response);
			}else if(cmd.contentEquals("자유게시판 목록")) {
				List<BoardDTO> list = dao.fBoardList();
				request.setAttribute("list", list);
			}else if(cmd.contentEquals("자유게시판 글 아이디로 찾기")) {
				String id = request.getParameter("id");
				List<BoardDTO> list = dao.fBoardById(id);
				request.setAttribute("list", list);
			}else if(cmd.contentEquals("질문게시판 목록")) {
				List<BoardDTO> list = dao.qBoardList();
				request.setAttribute("list", list);
			}else if(cmd.contentEquals("질문게시판 글 아이디로 찾기")) {
				String id = request.getParameter("id");
				List<BoardDTO> list = dao.qBoardById(id);
				request.setAttribute("list", list);
			}else if(cmd.contentEquals("1:1문의게시판 목록")) {
				dao.oneByOneList();
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
