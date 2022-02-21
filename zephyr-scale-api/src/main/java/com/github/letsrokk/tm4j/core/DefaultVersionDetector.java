package com.github.letsrokk.tm4j.core;

public class DefaultVersionDetector implements VersionDetector {
    @Override
    public synchronized String getVersion() {
        return "Unscheduled";
    }
}
