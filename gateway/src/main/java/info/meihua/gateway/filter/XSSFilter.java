package info.meihua.gateway.filter;

import web.XSSRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 防止XSS攻击
 * @author fedor
 */
public class XSSFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) {
		
	}
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;
		String method = req.getMethod();
		if(method != null ) {
		//if(method != null && !"get".equals(method.toLowerCase())) {
			chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
		} else {
			chain.doFilter(request, response);
		}
    }
}