package es.avalon.curso.negocio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import es.avalon.utilidades.persistencia.DBHelper;

public class Capitulo {

	private String titulo;
	private int paginas;
	private String Libro_Titulo;

	public Capitulo(String titulo) {
		super();
		this.titulo = titulo;
	}

	public Capitulo(String titulo, int paginas, String Libro_Titulo) {
		super();
		this.titulo = titulo;
		this.paginas = paginas;
		this.Libro_Titulo = Libro_Titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	public String getLibro_Titulo() {
		return Libro_Titulo;
	}

	public void setLibro_Titulo(String Libro_Titulo) {
		this.Libro_Titulo = Libro_Titulo;
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
		Capitulo other = (Capitulo) obj;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	// BUSCAR TODOS
	public static List<Capitulo> buscarTodos() {

		List<Capitulo> capitulos = new ArrayList<Capitulo>();
		ResultSet rs = null;
		try (Connection conexion = DBHelper.crearConexion(); Statement sentencia = DBHelper.crearStatement(conexion);) {
			String sql = "select * from capitulo";
			rs = sentencia.executeQuery(sql);
			while (rs.next()) {
				String tituloCap = rs.getString("titulo");
				int paginas = rs.getInt("paginas");
				String Libro_Titulo = rs.getString("Libro_Titulo");
				Capitulo c = new Capitulo(tituloCap, paginas, Libro_Titulo);
				capitulos.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return capitulos;

	}
//MOSTAR CAPITULOS	

	public static List<Capitulo> buscarTodosCapitulos(String libroTitulo) {
		System.out.println(libroTitulo);
		List<Capitulo> capitulos = new ArrayList<Capitulo>();
		ResultSet rs = null;
		try (Connection conexion = DBHelper.crearConexion(); Statement sentencia = DBHelper.crearStatement(conexion);) {
			String sql = "select * from capitulo where libro_Titulo='" + libroTitulo + "'";
			rs = sentencia.executeQuery(sql);
			while (rs.next()) {
				String tituloCap = rs.getString("titulo");
				int paginas = rs.getInt("paginas");
				String Libro_Titulo = rs.getString("Libro_Titulo");
				Capitulo c = new Capitulo(tituloCap, paginas, Libro_Titulo);
				capitulos.add(c);
			}
			System.out.println(capitulos.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return capitulos;

	}

// INSERTAR 
	public void insertar() {
		String sql = "insert into Capitulo (titulo, paginas, Libro_Titulo ) values (?,?,?)";
		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {
			sentencia.setString(1, titulo);
			sentencia.setInt(2, paginas);
			sentencia.setString(3, Libro_Titulo);
			sentencia.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

// BORRAR 
	public static void borrar(String titulo) throws ClassNotFoundException {
		String sql = "DELETE FROM Capitulo where titulo = ?";
		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.borrarPreparedStatement(conexion, sql);) {
			sentencia.setString(1, titulo);
			sentencia.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}}
		
	
//ACTUALIZAR 
		public void salvar() throws ClassNotFoundException {
			System.out.println(this.getTitulo() + " " + this.getPaginas() + " " + this.getLibro_Titulo());
			String sql = "update Capitulo set Libro_Titulo ='" + this.getLibro_Titulo() + "', paginas=" + this.getPaginas()
					+ " where titulo ='" + this.getTitulo() + "'";
			}
		

//BUSCAR UNO 
		public static Capitulo buscarUno(String titulo) throws ClassNotFoundException {
			Capitulo c = null;
			String sql = "select * FROM Capitulo  WHERE titulo ='" + titulo + "'";
			System.out.println(sql);
			try (Connection conexion = DBHelper.crearConexion();
					Statement sentencia = DBHelper.crearStatement(conexion);
					ResultSet rs = sentencia.executeQuery(sql)) {
				rs.next();
				c = new Capitulo(rs.getString("titulo"), rs.getInt("paginas"), rs.getString("Libro_Titulo"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return c;
		}

	//BUSCAR POR MODELO 

		public static List<Capitulo> buscarTodosTitulo(String tituloCapitulo) {
			List<Capitulo> capitulos = new ArrayList<Capitulo>();
			ResultSet rs = null;
			try (Connection conexion = DBHelper.crearConexion(); Statement sentencia = DBHelper.crearStatement(conexion);) {
				String sql = "select * from Capitulo where titulo='" + tituloCapitulo + "'";
				rs = sentencia.executeQuery(sql);
				while (rs.next()) {
					
					String titulo = rs.getString("titulo");
					int paginas = rs.getInt("paginas");
					String Libro_Titulo = rs.getString("Libro_Titulo");
					Capitulo c = new Capitulo(titulo, paginas, Libro_Titulo);
					capitulos.add(c);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return capitulos;
		}
	//FILTRAR 

		public static List<Capitulo> filtrar(String filtro) {
			List<Capitulo> capitulos = new ArrayList<Capitulo>();
			ResultSet rs = null;
			try (Connection conexion = DBHelper.crearConexion(); Statement sentencia = DBHelper.crearStatement(conexion);) {
				String sql = "select * from Capitulo order by " + filtro;
				rs = sentencia.executeQuery(sql);
				while (rs.next()) {
					
					String titulo = rs.getString("titulo");
					int paginas = rs.getInt("paginas");
					String Libro_Titulo = rs.getString("Libro_Titulo");
					Capitulo c = new Capitulo(titulo, paginas, Libro_Titulo);
					capitulos.add(c);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return capitulos;
		}

	
	}
	

