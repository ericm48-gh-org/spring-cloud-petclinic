package com.eric.common.petclinic.system;

import java.util.HashMap;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "heapdump") // Accessible at /actuator/heapdump
public class CustomActuatorHeapDumpEndpoint {

    @ReadOperation
    public ResponseEntity<HashMap<String, Object>> heapdump() {        

        //
        // Force a 404 here...
        //
        ResponseEntity<HashMap<String, Object>>returnValue = ResponseEntity.notFound().build();

        return(  returnValue );

    }
}