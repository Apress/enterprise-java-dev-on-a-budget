package com.ejdoab.tcms.admin.remote;

import java.util.Hashtable;

import javax.naming.Context;

import com.ejdoab.tcms.services.locator.ServiceLocator;
import com.ejdoab.tcms.services.locator.ServiceLocatorException;

/**
 * @author Brian Sam-Bodden
 */
public class ServiceProxyFactory {
	private String _host;
	private String _port;

	private static ServiceLocator _locator;

	// services
	private static ConferenceServicesProxy _conferenceService;

	// Jboss specific
	private static final String ICF = "org.jnp.interfaces.NamingContextFactory";
	private static final String PKG_PREFIXES =
		"org.jboss.naming:org.jnp.interfaces";

	/**
	 * 
	 */
	public ServiceProxyFactory(String host, String port) {
		_host = host;
		_port = port;
		try {
			getLocator();
		} catch (RemoteServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ServiceLocator getLocator() throws RemoteServerException {
		if (ServiceProxyFactory._locator == null) {
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY, ICF);
			env.put(Context.PROVIDER_URL, _host + ":" + _port);
			env.put(Context.URL_PKG_PREFIXES, PKG_PREFIXES);

			try {
				_locator = new ServiceLocator(env);
			} catch (ServiceLocatorException sle) {
				throw new RemoteServerException(sle);
			}
		}
		return _locator;
	}

	public ConferenceServicesProxy getConferencesServices()
		throws RemoteServerException {
		if (_conferenceService == null) {
			_conferenceService = new ConferenceServicesProxy(_locator);
		}
		return _conferenceService;
	}

}
