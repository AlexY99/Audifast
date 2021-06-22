package utilidades;

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

@WebFilter(filterName = "FiltroAcceso", urlPatterns = {"/*"})
public class FiltroAcceso implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String correoAuditor = "";
        boolean esInvitado = false;
        if(req.getSession().getAttribute("Invitado") != null){
            esInvitado = (boolean) req.getSession().getAttribute("Invitado");
        }
        if(req.getSession().getAttribute("CorreoAuditor") != null){
            correoAuditor = req.getSession().getAttribute("CorreoAuditor").toString();
        }
        String url = req.getRequestURI();
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        res.setDateHeader("Expires", 0);
        if(correoAuditor.isEmpty() || esInvitado ==  true){ // Si no ha inicuiado sesion o es invitado
            // Si no es el index, el formulario de registro o un recurso js o css redirecciona al index
            if(!url.endsWith("/") && !url.contains("index") && !url.contains("AudiFastWS") && !url.contains("registro") && !url.endsWith(".js") && !url.endsWith(".css") && !url.endsWith(".jpg") && !url.endsWith(".png") && !url.endsWith(".jar") && !url.endsWith(".war")){
                if(url.contains("AuditorServlet")){
                    String queryString = req.getQueryString();
                    if(queryString.contains("Login") || queryString.contains("Almacenar"))
                        chain.doFilter(request, response);
                    else{
                        System.out.println("Denegado<-"+url);
                        res.sendRedirect(req.getServletContext().getContextPath()+"/");
                    }
                }else if(url.contains("RetroalimentacionServlet")){
                    String queryString = req.getQueryString();
                    if(queryString.contains("Retroalimentacion"))
                        chain.doFilter(request, response);
                    else{
                        System.out.println("Denegado<-"+url);
                        res.sendRedirect(req.getServletContext().getContextPath()+"/");
                    }
                }else{
                    System.out.println("Denegado<-"+url);
                    res.sendRedirect(req.getServletContext().getContextPath()+"/");
                }               
            }else chain.doFilter(request, response);  
        }else{
            if(url.endsWith("/") || url.contains("index")){
                res.sendRedirect("/AudiFast/AuditorServlet?accion=Inicio");
            }else chain.doFilter(request, response);
        }     
    }

    @Override
    public void destroy() {    }
    
}
