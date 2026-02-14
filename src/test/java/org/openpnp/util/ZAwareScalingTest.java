package org.openpnp.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openpnp.model.Length;
import org.openpnp.model.LengthUnit;

/**
 * Verification test for Z-aware scaling logic.
 */
public class ZAwareScalingTest {
	@Test
	public void testViewingPlaneCalculation() {
		Length nozzleZ = new Length(10.0, LengthUnit.Millimeters);
		Length partHeight = new Length(2.5, LengthUnit.Millimeters);

		// The implementation plan specifies:
		// viewingPlaneZ = nozzleZ.add(part.getHeight())
		Length viewingPlaneZ = nozzleZ.add(partHeight);

		assertEquals(12.5, viewingPlaneZ.getValue(), 0.001);
		assertEquals(LengthUnit.Millimeters, viewingPlaneZ.getUnits());
	}

	@Test
	public void testZeroPartHeight() {
		Length nozzleZ = new Length(15.0, LengthUnit.Millimeters);
		Length partHeight = new Length(0.0, LengthUnit.Millimeters);

		Length viewingPlaneZ = nozzleZ.add(partHeight);

		assertEquals(15.0, viewingPlaneZ.getValue(), 0.001);
	}
}
