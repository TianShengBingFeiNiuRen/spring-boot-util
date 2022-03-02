package com.andon.springbootutil.controller;

import com.andon.springbootutil.domain.ResponseStandard;
import com.andon.springbootutil.filter.SecurityFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andon
 * 2021/11/10
 */
@Slf4j
@RestController
public class FilterController {

    /**
     * 自定义403接口处理响应
     */
    @RequestMapping(value = "/403")
    public ResponseStandard<String> filter() {
        ResponseStandard<String> response = ResponseStandard.failureResponse(null);
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            int code = (int) request.getAttribute("code");
            String message = String.valueOf(request.getAttribute("message"));
            String uri = String.valueOf(request.getAttribute("uri"));
            response.setCode(code);
            response.setMessage(message);
            String location = SecurityFilter.queryLocation(request);
            log.warn("X-Real-IP:{} location:{} remoteHost:{} method:{} uri:{}", request.getHeader("X-Real-IP"), location, request.getRemoteHost(), request.getMethod(), uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
