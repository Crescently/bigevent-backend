package com.bigeventbackend.interceptor;

import com.bigeventbackend.utils.JwtUtil;
import com.bigeventbackend.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
// 全局登录拦截器
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        // 验证token是否有效，如果无效则返回错误信息
        try {
            // 从redis中获取token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if (redisToken == null){
                throw new RuntimeException("token无效");
            }

            // 解析token
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 把claims存入THREAD_LOCAL中，方便后续获取
            ThreadLocalUtil.set(claims);


            // 放行
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            // 不放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清空threadLocal中的数据 防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
