package findwatson.search.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;

import findwatson.admin.dto.HListDTO;
import findwatson.configuration.Configuration;
import findwatson.search.dao.HospitalListDAO;

@WebServlet("*.s")
public class SearchController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8"); 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String cmd = requestURI.substring(contextPath.length());


		try {
			HospitalListDAO dao = HospitalListDAO.getInstance();
			//지역 서울 선택시 해당하는 구 List 보내주기
			if(cmd.contentEquals("/selectGu.s")) {

				String city = request.getParameter("city");

				List<String> result = dao.selectGu(city);

				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");

				JsonArray j = new JsonArray();
				for(String tmp:result) {
					j.add(tmp);
				}

				PrintWriter pw = response.getWriter();				
				pw.append(j.toString());  	

				
			}else if(cmd.contentEquals("/searchFrom.s")) {
				// 옵션별 검색 결과 받아오는 부분 

				String address1 = request.getParameter("address1");			
				String address2 = request.getParameter("address2");

				if (address1.contentEquals("null") & !(address2.contentEquals("null"))) {
					address1 = "";

				}else if (address2.contentEquals("null") & !(address1.contentEquals("null"))){
					address2 = "";
				
				}else if (address1.contentEquals("null") & address2.contentEquals("null")) {
					address1 = "";
					address2 = "";
					
				}else {
					System.out.println("address 1,2 다 들어있는 경우 ");
				}


				String[] animal_check = request.getParameterValues("animal");
				String animal = Arrays.toString(animal_check).replace("{","").replace("}","").replace("[","").replace("]","").replace(", ",";");
			
				if(animal.contentEquals("null")) {
					animal = "";
				}

				String[] time_check = request.getParameterValues("time");
				//System.out.println(Arrays.toString(time_check));
				// NULL이 NULL이라는 문자열로 바뀌었다.. 
				String time = Arrays.toString(time_check).replace("{","").replace("}","").replace("[","").replace("]","").replace(", ",";");
				//System.out.println( time.equals("null"));
				if(time.contentEquals("null")) {
					time = "";
				}


				//for(HListDTO list2 : list) {
				//	System.out.println(list2);}

				// 네비게이터 받아오는 부분 
				int currentPage =1;
				String page = request.getParameter("currentPage");
				if(page != null) {
					currentPage = Integer.parseInt(page);
				}
				int start = currentPage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage-1);
				int end = currentPage * Configuration.recordCountPerPage;

				List<HListDTO> list = new ArrayList<>();
				list = HospitalListDAO.getInstance().searchByOption(address1, address2, animal, time);
				request.setAttribute("list", list);
				request.getRequestDispatcher("/search/hospitalSearchView.jsp").forward(request, response);

				// 병원 이름 클릭하면 상세페이지 
			}else if(cmd.contentEquals("/contents.s")) {
				// 내용 전송 
				int seq = Integer.parseInt(request.getParameter("seq"));
			
				HListDTO contents = HospitalListDAO.getInstance().select(seq);
			

				HospitalListDAO.getInstance().plusss(contents.getViewCount(),seq);
				request.setAttribute("contents", contents);

				request.getRequestDispatcher("/search/hospitalSearchDetail_1.jsp").forward(request, response);

				
			}else if(cmd.contentEquals("/contentsReview.s")){ 
				// 여기 review controllert 로 XXXXX
				int seq = Integer.parseInt(request.getParameter("seq"));

				HListDTO contents = HospitalListDAO.getInstance().select(seq);
			

				HospitalListDAO.getInstance().plusss(contents.getViewCount(),seq);
				request.setAttribute("contents", contents);

				request.getRequestDispatcher("/search/hospitalSearchDetail2.jsp").forward(request, response);
			
				
			}else if(cmd.contentEquals("/keywordSearch.s")) {
				// 상단 검색바에서 검색하면 목록 가져오기 
				// 검색어를 가져온다 

				String keyword = request.getParameter("keywordSearch");
				// 공백으로 나눠서 배열에 담는다 
				String arr[] = keyword.split(" ");
		 
				// 네비게이터 받아오는 부분 
				int currentPage =1;
				String page = request.getParameter("currentPage");
				if(page != null) {
					currentPage = Integer.parseInt(page);
				}
				int start = currentPage * Configuration.recordCountPerPage - (Configuration.recordCountPerPage-1);
				int end = currentPage * Configuration.recordCountPerPage;

				List<HListDTO> list = new ArrayList<>();
				List<HListDTO> list2 = new ArrayList<>();

				// 배열의 개수만큼 검색한다
				for(int i=0; i<arr.length; i++) {
					list = HospitalListDAO.getInstance().selectByPageTotal(arr[i], start, end);
					// 배열에서 dto를 하나씩 빼내서 list에 넣는다 
					for(HListDTO dto : list){
						list2.add(dto);
						
					}
				}

				Set<HListDTO> set = new HashSet<>();
				for(HListDTO repe : list2){
					int count = Collections.frequency(list2, repe);
				
					if(count == arr.length) {
						set.add(repe);//여기서 중복 병원은 세번씩 들어가니까 list대신 중복 막아주는 set 사용하기
					
					}
				}

				// 3번 중복된 값만 list에 담아 jsp에 보내기 
				request.setAttribute("list", set);
		

				request.getRequestDispatcher("/search/hospitalSearchView.jsp").forward(request, response);
			
			}else if(cmd.contentEquals("/simpleMove.s")) {
				//같은 페이지인데 메뉴로 이동할때와 검색후 이동할때 경로가 달라 header문제 발생해서 단순 이동도 서블렛 거치게 추가
				//경로가 같도록 forward사용
				request.getRequestDispatcher("/search/hospitalSearchView.jsp").forward(request, response);
			}


		}catch(Exception e) {
			e.printStackTrace();
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
