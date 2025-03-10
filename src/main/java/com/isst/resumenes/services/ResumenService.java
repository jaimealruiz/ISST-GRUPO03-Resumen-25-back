package com.isst.resumenes.services;

import com.isst.resumenes.models.Resumen;
import com.isst.resumenes.models.Usuario;
import com.isst.resumenes.repositories.ResumenRepository;
import com.isst.resumenes.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ResumenService {

    private final ResumenRepository resumenRepository;
    private final UsuarioRepository usuarioRepository;
    private final String storagePath = "uploads/resumenes"; // Carpeta donde se guardan los archivos

    public ResumenService(ResumenRepository resumenRepository, UsuarioRepository usuarioRepository) {
        this.resumenRepository = resumenRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Guarda un resumen en la base de datos
    public Resumen guardarResumen(String titulo, String email, MultipartFile archivo, boolean ejemplo) throws IOException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con email " + email));

        String archivoNombre = guardarArchivo(archivo, usuario.getId());

        Resumen resumen = new Resumen();
        resumen.setTitulo(titulo);
        resumen.setAutor(usuario);
        resumen.setArchivoUrl("/archivos/" + archivoNombre);
        resumen.setEjemplo(ejemplo);
        resumen.setValidado(false); // Siempre empieza como no validado

        return resumenRepository.save(resumen);
    }

    // Lista todos los resúmenes, solo validados si se pide
    public List<Resumen> listarResúmenes(boolean soloValidados) {
        if (soloValidados) {
            return resumenRepository.findByValidado(true);
        }
        return resumenRepository.findAll();
    }

    // Busca los resúmenes de un usuario por email
    public List<Resumen> listarResúmenesPorAutor(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con email " + email));

        return resumenRepository.findByAutor(usuario);
    }

    // Valida un resumen por su ID
    public Resumen validarResumen(Long id) {
        Resumen resumen = resumenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el resumen con ID " + id));

        resumen.setValidado(true);
        return resumenRepository.save(resumen);
    }

    // Lista solo los resúmenes de ejemplo
    public List<Resumen> listarResumenesEjemplo() {
        return resumenRepository.findByEjemplo(true);
    }

    // Guarda el archivo subido en la carpeta de almacenamiento
    private String guardarArchivo(MultipartFile archivo, Long usuarioId) throws IOException {
        Path directorio = Paths.get(storagePath);
        if (!Files.exists(directorio)) {
            Files.createDirectories(directorio); // Crea la carpeta si no existe
        }

        String archivoNombre = "resumen_" + usuarioId + "_" + System.currentTimeMillis() + ".pdf";
        Path archivoPath = directorio.resolve(archivoNombre);
        Files.write(archivoPath, archivo.getBytes());

        return archivoNombre;
    }
}
