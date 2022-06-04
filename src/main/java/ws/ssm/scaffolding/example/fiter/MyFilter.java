package ws.ssm.scaffolding.example.fiter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author WindShadow
 * @date 2021-2-25
 */
@Slf4j
@Component
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("MyFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        log.info("URL: {}", req.getRequestURL());
        log.info("URI: {}", req.getRequestURI());
        log.info("ServletPath: {}", req.getServletPath());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
