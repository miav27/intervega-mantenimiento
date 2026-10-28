package pe.intervega.mantenimiento.model;

import java.time.LocalDateTime;

// Alerta que avisa que un vehículo alcanzó el intervalo de un mantenimiento preventivo
public class AlertaMantenimiento {

    private Integer id;
    private Integer idVehiculo;
    // Solo para mostrar (vienen de los JOIN)
    private String placa;
    private Integer idTipo;
    private String tipoNombre;
    private int kmProgramado;
    private LocalDateTime fechaGeneracion;
    // PENDIENTE, ATENDIDA o DESCARTADA
    private String estado;

    public AlertaMantenimiento() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(Integer idVehiculo) { this.idVehiculo = idVehiculo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public Integer getIdTipo() { return idTipo; }
    public void setIdTipo(Integer idTipo) { this.idTipo = idTipo; }

    public String getTipoNombre() { return tipoNombre; }
    public void setTipoNombre(String tipoNombre) { this.tipoNombre = tipoNombre; }

    public int getKmProgramado() { return kmProgramado; }
    public void setKmProgramado(int kmProgramado) { this.kmProgramado = kmProgramado; }

    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(LocalDateTime fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}