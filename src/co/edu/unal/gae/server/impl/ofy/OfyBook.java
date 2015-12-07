package co.edu.unal.gae.server.impl.ofy;

import co.edu.unal.gae.server.dao.ofy.IBook;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;



@Entity
@Index
public class OfyBook implements IBook
{
	@Override
	public Long getId() {
		
		return id;
	}

	@Override
	public void setId( Long id ) {
		
		this.id = id;
	}

	@Override
	public String getTitulo() {
		
		return titulo;
	}

	@Override
	public void setTitulo( String titulo ) {
		
		this.titulo = titulo;
	}

	@Override
	public String getAutor() {
		
		return autor;
	}

	@Override
	public void setAutor( String autor ) {

		this.autor = autor;
	}

	@Override
	public String getEditorial() {
		
		return editorial;
	}

	@Override
	public String setEditorial( String editorial ) {

		return this.editorial = editorial;
	}
	@Override

	public Long getCopias() {

		return copias;
	}

	@Override
	public Long setCopias(Long copias) {
		return copias;
	}

	private long id;
	private String titulo; 
	private String autor;
	private String editorial;
	private Long copias;
	
}
