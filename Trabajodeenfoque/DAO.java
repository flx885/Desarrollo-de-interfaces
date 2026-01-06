package Trabajodeenfoque;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class DAO {

    // Método que obtiene todos los alquileres (para mostrar en interfaz)
public List<Object[]> listarTabla() {
    List<Object[]> datos = new ArrayList<>();  // Lista para almacenar resultados
    String sql = "SELECT * FROM alquileres";   // Consulta SQL para obtener todos los registros

    // Try-with-resources: cierra automáticamente conexión, statement y result set
    try (
            Connection con = ConexionBD.conectar();           // Establece conexión a BD
            PreparedStatement stmt = con.prepareStatement(sql);  // Prepara consulta SQL
            ResultSet rs = stmt.executeQuery()) {             // Ejecuta consulta y obtiene resultados
            
        // Recorre cada fila del resultado
        while (rs.next()) {
            // Crea array con datos de cada alquiler y lo añade a la lista
            datos.add(new Object[]{
                rs.getInt("id"),              // Obtiene ID del alquiler
                rs.getDate("fecha_inicio"),   // Obtiene fecha de inicio
                rs.getInt("duracion"),        // Obtiene duración en días
                rs.getInt("cliente_id"),      // Obtiene ID del cliente (referencia)
                rs.getInt("vivienda_id")      // Obtiene ID de la vivienda (referencia)
            });
        }
    } catch (Exception e) {
        e.printStackTrace();  // Imprime error en consola si falla la consulta
    }
    return datos;  // Devuelve lista con todos los alquileres obtenidos
}

// Método que filtra alquileres por rango de fechas (para interfaz)
public List<Object[]> buscarFechasTabla(Date inicio, Date fin) {
    List<Object[]> datos = new ArrayList<>();  // Lista para resultados filtrados
    // Consulta SQL con parámetros para filtro por fechas
    String sql = "SELECT * FROM alquileres WHERE fecha_inicio BETWEEN ? AND ?";
    
    try (
            Connection con = ConexionBD.conectar();           // Conexión a BD
            PreparedStatement stmt = con.prepareStatement(sql)) {  // Prepara consulta con parámetros
            
        // Asigna valores a los parámetros de la consulta (evita SQL injection)
        stmt.setDate(1, new java.sql.Date(inicio.getTime()));  // Parámetro 1: fecha inicio
        stmt.setDate(2, new java.sql.Date(fin.getTime()));     // Parámetro 2: fecha fin

        ResultSet rs = stmt.executeQuery();  // Ejecuta consulta con los parámetros
        
        // Procesa cada fila del resultado filtrado
        while (rs.next()) {
            datos.add(new Object[]{           // Añade cada alquiler filtrado a la lista
                rs.getInt("id"),              // ID del alquiler
                rs.getDate("fecha_inicio"),   // Fecha de inicio
                rs.getInt("duracion"),        // Duración
                rs.getInt("cliente_id"),      // ID del cliente
                rs.getInt("vivienda_id")      // ID de la vivienda
            });
        }
    } catch (Exception e) {
        e.printStackTrace();  // Manejo de errores
    }
    return datos;  // Devuelve lista con alquileres filtrados por fecha
}

    // Devuelve nombres completos sin filtrar (solo para el informe)
public List<Object[]> listarTablaCompleta() {  // Método que retorna lista de arreglos de objetos
    List<Object[]> datos = new ArrayList<>();  // Crea lista vacía para almacenar resultados
    
    // Query SQL que obtiene datos de alquileres con joins a tablas relacionadas
    String sql = "SELECT a.id, a.fecha_inicio, a.duracion, c.nombre, v.ubicacion "
            + "FROM alquileres a "
            + "JOIN clientes c ON a.cliente_id = c.id "  // Une tabla clientes por ID
            + "JOIN viviendas v ON a.vivienda_id = v.id"; // Une tabla viviendas por ID
    
    // Try-with-resources para manejo automático de cierre de conexiones
    try (
            Connection con = ConexionBD.conectar();  // Obtiene conexión a BD
            PreparedStatement stmt = con.prepareStatement(sql);  // Prepara consulta SQL
            ResultSet rs = stmt.executeQuery()) {  // Ejecuta consulta y obtiene resultados
        
        // Itera sobre cada fila del resultado
        while (rs.next()) {
            datos.add(new Object[]{  // Agrega arreglo de objetos a la lista
                // Aquí hacemos que aparezca los nombres y ubicación y quitamos los IDs
                rs.getInt("id"),  // Obtiene ID del alquiler como entero
                rs.getDate("fecha_inicio"),  // Obtiene fecha de inicio como Date
                rs.getInt("duracion"),  // Obtiene duración como entero
                rs.getString("nombre"),  // Obtiene nombre del cliente como String
                rs.getString("ubicacion")  // Obtiene ubicación de vivienda como String
            });
        }
    } catch (Exception e) {  // Maneja excepciones de BD
        e.printStackTrace();  // Imprime error en consola
    }
    return datos;  // Retorna lista con resultados
}

// Filtra por fechas y devuelve nombres completos (solo para el informe)
public List<Object[]> buscarFechasTablaCompleta(Date inicio, Date fin) {  // Método con parámetros de fecha
    List<Object[]> datos = new ArrayList<>();  // Crea lista vacía para resultados
    
    // SQL similar al anterior pero con filtro BETWEEN para fechas
    String sql = "SELECT a.id, a.fecha_inicio, a.duracion, c.nombre, v.ubicacion "
            + "FROM alquileres a "
            + "JOIN clientes c ON a.cliente_id = c.id "
            + "JOIN viviendas v ON a.vivienda_id = v.id "
            + "WHERE a.fecha_inicio BETWEEN ? AND ?";  // Filtro con parámetros

    try (
            Connection con = ConexionBD.conectar();  // Obtiene conexión
            PreparedStatement stmt = con.prepareStatement(sql)) {  // Prepara consulta con parámetros
        
        // Asigna valores a los parámetros de la consulta
        stmt.setDate(1, new java.sql.Date(inicio.getTime()));  // Primer parámetro: fecha inicio
        stmt.setDate(2, new java.sql.Date(fin.getTime()));  // Segundo parámetro: fecha fin

        ResultSet rs = stmt.executeQuery();  // Ejecuta consulta con parámetros ya asignados

        // Itera sobre resultados filtrados
        while (rs.next()) {
            datos.add(new Object[]{  // Agrega cada fila a la lista
                rs.getInt("id"),
                rs.getDate("fecha_inicio"),
                rs.getInt("duracion"),
                rs.getString("nombre"),
                rs.getString("ubicacion")
            });
        }
    } catch (Exception e) {  // Maneja excepciones
        e.printStackTrace();  // Imprime error
    }
    return datos;  
}
} 