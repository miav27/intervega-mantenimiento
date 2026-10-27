package pe.intervega.mantenimiento.model;

// Taller de InterVega (clase simple, sin anotaciones JPA)
public class Taller {

    private Integer id;
    private String nombre;
    private String direccion;
    private boolean estado = true;

    public Taller() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
