package com.cynthia.apiRest.apiRest.Controllers;

import com.cynthia.apiRest.apiRest.Entities.Producto;
import com.cynthia.apiRest.apiRest.Repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.util.UUID;


import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;


    @GetMapping
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }


    @GetMapping("/id")
    public Producto obtenerProductoPorId(@RequestParam Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID: " + id));
    }

    @PostMapping
    public Producto createProducto(
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") double precio,
            @RequestParam("descripcion") String descripcion,
            @RequestParam(value = "imagenPrincipalArchivo", required = false) MultipartFile imagenPrincipalArchivo,
            @RequestParam(value = "imagenPrincipalUrl", required = false) String imagenPrincipalUrl,
            @RequestParam(value = "imagenExtra1", required = false) MultipartFile imagenExtra1,
            @RequestParam(value = "imagenExtra2", required = false) MultipartFile imagenExtra2
    ) throws IOException {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);

        // imagen principal (obligatoria: archivo o url)
        if (imagenPrincipalArchivo != null && !imagenPrincipalArchivo.isEmpty()) {
            String fileName = saveFile(imagenPrincipalArchivo);
            producto.setImagenPrincipal("/uploads/" + fileName);
        } else if (imagenPrincipalUrl != null && !imagenPrincipalUrl.isBlank()) {
            producto.setImagenPrincipal(imagenPrincipalUrl);
        } else {
            throw new RuntimeException("La imagen principal es obligatoria (archivo o URL).");
        }

        // extras
        if (imagenExtra1 != null && !imagenExtra1.isEmpty()) {
            producto.setImagenExtra1("/uploads/" + saveFile(imagenExtra1));
        }
        if (imagenExtra2 != null && !imagenExtra2.isEmpty()) {
            producto.setImagenExtra2("/uploads/" + saveFile(imagenExtra2));
        }

        return productoRepository.save(producto);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get("src/main/resources/static/uploads/");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }


    @PutMapping
    public Producto ActualizarProductoPorId(
            @RequestParam Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") double precio,
            @RequestParam("descripcion") String descripcion,
            @RequestParam(value = "imagenPrincipalArchivo", required = false) MultipartFile imagenPrincipalArchivo,
            @RequestParam(value = "imagenPrincipalUrl", required = false) String imagenPrincipalUrl,
            @RequestParam(value = "imagenExtra1", required = false) MultipartFile imagenExtra1,
            @RequestParam(value = "imagenExtra2", required = false) MultipartFile imagenExtra2
    ) throws IOException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el producto con el ID: " + id));

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);

        if (imagenPrincipalArchivo != null && !imagenPrincipalArchivo.isEmpty()) {
            producto.setImagenPrincipal("/uploads/" + saveFile(imagenPrincipalArchivo));
        } else if (imagenPrincipalUrl != null && !imagenPrincipalUrl.isBlank()) {
            producto.setImagenPrincipal(imagenPrincipalUrl);
        }

        if (imagenExtra1 != null && !imagenExtra1.isEmpty()) {
            producto.setImagenExtra1("/uploads/" + saveFile(imagenExtra1));
        }
        if (imagenExtra2 != null && !imagenExtra2.isEmpty()) {
            producto.setImagenExtra2("/uploads/" + saveFile(imagenExtra2));
        }

        return productoRepository.save(producto);
    }


    @DeleteMapping
    public String eliminarProductoPorId(@RequestParam Long id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID: " + id ));
     productoRepository.delete(producto);
     return "El producto con el ID: " + id + " se elimino correctamente";
    }




}
