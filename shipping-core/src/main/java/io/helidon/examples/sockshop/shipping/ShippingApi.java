package io.helidon.examples.sockshop.shipping;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public interface ShippingApi {
    @GET
    @Path("{orderId}")
    @Produces(APPLICATION_JSON)
    @Operation(description = "Return the Shipment for the specified order")
    Shipment getShipmentByOrderId(@Parameter(description = "Order identifier")
                                  @PathParam("orderId") String orderId);

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Operation(description = "Ship the specified shipping request")
    Shipment ship(@Parameter(description = "Shipping request") ShippingRequest req);
}
