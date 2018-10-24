package rrs.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rrs.handler.Handler;
import rrs.handler.ResourceEditHandler;

/**
 * Servlet implementation class ResourceEditServlet
 */
@WebServlet("/resourceedit")
public class ResourceEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Handler handler = new ResourceEditHandler();

        String view = handler.handleService(request);

        if (request.getAttributeNames().hasMoreElements()) {
            RequestDispatcher rd = request.getRequestDispatcher(view);
            rd.forward(request, response);
        } else {
            response.sendRedirect(view);
        }
	}

}
