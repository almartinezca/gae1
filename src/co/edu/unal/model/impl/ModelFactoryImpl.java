package co.edu.unal.model.impl;

import co.edu.unal.gae.server.dao.ofy.IBook;
import co.edu.unal.gae.server.dao.ofy.IModelFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ModelFactoryImpl implements IModelFactory {

	@Inject
	public ModelFactoryImpl( final Provider<IBook> book ) {

		super();
		this.bookProvider = book;
	}
	
	@Override
	public IBook createBook( Long id, String titulo ,String  autor,String editorial, Long copias, String estado ) {
		
		IBook book = bookProvider.get();
		book.setId( id );
		book.setTitulo( titulo );
		book.setAutor( autor );
		book.setEditorial( editorial );
		
		return book;
	}

	private final Provider<IBook> bookProvider;
}
