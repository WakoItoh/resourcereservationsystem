package rrs.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import rrs.handler.Handler;
import rrs.handler.UserFilterHandler;

/**
 * Servlet Filter implementation class UserFilter
 */
// TODO リリース時に適用
@WebFilter({"/reservableresouce",
        "/reservableresoucesearch",
        "/reservableresoucesearchnow",
        "/reservation",
        "/reservationcancel",
        "/reservationcancelconfirm",
        "/reservationcopy",
        "/reservationdetail",
        "/reservationedit",
        "/reservationeditor",
        "/reservationnow",
        "/reservationregister",
        "/reservationregisternow",
        "/reservationregistrator",
        "/reservationsearch",
        "/resource",
        "/resourcedetail" })
public class UserFilter implements Filter {

    /**
     * Default constructor.
     */
    public UserFilter() {
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

        Handler handler = new UserFilterHandler();

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
