package pe.intervega.mantenimiento.model;

// Representa un usuario del sistema (Jefe de Taller o Administrador General)
public class Usuario {

    private Integer id;
    private String username;
    // Guarda solo el hash BCrypt, nunca la contraseña en texto plano
    private String passwordHash;
    private String nombres;
    // ADMIN_GENERAL o JEFE_TALLER
    private String rol;
    // Nulo para el Administrador General (acceso global)
    private Integer idTaller;
    private boolean estado = true;
    private int intentosFallidos = 0;

    public Usuario() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Integer getIdTaller() { return idTaller; }
    public void setIdTaller(Integer idTaller) { this.idTaller = idTaller; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public int getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(int intentosFallidos) { this.intentosFallidos = intentosFallidos; }
}