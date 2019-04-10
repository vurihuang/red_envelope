package com.upeoe.redenvelope.rest;

import com.upeoe.redenvelope.utils.JwtKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author upeoe
 * @create 2019/4/11 02:27
 */
@Api(value = "Token")
@RestController
@RequestMapping("/api/v1/token")
public class TokenResource {

    @ApiOperation(value = "Get authentication token.")
    @GetMapping("{userId}")
    public String getToken(@PathVariable String userId) {
        if (StringUtils.isNotBlank(userId)) {
            return JwtKit.encode(userId);
        }
        return "";
    }

}
