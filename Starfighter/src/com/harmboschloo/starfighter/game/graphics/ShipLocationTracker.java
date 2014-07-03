package com.harmboschloo.starfighter.game.graphics;

import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;
import com.harmboschloo.boxy.graphics.viewport.HasViewportUpdate;
import com.harmboschloo.boxy.graphics.viewport.Viewport;
import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.Limit;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.util.Group;
import com.harmboschloo.starfighter.game.object.Ship;

public class ShipLocationTracker extends Group<Ship> implements
		HasViewportUpdate {
	private final AreaF wrapArea;

	public ShipLocationTracker(final AreaF wrapArea) {
		this.wrapArea = wrapArea;
	}

	public ShipLocationTracker(final List<Ship> ships, final AreaF wrapArea) {
		super(ships);
		this.wrapArea = wrapArea;
	}

	@Override
	public void update(final Viewport viewport) {
		final VectorF vpHalfSize = viewport.getHalfSize();
		final VectorF vpCenter = new VectorF(viewport.getCenter());
		final Context2d context = viewport.getContext();
		context.save();
		context.setLineWidth(2);

		for (final Ship ship : getElements()) {
			if (ship.isEnabled() && !ship.isDestroyed()) {
				final VectorF center = ship.getCenter().minus(vpCenter);
				wrapArea.wrapRelativePosition(center);
				final float shipRadius = ship.getHalfSize().getLength();
				final float divider = Math.max(
						Math.abs(center.x / vpHalfSize.x),
						Math.abs(center.y / vpHalfSize.y));
				final float cutOff = 0.8f;
				if (divider > cutOff) {
					final VectorF position = new VectorF(center);
					if (divider > 1f) {
						position.divideBy(divider);
					}

					float alpha = 1;

					if (divider < 1f) {
						alpha = Limit.clamp((divider - cutOff) / (1 - cutOff),
								0, 1);
					}

					final float fraction = Math.max(
							Math.abs(2 * center.x / wrapArea.getWidth()),
							Math.abs(2 * center.y / wrapArea.getHeight()));
					final float fadeOutFraction = 0.95f;
					if (fraction > fadeOutFraction) {
						alpha = Limit.clamp((fraction - 1)
								/ (fadeOutFraction - 1), 0, 1);
					}

					position.add(vpCenter);

					context.setGlobalAlpha(alpha);
					context.setStrokeStyle(ship.getColor().makeCssColor());
					context.beginPath();
					context.arc(position.x, position.y, shipRadius, 0,
							2 * Math.PI);
					context.stroke();
				}
			}
		}

		context.restore();
	}
}
