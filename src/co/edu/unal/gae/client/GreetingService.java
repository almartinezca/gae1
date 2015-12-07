package co.edu.unal.gae.client;

import co.edu.unal.gae.shared.LoginInfo;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	// TODO #09: start create login helper methods in service interface	
		String getUserEmail(String token);	

		LoginInfo login(String requestUri);	

		LoginInfo loginDetails(String token);
		// TODO #09:> end	
}
