package org.jbpm.demo.test.utils;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public final class KieServicesClientHelper {

	private static final String URL = System.getProperty("kie.server.url", "http://localhost:8080/kie-server/services/rest/server");
	private static final String USERNAME = System.getProperty("kie.server.user", "bpmsAdmin");
	private static final String PASSWORD = System.getProperty("kie.server.password", "Pa$$w0rd");

	private KieServicesClientHelper() {
	}

	public static final KieServicesClientHelper getInstance() {
		return new KieServicesClientHelper();
	}

	public KieServicesClient getKieServicesClient(Class<?>... remoteClasses) {
		return getKieServicesClient(USERNAME, PASSWORD, remoteClasses);
	}

	/**
	 * Individual User Services currently all using the same password.
	 * 
	 * @param username
	 * @param remoteClasses
	 * @return
	 */
	public KieServicesClient getKieServicesClientForUser(String username, Class<?>... remoteClasses) {
		return getKieServicesClient(username, PASSWORD, remoteClasses);
	}

	public KieServicesClient getKieServicesClient(String username, String password, Class<?>... remoteClasses) {
		KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(URL, username, password);
		config.addExtraClasses(Arrays.asList(remoteClasses).stream().collect(Collectors.toSet()));
		config.setMarshallingFormat(MarshallingFormat.JSON);
		config.setTimeout(10000l);
		return KieServicesFactory.newKieServicesClient(config);
	}

}
