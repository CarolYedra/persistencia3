package es.avalon.curso.negocio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import es.avalon.utilidades.persistencia.DBHelper;

public class Libro {

	private String autor;
	private String titulo;
	private int pagina;

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public Libro(String titulo, String autor, int pagina) {
		super();
		this.autor = autor;
		this.titulo = titulo;
		this.pagina = pagina;
	}

	public Libro(String titulo) {
		super();
		this.titulo = titulo;
	}

	public Libro() {
		super();
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
		Libro other = (Libro) obj;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

//BUSCAR TODOS
	public static List<Libro> buscarTodos() {

		List<Libro> libros = new ArrayList<Libro>();
		ResultSet rs = null;
		try (Connection conexion = DBHelper.crearConexion(); Statement sentencia = DBHelper.crearStatement(conexion);) {
			String sql = "select * from libro";
			rs = sentencia.executeQuery(sql);
			while (rs.next()) {
				String autor = rs.getString("autor");
				String titulo = rs.getString("titulo");
				int pagina = rs.getInt("pagina");
				Libro l = new Libro(titulo, autor, pagina);
				libros.add(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return libros;

	}

//INSERTAR
	public void insertar() {
		String sql = "insert into Libro (titulo, autor, pagina) values (?,?,?)";
		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {
			sentencia.setString(1, titulo);
			sentencia.setString(2, autor);
			sentencia.setInt(3, pagina);
			sentencia.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//BORRAR
	public static void borrar(String titulo) throws ClassNotFoundException {
		String sql = "DELETE FROM Libro where titulo = ?";
		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.borrarPreparedStatement(conexion, sql);) {
			sentencia.setString(1, titulo);
			sentencia.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//ACTUALIZAR
	public void salvar() throws ClassNotFoundException {
		System.out.println(this.getAutor() + " " + this.getTitulo() + " " + this.getPagina());
		String sql = "update Libro set autor ='" + this.getAutor() + "', pagina=" + this.getPagina()
				+ " where titulo ='" + this.getTitulo() + "'";
		try (Connection conexion = DBHelper.crearConexion(); Statement sentencia = DBHelper.crearStatement(conexion);) {
			sentencia.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//BUSCAR UNO
	public static Libro buscarUno(String titulo) throws ClassNotFoundException {
		Libro l = null;
		String sql = "select * FROM Libro  WHERE titulo ='" + titulo + "'";
		System.out.println(sql);
		try (Connection conexion = DBHelper.crearConexion();
				Statement sentencia = DBHelper.crearStatement(conexion);
				ResultSet rs = sentencia.executeQuery(sql)) {
			rs.next();
			l = new Libro(rs.getString("titulo"), rs.getString("autor"), rs.getInt("pagina"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

//BUSCAR POR MODELO

	public static List<Libro> buscarTodosTitulo(String tituloLibro) {
		List<Libro> libros = new ArrayList<Libro>();
		ResultSet rs = null;
		try (Connection conexion = DBHelper.crearConexion(); Statement sentencia = DBHelper.crearStatement(conexion);) {
			String sql = "select * from Libro where titulo='" + tituloLibro + "'";
			rs = sentencia.executeQuery(sql);
			while (rs.next()) {
				String autor = rs.getString("autor");
				String titulo = rs.getString("titulo");
				int pagina = rs.getInt("pagina");
				Libro l = new Libro(titulo, autor, pagina);
				libros.add(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return libros;
	}
//FILTRAR

	public static List<Libro> filtrar(String filtro) {
		List<Libro> libros = new ArrayList<Libro>();
		ResultSet rs = null;
		try (Connection conexion = DBHelper.crearConexion(); Statement sentencia = DBHelper.crearStatement(conexion);) {
			String sql = "select * from Libro order by " + filtro;
			rs = sentencia.executeQuery(sql);
			while (rs.next()) {
				String autor = rs.getString("autor");
				String titulo = rs.getString("titulo");
				int pagina = rs.getInt("pagina");
				Libro l = new Libro(titulo, autor, pagina);
				libros.add(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return libros;
	}
	

	
}
