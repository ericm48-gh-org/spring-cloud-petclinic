/**
 * Custom Actuator Endpoint to override the default /actuator/heapdump endpoint
 * to always return a 404 Not Found response.
 *
 * This is useful when you want to disable heap dump functionality without
 * removing the endpoint entirely.
 *
 * @author Eric Manley
 */
package com.eric.common.petclinic.system;

import java.util.HashMap;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

//@Component
//@Endpoint(id = "heapdump") // Accessible at /actuator/heapdump
//@EndpointExtension (endpoint = HeapDumpWebEndpoint.class, filter = null)

//@EndpointWebExtension(endpoint = HeapDumpWebEndpoint.class)

public class HeapDumpWebEndpointExtension {

    // @ReadOperation
    // public ResponseEntity<HashMap<String, Object>> heapdump() {        

    //     //
    //     // Force a 404 here...
    //     //
    //     ResponseEntity<HashMap<String, Object>>returnValue = ResponseEntity.notFound().build();

    //     return(  returnValue );

    // }
}