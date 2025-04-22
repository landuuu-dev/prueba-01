package com.cynthia.apiRest.apiRest.Controllers;

import com.cynthia.apiRest.apiRest.Entities.Producto;
import com.cynthia.apiRest.apiRest.Repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;


    @GetMapping
    public List<Producto> getAllProductos(@RequestParam(required = false) Long id) {
        if (id != null) {
            return productoRepository.findById(id)
                    .map(List::of) // Convierte el producto encontrado a una lista
                    .orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID: " + id));
        }
        return productoRepository.findAll();
    }

    @GetMapping("/id")
    public Producto obtenerProductoPorId(@RequestParam Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID: " + id));
    }

    @PostMapping
    public Producto createProducto(@RequestBody Producto producto){
        return productoRepository.save(producto);
    }

    @PutMapping
    public Producto ActualizarProductoPorId(@RequestParam Long id, @RequestBody Producto detallesProducto){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID: " + id));
        producto.setNombre(detallesProducto.getNombre());
        producto.setPrecio(detallesProducto.getPrecio());
        producto.setDescripcion(detallesProducto.getDescripcion());

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
