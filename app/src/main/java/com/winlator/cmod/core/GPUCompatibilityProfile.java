package com.winlator.cmod.core;

import java.util.Locale;

/** Capability-profile classification based on the reported Vulkan physical-device name. */
public final class GPUCompatibilityProfile {
    public enum Family {
        IMMORTALIS,
        MALI,
        ADRENO,
        OTHER,
        UNKNOWN
    }

    public final Family family;
    public final String deviceName;

    private GPUCompatibilityProfile(Family family, String deviceName) {
        this.family = family;
        this.deviceName = deviceName;
    }

    public static GPUCompatibilityProfile fromVulkanDeviceName(String value) {
        String name = value == null ? "" : value.trim();
        String lower = name.toLowerCase(Locale.ROOT);
        if (name.isEmpty() || lower.equals("unknown")) {
            return new GPUCompatibilityProfile(Family.UNKNOWN, "Unknown");
        }
        if (lower.contains("immortalis") || (lower.contains("mali") && lower.contains("g925"))) {
            return new GPUCompatibilityProfile(Family.IMMORTALIS, name);
        }
        if (lower.contains("mali")) {
            return new GPUCompatibilityProfile(Family.MALI, name);
        }
        if (lower.contains("adreno")) {
            return new GPUCompatibilityProfile(Family.ADRENO, name);
        }
        return new GPUCompatibilityProfile(Family.OTHER, name);
    }

    public boolean isImmortalisG925() {
        String lower = deviceName.toLowerCase(Locale.ROOT);
        return family == Family.IMMORTALIS && lower.contains("g925");
    }

    public String getFamilyLabel() {
        switch (family) {
            case IMMORTALIS:
                return "Immortalis";
            case MALI:
                return "Mali";
            case ADRENO:
                return "Adreno";
            case OTHER:
                return "Other Android GPU";
            default:
                return "Unavailable";
        }
    }

    /** Returns a suggestion only; this class never changes a container configuration. */
    public String getSafeRecommendation() {
        if (isImmortalisG925()) {
            return "Immortalis-G925 detected. Keep the Android system driver as the safe starting point. "
                    + "No Malilator-validated wrapper preset is available yet; choose and override container graphics settings manually.";
        }
        if (family == Family.MALI || family == Family.IMMORTALIS) {
            return "Mali-family GPU detected. Keep the system driver available and compare wrapper and texture modes per game. "
                    + "Automatic backend selection is not enabled until this driver path is validated.";
        }
        if (family == Family.ADRENO) {
            return "Adreno GPU detected. Existing Adreno driver options remain available; keep the selected container driver unless you choose to change it.";
        }
        return "No validated device profile is available. Use the system graphics driver and select installed wrappers manually.";
    }
}
