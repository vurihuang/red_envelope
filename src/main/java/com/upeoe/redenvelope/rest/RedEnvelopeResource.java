package com.upeoe.redenvelope.rest;

import com.upeoe.redenvelope.entity.RedEnvelope;
import com.upeoe.redenvelope.exception.BusinessException;
import com.upeoe.redenvelope.repository.RedEnvelopeItemRepo;
import com.upeoe.redenvelope.repository.RedEnvelopeRepo;
import com.upeoe.redenvelope.service.RedEnvelopeService;
import com.upeoe.redenvelope.utils.ResultHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author upeoe
 * @create 2019/4/10 02:02
 */
@Api(value = "Red Envelope")
@RestController
@RequestMapping("/api/v1/red_envelope")
public class RedEnvelopeResource {

    @Autowired
    private RedEnvelopeService redEnvelopeService;

    @ApiOperation(value = "Send red envelope.", authorizations = {
            @Authorization("Bearer")})
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public ResultHolder sendRedEnvelope(Principal p, @RequestBody RedEnvelope params) {
        try {
            params.setUserId(p.getName());
            return new ResultHolder(redEnvelopeService.sendRedEnvelope(params));
        } catch (BusinessException e) {
            return new ResultHolder(ResultHolder.FAILED, e.getMessage());
        }
    }

    @ApiOperation(value = "Fetch red envelope.", authorizations = {
            @Authorization("Bearer")})
    @PostMapping(value = "fetch")
    public ResultHolder fetchRedEnvelope(Principal p, @RequestBody RedEnvelope params) {
        try {
            return new ResultHolder(redEnvelopeService.fetchRedEnvelope(p.getName(), params.getSign()));
        } catch (BusinessException e) {
            return new ResultHolder(ResultHolder.FAILED, e.getMessage());
        }
    }

}
