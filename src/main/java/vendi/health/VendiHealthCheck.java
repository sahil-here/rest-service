package vendi.health;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;

import vendi.rotation.RotationHelper;

public class VendiHealthCheck extends HealthCheck{
	@Inject
	RotationHelper rotationHelper;
	

	@Override
	protected Result check() throws Exception {

		if (!rotationHelper.getRotationStatus()) {
			return Result.unhealthy("Vendi is down");
		}
		return Result.healthy("Vendi is up");
	}
}
