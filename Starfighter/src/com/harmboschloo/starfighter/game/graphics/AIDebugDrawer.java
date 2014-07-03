package com.harmboschloo.starfighter.game.graphics;

import com.google.gwt.canvas.dom.client.Context2d;
import com.harmboschloo.boxy.graphics.color.Color;
import com.harmboschloo.boxy.graphics.viewport.HasViewportUpdate;
import com.harmboschloo.boxy.graphics.viewport.Viewport;
import com.harmboschloo.starfighter.game.ai.DroneDebugSensors;
import com.harmboschloo.starfighter.game.ai.DroneSensors;
import com.harmboschloo.starfighter.game.ai.EvadeAI;
import com.harmboschloo.starfighter.game.object.Ship;

public class AIDebugDrawer implements HasViewportUpdate {
	private final EvadeAI ai;
	private final DroneSensors sensors;
	private final DroneDebugSensors debugSensors;

	public AIDebugDrawer(final EvadeAI ai) {
		this.ai = ai;
		sensors = ai.getSensors();
		debugSensors = (DroneDebugSensors) (sensors instanceof DroneDebugSensors ? sensors
				: null);
	}

	private void drawSensors(final Context2d context) {
		final Ship ship = ai.getShip();
		final float shipRadius = ship.getHalfSize().getLength();

		context.setStrokeStyle("#0000ff");

		context.beginPath();
		context.moveTo(sensors.getRange(), 0);
		context.arc(0, 0, sensors.getRange(), 0, 2 * Math.PI);
		context.stroke();

		context.beginPath();
		context.arc(0, 0, shipRadius + sensors.getSafetyMargin(), 0,
				2 * Math.PI);
		context.stroke();

		if (debugSensors != null) {
			for (final DroneSensors.ObjectData data : debugSensors
					.getObjectData()) {
				context.setStrokeStyle("#00ffff");
				context.beginPath();
				context.arc(data.center.x, data.center.y, data.radius, 0,
						2 * Math.PI);
				context.stroke();

				context.setStrokeStyle("#00aaaa");
				context.beginPath();
				context.arc(data.center.x + data.velocity.x
						* data.timeToContact, data.center.y + data.velocity.y
						* data.timeToContact, data.radius, 0, 2 * Math.PI);
				context.stroke();

				context.beginPath();
				context.moveTo(data.center.x, data.center.y);
				context.lineTo(data.center.x + data.velocity.x, data.center.y
						+ data.velocity.y);
				context.stroke();

				if (data.contactCenter != null) {
					context.setStrokeStyle("#ff0000");
					context.beginPath();
					context.moveTo(0, 0);
					context.lineTo(data.contactCenter.x, data.contactCenter.y);
					context.stroke();
				}

			}
		}

		context.setGlobalAlpha(0.5);
		final float[] constaints = sensors.getConstraints();
		final Color color0 = new Color(0, 255, 0);
		final Color color1 = new Color(255, 0, 0);
		for (int i = 0; i < constaints.length; ++i) {
			context.setFillStyle(Color.interpolate(color0, color1,
					constaints[i]).makeCssColor());
			context.beginPath();
			final float angle = 2 * ((float) Math.PI) * i / constaints.length;
			context.arc(2 * shipRadius * Math.cos(angle),
					2 * shipRadius * Math.sin(angle), shipRadius / 2, 0,
					2 * Math.PI);
			context.fill();
		}

		context.setFillStyle((sensors.willCollide() ? color1 : color0)
				.makeCssColor());
		context.beginPath();
		context.arc(0, 0, shipRadius / 2, 0, 2 * Math.PI);
		context.fill();
	}

	@Override
	public void update(final Viewport viewport) {
		final Context2d context = viewport.getContext();
		context.save();

		final Ship ship = ai.getShip();
		context.translate(ship.getCenter().x, ship.getCenter().y);
		context.rotate(ship.getRotation().getAngle());

		// sensors
		drawSensors(context);

		context.restore();
	}
}
