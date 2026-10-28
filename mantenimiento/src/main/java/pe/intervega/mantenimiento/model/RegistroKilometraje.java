package pe.intervega.mantenimiento.model;

import java.time.LocalDateTime;

// Un registro de kilometraje tomado al ingreso de un vehículo al taller
public class RegistroKilometraje {

    private Integer id;
    private Integer idVehiculo;
    private Integer idTaller;
    private Integer idUsuario;
    // Solo para mostrar en la tabla (viene del JOIN con vehiculo)
    private String placa;
    private int kilometraje;
    private LocalDateTime fechaRegistro;
    private String observacion;

    public RegistroKilometraje() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(Integer idVehiculo) { this.idVehiculo = idVehiculo; }

    public Integer getIdTaller() { return idTaller; }
    public void setIdTaller(Integer idTaller) { this.idTaller = idTaller; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public int getKilometraje() { return kilometraje; }
    public void setKilometraje(int kilometraje) { this.kilometraje = kilometraje; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}