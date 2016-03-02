package carl.royal.an.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <p>Http request interceptor for log every access</p>
 * <p>Reference document : <a href="http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/HandlerInterceptor.html">spring</a></p>
 * 
 * @author carl.huang
 * @date Oct 15, 2015 5:25:48 PM
 */
public class HttpRequestLoggerInterceptor extends HandlerInterceptorAdapter {
    //---------------------------------------------------------------
    private static final String NEWLINE = System.getProperty("line.separator");
	private static final Logger logger = LoggerFactory.getLogger(HttpRequestLoggerInterceptor.class);

    //---------------------------------------------------------------
	/**
	 * Called just before the controller.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    StringBuilder str = new StringBuilder();
	    str.append("=============== Received Request ===============").append(NEWLINE)
	       .append("From: ").append(request.getRemoteHost()).append(NEWLINE)
	       .append(request.getMethod()).append(" ").append(request.getRequestURL()).append(NEWLINE);
	    if(!(handler instanceof HandlerMethod)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            logger.debug(str.toString());
            return false;
        }
	    for(Enumeration<String> headers = request.getHeaderNames(); headers.hasMoreElements(); str.append(headers.hasMoreElements()? NEWLINE : "")) {
	        String name = headers.nextElement();
	        str.append(name).append(":").append(request.getHeader(name));
	    }
	    logger.debug(str.toString());
		request.setAttribute("startTime", System.currentTimeMillis());
		//if returned false, we need to make sure 'response' is sent
		return true;
	}

	/**
	 * Called immediately after the controller
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.debug("Response model:{}", modelAndView);
		//we can add attributes in the modelAndView and use that in the view page
	}

	/**
	 * Called just before sending response to view
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long startTime = (Long) request.getAttribute("startTime");
		logger.debug("Time Taken={}", System.currentTimeMillis() - startTime);
	}

}
