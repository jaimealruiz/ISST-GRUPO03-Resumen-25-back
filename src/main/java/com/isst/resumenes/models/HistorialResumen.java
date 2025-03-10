package com.isst.resumenes.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_resumenes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistorialResumen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Usuario que lee el resumen

    @ManyToOne
    @JoinColumn(name = "resumen_id", nullable = false)
    private Resumen resumen; // Resumen que está leyendo

    @Column(nullable = false)
    private LocalDateTime fechaLectura; // Última fecha de acceso

    @Column(nullable = false)
    private int progreso; // Página en la que se quedó

    @Column(nullable = false)
    private int estrellas; // Opinión (0 a 5)
}
