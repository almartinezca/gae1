package co.edu.unal.gae.server.dao.ofy;
import java.util.List;



public interface IBookDAO {

	void save( IBook book );
	
	IBook fetchById( long id );
	
	List<IBook> fetchAllBooks();
	
	boolean isEmpty();
}