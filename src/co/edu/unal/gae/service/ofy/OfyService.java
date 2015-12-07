package co.edu.unal.gae.service.ofy;

import static com.googlecode.objectify.ObjectifyService.factory;


import co.edu.unal.gae.server.impl.ofy.OfyBook;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {

	static {
		
		factory().register( OfyBook.class );
	}
	
	public static Objectify ofy() {
		
        return ObjectifyService.ofy();
    }
}
