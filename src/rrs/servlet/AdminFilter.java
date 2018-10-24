package rrs.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import rrs.handler.AdminFilterHandler;
import rrs.handler.Handler;

/**
 * Servlet Filter implementation class AdminFilter
 */
// TODO リリース時に適用
//@WebFilter({ "/resourcedelete", "/resourcedeleteconfirm", "/resourceedit", "/resourceeditor", "/resourceregister", "/resourceregistrator" })
public class AdminFilter implements Filter {

    /**
     * Default constructor.
     */
    public AdminFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");

        Handler handler = new AdminFilterHandler();

        String view = handler.handleService((HttpServletRequest) request);

        if (view != null) {
            RequestDispatcher rd = request.getRequestDispatcher(view);
            rd.forward(request, response);
        }

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
