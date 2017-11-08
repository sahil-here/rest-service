package com.state_modifier_handlers;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import vendi.entity.UserType;
import vendi.exception.VendiException;
import vendi.rest.dao.IShipmentDAO;
import vendi.rest.dao.IStatusDAO;
import vendi.rest.dao.entity.Shipment;
import vendi.rest.dao.entity.StatusHistory;
import vendi.rest.service.ClientService;
import vendi.statemachine.State;
import vendi.statemachine.StateTransitionData;

public class StateChangeHandler {

	protected static final Logger logger = LoggerFactory.getLogger(StateChangeHandler.class);

	@Inject
	private IShipmentDAO shipmentDAO;
	
	@Inject
	protected IStatusDAO statusDAO;

	@Inject
	protected ClientService spokesService;

	public void modifyState(StateTransitionData stateTransitionData, String finalState) throws VendiException{
		logger.info("In state modifier for" + stateTransitionData + " and final state " + finalState);
		Shipment shipment = shipmentDAO.getShipmentByShipmentId((Long) stateTransitionData.get("shipmentId"));
		if (null != shipment) {
			shipment.setStatus(State.valueOf(finalState));
			shipmentDAO.createOrUpdate(shipment);
			StatusHistory status = new StatusHistory();
			status.setShipment(shipment);
			status.setUpdatedBy(UserType.valueOf(stateTransitionData.get("userType").toString()));
			status.setStatus(State.valueOf(finalState));
			status.setMessage((String) stateTransitionData.get("statusMessage"));
			status.setStatusTime((Timestamp) stateTransitionData.get("statusTime"));
			status.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
			status.setGeoLat((Float) stateTransitionData.get("geoLat"));
			status.setGeoLong((Float) stateTransitionData.get("geoLong"));
			statusDAO.createOrUpdate(status);
		}
	}
}
