package com.upeoe.redenvelope.rest;

import com.upeoe.redenvelope.service.UserService;
import com.upeoe.redenvelope.utils.ResultHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author upeoe
 * @create 2019/4/11 02:36
 */
@Api(value = "User")
@RestController
@RequestMapping("/api/v1/user")
public class UserResource {

    @Autowired
    UserService userService;

    @ApiOperation(value = "Get user id.", authorizations = {
            @Authorization("Bearer")})
    @RequestMapping(value = "my", method = RequestMethod.GET)
    public ResultHolder getUser(Principal p) {
        try {
            return new ResultHolder(p.getName());
        } catch (Exception e) {
            return new ResultHolder(ResultHolder.FAILED, e.getMessage());
        }
    }

    @ApiOperation(value = "Get user's send red envelopes.", authorizations = {
            @Authorization("Bearer")})
    @GetMapping("send")
    public ResultHolder getUserSendRedEnvelopes(Principal p) {
        try {
            return new ResultHolder(userService.findSendRedEnvelopesByUserId(p.getName()));
        } catch (Exception e) {
            return new ResultHolder(ResultHolder.FAILED, e.getMessage());
        }
    }

    @ApiOperation(value = "Get user's fetched red envelopes.", authorizations = {
            @Authorization("Bearer")})
    @GetMapping("fetched")
    public ResultHolder getUserFetchedRedEnvelopes(Principal p) {
        try {
            return new ResultHolder(userService.findFetchedRedEnvelopesByUserId(p.getName()));
        } catch (Exception e) {
            return new ResultHolder(ResultHolder.FAILED, e.getMessage());
        }
    }

    @ApiOperation(value = "Get user's wallet", authorizations = {
            @Authorization("Bearer")})
    @GetMapping("wallet")
    public ResultHolder getUserWallet(Principal p) {
        try {
            return new ResultHolder(userService.findAmount(p.getName()));
        } catch (Exception e) {
            return new ResultHolder(ResultHolder.FAILED, e.getMessage());
        }

    }

}
