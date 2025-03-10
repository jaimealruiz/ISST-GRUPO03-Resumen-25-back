package com.isst.resumenes.repositories;

import com.isst.resumenes.models.Resumen;
import com.isst.resumenes.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResumenRepository extends JpaRepository<Resumen, Long> {
    List<Resumen> findByAutor(Usuario autor);
    List<Resumen> findByValidado(boolean validado);
    List<Resumen> findByEjemplo(boolean ejemplo);

}
