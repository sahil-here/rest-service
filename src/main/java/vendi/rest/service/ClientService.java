package vendi.rest.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import vendi.entity.StatusHistoryDetails;
import vendi.exception.VendiException;
import vendi.response.StatusHistoryResponse;
import vendi.rest.VendiConfiguration;
import vendi.rest.dao.IShipmentDAO;
import vendi.rest.dao.entity.Shipment;
import vendi.statemachine.State;
import vendi.statemachine.StateTransitionData;

public class ClientService extends RestClient {
	
	@Inject
	private IShipmentDAO shipmentDAO;

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

	private ObjectMapper objectMapper;

	public static final String UPDATE_STATUS = "Vendi_App/vendi_notify";

	@Inject
	public ClientService(HttpClient httpClient, VendiConfiguration configuration, ObjectMapper objectMapper) {
		super(httpClient, configuration.getUrlConfiguration().getSpokesUrl());
		this.objectMapper = objectMapper;
	}

	public void publishStatusUpdate(StateTransitionData stateTransitionData) throws VendiException {

		String api = UPDATE_STATUS;

		Shipment shipment = shipmentDAO.getShipmentByShipmentId((Long) stateTransitionData.get("shipmentId"));
		State state = (shipment!=null)?shipment.getStatus():null;

		if (shipment != null && state != null) {
			StatusHistoryDetails statusHistoryDetail = new StatusHistoryDetails();
			statusHistoryDetail.setReferenceId(shipment.getExternalReferenceId());
			statusHistoryDetail.setShipmentId(shipment.getShipmentId());
			statusHistoryDetail.setTrackingId(shipment.getTrackingId());
			statusHistoryDetail.setStatus(state.toString());
			statusHistoryDetail.setStatusMessage((String) stateTransitionData.get("statusMessage"));
			statusHistoryDetail.setStatusTime((Timestamp) stateTransitionData.get("statusTime"));
			statusHistoryDetail.setStatusGeoLat((Float) stateTransitionData.get("statusGeoLat"));
			statusHistoryDetail.setStatusGeoLong((Float) stateTransitionData.get("statusGeoLong"));

			List<StatusHistoryDetails> statusHistoryDetails = new ArrayList<StatusHistoryDetails>();
			statusHistoryDetails.add(statusHistoryDetail);
			StatusHistoryResponse statusHistoryResponse = new StatusHistoryResponse();
			statusHistoryResponse.setStatusHistoryDetails(statusHistoryDetails);

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("content-type", "application/json");

			try {
				doPost(api, objectMapper.writeValueAsString(statusHistoryResponse), headers);
			} catch (JsonProcessingException | VendiException e) {
				getLogger().error(
						"Exception occured while publishing message to Client, Exception Cause : " + e.getMessage());
				getLogger().error("Exception occured while publishing message to Client, Exception Stacktrace : "
						+ Arrays.asList(e.getStackTrace()).toString());
			}
		}

	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public String getClientName() {
		return "CLIENT_SERVICE";
	}
}
