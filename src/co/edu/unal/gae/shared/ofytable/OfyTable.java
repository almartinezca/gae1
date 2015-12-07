package co.edu.unal.gae.shared.ofytable;



import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

//implements co.edu.unal.gae.shared.guice.ITable
@Entity
public class OfyTable {

@Id private long codigo;
	private String titulo;
	private String autor;
	private String editorial;
	private int copias;
	private String estado;



	public OfyTable(int codigo2, String titulo2, String autor2, String editorial2, int copias2, String estado2){
		this.codigo=codigo2;
		this.titulo=titulo2;
		this.autor=autor2;
		this.editorial=editorial2;
		this.copias=copias2;
		this.estado=estado2;
		
	}

	@Override
	public String toString() {
		return "OfyTable [codigo=" + codigo + ", titulo=" + titulo + ", autor="
				+ autor + ", editorial=" + editorial + ", copias=" + copias
				+ ", estado=" + estado + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OfyTable other = (OfyTable) obj;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String nombre) {
		this.titulo = nombre;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String apellido) {
		this.autor = apellido;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String facultad) {
		this.editorial = facultad;
	}

	public int getCopias() {
		return copias;
	}



	public String getEstado() {
		return estado;
	}

	public void setEstado(String categoria) {
		this.estado = categoria;
	}
/*
	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}
*/
/*
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}*/
}
