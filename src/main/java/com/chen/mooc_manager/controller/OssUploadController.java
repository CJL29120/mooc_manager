package com.chen.mooc_manager.controller;

import com.chen.mooc_manager.util.OssUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@Controller
@CrossOrigin
public class OssUploadController {

    @Autowired OssUploadUtils ossUploadUtils;

    @GetMapping("upload/getPolicyAndCallback")
    @ResponseBody
    public void getPolicyAndCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String res = ossUploadUtils.getPolicyAndCallback();

        String callbackFunName = request.getParameter("callback");
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
            response.getWriter().println(res);
        else
            response.getWriter().println(callbackFunName + "( " + res + " )");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setStatus(HttpServletResponse.SC_OK);
        response.flushBuffer();
    }

    @PostMapping("/upload/uploadCallback")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder builder = new StringBuilder();
        request.getParameterMap().forEach((key, value)-> {
            for (int i = 0; i < value.length; i++) {
                builder.append(key+"="+URLEncoder.encode(value[i])+"&");
            }
        });
        String callbackStr = builder.toString().substring(0,builder.toString().length()-1);
        log.info(callbackStr);

/*
        String ossCallbackBody = OssUploadUtils.getPostBody(request.getInputStream(), Integer.parseInt(request.getHeader("content-length")));
        log.info(String.valueOf(ossCallbackBody.length()));
*/

        boolean ret = ossUploadUtils.verifyOSSCallbackRequest(request, /*ossCallbackBody*/callbackStr);
        log.info("verify result : " + ret);
        if (ret) {
            response(request, response, "{\"Status\":\"OK\"}", HttpServletResponse.SC_OK);
        } else {
            response(request, response, "{\"Status\":\"verify not ok\"}", HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    private void response(HttpServletRequest request, HttpServletResponse response, String results, int status)
            throws IOException {
        String callbackFunName = request.getParameter("callback");
        response.addHeader("Content-Length", String.valueOf(results.length()));
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
            response.getWriter().println(results);
        else
            response.getWriter().println(callbackFunName + "( " + results + " )");
        response.setStatus(status);
        response.flushBuffer();
    }
}
