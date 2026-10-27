package pe.intervega.mantenimiento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Vehículo de la flota de InterVega
@Entity
@Table(name = "vehiculo")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_taller", nullable = false)
    private Taller taller;

    @Column(nullable = false, unique = true, length = 10)
    private String placa;

    @Column(length = 40)
    private String marca;

    @Column(length = 40)
    private String modelo;

    private Integer anio;

    @Column(name = "kilometraje_actual", nullable = false)
    private int kilometrajeActual = 0;

    // ACTIVO, EN_TALLER o BAJA
    @Column(nullable = false, length = 15)
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
