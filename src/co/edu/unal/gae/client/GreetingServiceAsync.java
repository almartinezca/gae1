package co.edu.unal.gae.client;

import co.edu.unal.gae.shared.LoginInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	// TODO #10: create login helper methods in service asynchronous interface	
		void getUserEmail(String token, AsyncCallback<String> callback);

		void login(String requestUri, AsyncCallback<LoginInfo> asyncCallback);

		void loginDetails(String token, AsyncCallback<LoginInfo> asyncCallback);
		// TODO #10:> end
}
