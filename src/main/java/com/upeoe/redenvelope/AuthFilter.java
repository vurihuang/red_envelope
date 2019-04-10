package com.upeoe.redenvelope;

import com.upeoe.redenvelope.utils.JwtKit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author upeoe
 * @create 2019/4/9 15:21
 */
public class AuthFilter extends BasicAuthenticationFilter {

    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

    private static final String AUTHENTICATION = "Authentication";
    private static final String BEARER = "Bearer";

    public AuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String token = request.getHeader(AUTHENTICATION);
        if (StringUtils.isNotBlank(token)) {
            LOG.trace("do filter internal base64:{}, req.url:{}", token, request.getRequestURL());
        }

        final String header = headerMap(request).toString();
        LOG.trace("request header:{}", header);

        if (StringUtils.isNotBlank(token) && token.contains(BEARER)) {
            try {
                final String userId = JwtKit.decode(token.replace(BEARER + " ", "")).getSubject();
                if (StringUtils.isNotBlank(userId)) {
                    final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    LOG.debug("Auth user_id is empty.");
                }
            } catch (Throwable ex) {
                LOG.warn("Token is incorrect.");
            }
        } else {
            LOG.trace("Skip auth header.");
        }
        chain.doFilter(request, response);
    }

    private Map<String, String> headerMap(HttpServletRequest request) {
        final Enumeration<String> heads = request.getHeaderNames();
        final Map<String, String> map = new HashMap<>();

        while (heads.hasMoreElements()) {
            final String next = heads.nextElement();
            map.put(next, request.getHeader(next));
        }
        return map;
    }

}
