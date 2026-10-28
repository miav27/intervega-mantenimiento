package pe.intervega.mantenimiento.model;

// Tipo de mantenimiento preventivo con su intervalo en kilómetros
public class TipoMantenimiento {

    private Integer id;
    private String nombre;
    private int intervaloKm;

    public TipoMantenimiento() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getIntervaloKm() { return intervaloKm; }
    public void setIntervaloKm(int intervaloKm) { this.intervaloKm = intervaloKm; }
}