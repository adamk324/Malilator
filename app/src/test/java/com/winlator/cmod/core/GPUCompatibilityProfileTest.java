package com.winlator.cmod.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GPUCompatibilityProfileTest {
    @Test
    public void identifiesImmortalisG925FromReportedDeviceName() {
        GPUCompatibilityProfile profile = GPUCompatibilityProfile.fromVulkanDeviceName("Immortalis-G925 MC12");

        assertEquals(GPUCompatibilityProfile.Family.IMMORTALIS, profile.family);
        assertTrue(profile.isImmortalisG925());
        assertTrue(profile.getSafeRecommendation().contains("system driver"));
    }

    @Test
    public void recognizesG925WhenDriverUsesMaliDeviceLabel() {
        GPUCompatibilityProfile profile = GPUCompatibilityProfile.fromVulkanDeviceName("Mali-G925 MC12");

        assertEquals(GPUCompatibilityProfile.Family.IMMORTALIS, profile.family);
        assertTrue(profile.isImmortalisG925());
    }

    @Test
    public void keepsGenericMaliDistinctFromImmortalis() {
        GPUCompatibilityProfile profile = GPUCompatibilityProfile.fromVulkanDeviceName("Mali-G720 MC12");

        assertEquals(GPUCompatibilityProfile.Family.MALI, profile.family);
        assertFalse(profile.isImmortalisG925());
    }

    @Test
    public void classifiesAdrenoFromReportedDeviceName() {
        GPUCompatibilityProfile profile = GPUCompatibilityProfile.fromVulkanDeviceName("Adreno (TM) 830");

        assertEquals(GPUCompatibilityProfile.Family.ADRENO, profile.family);
    }

    @Test
    public void usesSafeUnknownFallbackForUnavailableRenderer() {
        GPUCompatibilityProfile profile = GPUCompatibilityProfile.fromVulkanDeviceName("Unknown");

        assertEquals(GPUCompatibilityProfile.Family.UNKNOWN, profile.family);
        assertEquals(GPUCompatibilityProfile.Family.UNKNOWN,
                GPUCompatibilityProfile.fromVulkanDeviceName(null).family);
        assertTrue(profile.getSafeRecommendation().contains("system graphics driver"));
    }
}
