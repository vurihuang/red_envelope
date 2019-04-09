package com.upeoe.redenvelope.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author upeoe
 * @create 2019/4/9 17:55
 */
@Api(value = "Index")
@RestController
public class IndexResource {

    @ApiOperation(value = "Index")
    @RequestMapping("/")
    public String index() {
        return "Ahoy!";
    }

}
