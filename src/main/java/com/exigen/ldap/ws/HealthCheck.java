package com.exigen.ldap.ws;

import com.exigen.ldap.type.GenericResponse;
import com.exigen.ldap.type.GenericResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
public class HealthCheck {

    @RequestMapping("/ldapproxy/h")
    public GenericResponse heartBeat() {
        GenericResponse response = new GenericResponse();
        final Date now = new Date();
        response.setEntity(
            new GenericResponseEntity(0, "Server time: " + String.valueOf(now), "HEALTH_CHECK"));
        return response;
    }
}
