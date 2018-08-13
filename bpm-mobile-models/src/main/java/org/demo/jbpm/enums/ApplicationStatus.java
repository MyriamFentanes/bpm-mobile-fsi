package org.demo.jbpm.enums;

public enum ApplicationStatus {

	COMPLETED("COMPLETED"), IN_PROGRESS("IN_PROGRESS");

	private final String label;

	private ApplicationStatus(final String label) {
		this.label = label;
	}

	public String getStatus() {
		return label;
	}

	public static ApplicationStatus fromString(String text) {
		if (text != null) {
			for (ApplicationStatus c : ApplicationStatus.values()) {
				if (text.equalsIgnoreCase(c.label)) {
					return c;
				}
			}
		}
		return null;
	}

}
