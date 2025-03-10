package com.isst.resumenes.repositories;


import com.isst.resumenes.models.HistorialResumen;
import com.isst.resumenes.models.Usuario;
import com.isst.resumenes.models.Resumen;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface HistorialResumenRepository extends JpaRepository<HistorialResumen, Long> {
    List<HistorialResumen> findByUsuario(Usuario usuario);
    Optional<HistorialResumen> findByUsuarioAndResumen(Usuario usuario, Resumen resumen);
}
