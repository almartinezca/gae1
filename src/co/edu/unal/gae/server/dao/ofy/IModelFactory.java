package co.edu.unal.gae.server.dao.ofy;

public interface IModelFactory {
	
	IBook createBook( Long id, String titulo ,String  autor,String editorial, Long copias, String estado );


}
