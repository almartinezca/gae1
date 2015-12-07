package co.edu.unal.gae.server.dao.ofy;

public interface IBook {

	Long getId();
	void setId( Long id );
	
	String getTitulo();
	void setTitulo( String titulo );

	String getAutor();
	void setAutor( String autor );
	
	String getEditorial();
	String setEditorial( String editorial );
	
	Long getCopias();
	Long setCopias( Long copias );
}
