package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Depreciation;
import model.Message;
import model.Roles;
import repository.DepreciationRepository;
import repository.RBACRepository;

/**
 * Servlet implementation class DepreciationControllerServlet
 */
/*@WebServlet("/DepreciationControllerServlet")*/
public class DepreciationControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String page = "";
    private String whoami;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DepreciationControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//System.out.println("we are proudly here!");
		page = "views/famsAssetDepreciation.jsp";
		HttpSession mySession = request.getSession();
		whoami = (String)mySession.getAttribute("username");
		findAllDepreciation(request,response,page);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession mySession = request.getSession();
		whoami = (String)mySession.getAttribute("username");
		String action = request.getPathInfo();
		switch(action) {
		case "/create" :
			createDepreciation(request,response);
			break;
		case "/edit" :
			findOne(request,response);
			break;
		case "/update" :
			updateDepreciation(request,response);
			break;
		case "/delete" :
			deleteDepreciation(request,response);
			break;
		}
	}
	public void createDepreciation(HttpServletRequest request, HttpServletResponse response) {
		String deprCode = request.getParameter("deprCode");
		String deprDesc = request.getParameter("deptDesc");
		float deprRate = Float.parseFloat(request.getParameter("deprRate").toString());
		Depreciation depreciation = new Depreciation();
		depreciation.setCode(deprCode);
		depreciation.setDescription(deprDesc);
		depreciation.setRate(deprRate);
		depreciation.setMaker(whoami);
		
		DepreciationRepository dsr = new DepreciationRepository();
		Message msg = new Message();
		if(dsr.save(depreciation)) {
			msg.setSuccessMsg("Record Saved Successfully!");
		}else {
			msg.setErrorMsg("Error while savind a record!");
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		page = "../views/famsAssetDepreciation.jsp";
		findAllDepreciation(request,response,page);
		
	}
	
	public void updateDepreciation(HttpServletRequest request, HttpServletResponse response) {
		String deprCode = request.getParameter("deprCode");
		String deprDesc = request.getParameter("deptDesc");
		int id = Integer.parseInt(request.getParameter("id").toString());
		float deprRate = Float.parseFloat(request.getParameter("deprRate").toString());
		Depreciation depreciation = new Depreciation();
		depreciation.setId(id);
		depreciation.setCode(deprCode);
		depreciation.setDescription(deprDesc);
		depreciation.setRate(deprRate);
		
		DepreciationRepository dsr = new DepreciationRepository();
		Message msg = new Message();
		if(dsr.update(depreciation)) {
			msg.setSuccessMsg("Record updated Successfully!");
		}else {
			msg.setErrorMsg("Error while updating a record!");
		}
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg", msg);
		page = "../views/famsAssetDepreciation.jsp";
		findAllDepreciation(request,response,page);
	}
	public void deleteDepreciation(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		DepreciationRepository dr = new DepreciationRepository();
		Message msg = new Message();
		if(dr.delete(id)) {
			msg.setSuccessMsg("Record Deleted Successfuly!");
		}else {
			msg.setErrorMsg("Error Occurred while deleting the record");
		}
			request.setAttribute("msg",msg);
			request.setAttribute("onServer", "yes");
			String page = "../views/famsAssetDepreciation.jsp";
			findAllDepreciation(request,response,page);
		
	}
	public void findOne(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		DepreciationRepository dr = new DepreciationRepository();
		Depreciation editDepreciation = dr.edit(id);
		request.setAttribute("editDepreciation", editDepreciation);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetDepreciation.jsp";
		findAllDepreciation(request,response,page);
	}
	public void findAllDepreciation(HttpServletRequest request, HttpServletResponse response,String page) {
		DepreciationRepository dsr = new DepreciationRepository();
		List<Depreciation> depreciationList = dsr.fetch();
		//fetching roles
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(2025);
		request.setAttribute("userRoleLists", userRoleLists);
		request.setAttribute("depreciationList", depreciationList);
		request.setAttribute("total", depreciationList.size());
		//request.setAttribute("onServer", true);
		dispatcher(request,response,page);
	}
	public void dispatcher(HttpServletRequest request, HttpServletResponse response, String page) {
		try {
			RequestDispatcher ds = request.getRequestDispatcher(page);
			ds.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
