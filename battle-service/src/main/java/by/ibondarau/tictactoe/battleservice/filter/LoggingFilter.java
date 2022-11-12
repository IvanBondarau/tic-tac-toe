package by.ibondarau.tictactoe.battleservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LoggingFilter implements Filter  {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) servletRequest).getRequestURL().toString();
        String body = servletRequest.getReader().lines().collect(Collectors.joining());
        String params = ((HttpServletRequest) servletRequest).getQueryString();
        String method = ((HttpServletRequest) servletRequest).getMethod();
        log.debug("""
            New request
            url:    {}
            method: {}
            params: {}
            body:   {}
            """, uri, method, params, body);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
