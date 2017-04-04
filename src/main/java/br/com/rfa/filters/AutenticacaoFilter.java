package br.com.rfa.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.rfa.model.UsuarioModel;

@WebFilter("/sistema/*")
public class AutenticacaoFilter implements Filter {

    public AutenticacaoFilter() {

    }

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// Recupera o objeto de sessão da requisição enviada
		HttpSession httpSession 				= ((HttpServletRequest) request).getSession(); 
		
		// Converte o servlet de requisição em um HttpServletRequest 
		HttpServletRequest httpServletRequest   = (HttpServletRequest) request;
		// Converte o servlet de resposta em um HttpServletResponse
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		
		
		// Verifica se a requisição enviada partil de uma página expecífica, no caso "index.xhtml" 
		if(httpServletRequest.getRequestURI().indexOf("index.xhtml") <= -1){
			UsuarioModel usuarioModel =(UsuarioModel) httpSession.getAttribute("usuarioAutenticado");
			if(usuarioModel == null){
				System.out.println(httpServletRequest.getContextPath());				
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+ "/index.xhtml");
			}
			else{
				chain.doFilter(request, response);
			}
		}		
		else{
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}