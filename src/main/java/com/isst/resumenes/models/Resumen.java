package com.isst.resumenes.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resumenes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resumen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @Column(nullable = false, unique = true)
    private String archivoUrl; // URL pública del archivo PDF

    @Column(nullable = false)
    private boolean validado = false; // Por defecto, no validado

    @Column(nullable = false)
    private boolean ejemplo = false; // Indica si es un resumen de ejemplo
}
