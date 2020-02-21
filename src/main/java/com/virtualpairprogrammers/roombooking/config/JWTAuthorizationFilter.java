package com.virtualpairprogrammers.roombooking.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.virtualpairprogrammers.roombooking.services.JwtService;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Autowired
	JwtService jwtService;
	
	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String authorizationHeader = request.getHeader("Authorization");
		
		// if jwt token not in authorization
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;			
		}
		
		// if jwtService not initialized
		if(jwtService == null) {
			ServletContext servletContext = request.getServletContext();
			WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			jwtService = wac.getBean(JwtService.class);
		}
		
		UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationHeader);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);		
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(String header) {
		String jwtToken = header.substring(7);
		
		String payload = jwtService.validateToken(jwtToken);
		JsonParser parser = (JsonParser) JsonParserFactory.getJsonParser();
		
		Map<String, Object> payloadMap = parser.parseMap(payload);

		System.out.println(payloadMap);
		
		String user = payloadMap.get("user").toString();
		List<String> roles = (List<String>) payloadMap.get("roles");
	
		System.out.println(roles);
		System.out.println(user);
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		roles.forEach((role) ->  {
			authorities.add(new GrantedAuthority() {
				
				@Override
				public String getAuthority() {
					// TODO Auto-generated method stub
					return "ROLE_" + role;
				}
			});
		});
				
		return new UsernamePasswordAuthenticationToken(user, null, authorities);
	}
	
}
