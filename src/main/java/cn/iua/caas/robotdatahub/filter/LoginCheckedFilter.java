package cn.iua.caas.robotdatahub.filter;

import cn.iua.caas.robotdatahub.common.Result;
import cn.iua.caas.robotdatahub.Utils.JwtUtils;
import cn.iua.caas.robotdatahub.context.UserContext;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import java.io.IOException;

import static org.springframework.integration.IntegrationPatternType.chain;


/*
拦截器
 */
@WebFilter(urlPatterns = "/*")
public class LoginCheckedFilter implements Filter {

    //拦截方法，只要资源被拦截到，就会触发此方法
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1 获取请求路径

        String url = request.getRequestURL().toString();

        if (url.contains("page")) {
            filterChain.doFilter(request, response);
            return;
        }

        //2 判断请求的资源路径


        if(url.contains("login")){
            //登陆功能，放行
            filterChain.doFilter(request, response);
            return;
        }

        //3 获取请求头token

        String jwt = request.getHeader("token");

        System.out.println(jwt);

        if(!StringUtils.hasText(jwt)){
            //返回登陆页面
            System.out.println("jwt不存在："+jwt);
            Result NotLogin = Result.error("NOT_LOGIN");
            //给客户端响应数据
            String jsonString = JSONObject.toJSONString(NotLogin);
            //给前段返回约定好的json格式的设置
            response.getWriter().write(jsonString);
            return;
        }

        //4 解析token并且放行

        Claims claims = null;

        try{
            claims = JwtUtils.parseToken(jwt);
            UserContext.setUser(claims.get("username",String.class));
        }catch (Exception e){
            //返回登陆页面
            Result tokenError = Result.error("EXPIRED-OR-WRONG-TOKEN");
            //给客户端响应数据
            String jsonString = JSONObject.toJSONString(tokenError);
            //给前段返回约定好的json格式的设置
            response.getWriter().write(jsonString);
            return; // 防止继续执行 filterChain.doFilter()
        }

        try{
            filterChain.doFilter(request, response);
        }catch (Exception e){
            Result urlError = Result.error("internal server error");
            //给客户端响应数据
            String jsonString = JSONObject.toJSONString(urlError);
            //给前段返回约定好的json格式的设置
            response.getWriter().write(jsonString);
            e.printStackTrace();
        }finally {
            UserContext.removeUser();
        }
    }

}
