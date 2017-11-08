package vendi.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;

import vendi.rest.util.logging.Logged;
import vendi.rotation.RotationHelper;

@Path("rotation")
@Api(value = "rotation")
@Produces(MediaType.APPLICATION_JSON)
public class RotationResource {

	@Inject
	RotationHelper rotationHelper;

	public static final Logger LOGGER = LoggerFactory.getLogger(RotationResource.class);
	
	@Logged
	@Timed
	@Path("/OOR")
	@GET
	public String makeOOR() {
		LOGGER.info("Vendi Service make OOR");
		Boolean status = rotationHelper.getRotationStatus();
		if( status ) {
			RotationHelper.setRotationStatus(false);
			LOGGER.info("Machine is out of rotation now");
			return "Machine is out of rotation now";
		} else {
			LOGGER.info("Machine is already out of rotation");
			return "Machine is already out of rotation";
		}
	}
	
	@GET
	@Logged
	@Timed
	@Path("/BIR")
	public String makeBIR() {
		LOGGER.info("Vendi Service make BIR");
		Boolean status = rotationHelper.getRotationStatus();
		if( status ) {
			LOGGER.info("Machine is already in rotation");
			return "Machine is already in rotation";
		} else {
			RotationHelper.setRotationStatus(true);
			LOGGER.info("Machine is in rotation now");
			return "Machine is in rotation now";
		}
	}

}
