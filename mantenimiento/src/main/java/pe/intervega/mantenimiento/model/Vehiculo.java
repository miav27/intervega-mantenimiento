package pe.intervega.mantenimiento.model;

// Vehículo de la flota de InterVega (clase simple, sin anotaciones JPA)
public class Vehiculo {

    private Integer id;
    // Taller al que pertenece el vehículo
    private Taller taller;
    private String placa;
    private String marca;
    private String modelo;
    private Integer anio;
    private int kilometrajeActual = 0;
    // ACTIVO, EN_TALLER o BAJA
    private String estado = "ACTIVO";

    public Vehiculo() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Taller getTaller() { return taller; }
    public void setTaller(Taller taller) { this.taller = taller; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public int getKilometrajeActual() { return kilometrajeActual; }
    public void setKilometrajeActual(int kilometrajeActual) { this.kilometrajeActual = kilometrajeActual; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
