package co.edu.unal.gae.server.dao.ofy;

import static co.edu.unal.gae.service.ofy.OfyService.ofy;

import java.util.LinkedList;
import java.util.List;

import co.edu.unal.gae.server.impl.ofy.OfyBook;

import com.google.inject.Singleton;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

@Singleton
public class OfyBookDAO implements IBookDAO {

	@Override
	public void save( final IBook book ) {

		ofy().save().entity( book ).now();
	}



	@Override
	public OfyBook fetchById( final long id ) {

		Key<OfyBook> key = Key.create( OfyBook.class, id );

		return ofy().load().key( key ).now();
	}

	@Override
	public List<IBook> fetchAllBooks() {

		Query<OfyBook> q = ofy().load().type( OfyBook.class );

		return new LinkedList<IBook>( q.list() );
	}

	@Override
	public boolean isEmpty() {

		Query<OfyBook> q = ofy().load().type( OfyBook.class );

		return q.limit( 1 ).list().isEmpty();
	}
}
