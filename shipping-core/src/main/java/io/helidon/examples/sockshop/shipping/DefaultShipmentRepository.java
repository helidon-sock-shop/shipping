package io.helidon.examples.sockshop.shipping;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 */
public class DefaultShipmentRepository implements ShipmentRepository {

    private Map<String, Shipment> shipments = new ConcurrentHashMap<>();

    @Override
    public Collection<Shipment> getAllShipments() {
        return shipments.values();
    }

    @Override
    public Shipment getShipment(String id) {
        return shipments.get(id);
    }

    @Override
    public Shipment addShipment(Shipment shipment) {
        shipments.put(shipment.getId(), shipment);
        return shipment;
    }
}
