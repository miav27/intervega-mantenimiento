package pe.intervega.mantenimiento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Representa un usuario del sistema (Jefe de Taller o Administrador General)
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(nullable = false, unique = true, length = 40)
    private String username;

    // Guarda solo el hash BCrypt, nunca la contraseña en texto plano
    @Column(name = "password_hash", nullable = false, length = 60)
    private String passwordHash;

    @Column(nullable = false, length = 80)
    private String nombres;

    // ADMIN_GENERAL o JEFE_TALLER
    @Column(nullable = false, length = 20)
    private String rol;

    // Nulo para el Administrador General (acceso global)
    @Column(name = "id_taller")
    private Integer idTaller;

    @Column(nullable = false)
    private boolean estado = true;

    @Column(name = "intentos_fallidos", nullable = false)
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