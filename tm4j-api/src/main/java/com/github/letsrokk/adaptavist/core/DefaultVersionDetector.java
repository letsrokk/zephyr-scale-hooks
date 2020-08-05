package com.github.letsrokk.adaptavist.core;

public class DefaultVersionDetector implements VersionDetector {
    @Override
    public synchronized String getVersion() {
        return "Unscheduled";
    }
}
