package ec.edu.insteclrg.controller.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.insteclrg.domain.Product;
import ec.edu.insteclrg.dto.ApiResponseDTO;
import ec.edu.insteclrg.dto.ProductoDTO;
import ec.edu.insteclrg.service.crud.ProductService;

@RestController
@RequestMapping("/api/v1.0/producto/")
public class ProductController {
	@Autowired
	ProductService service;

	@PostMapping(path = "guardar")
	public ResponseEntity<Object> guardar(@RequestBody ProductoDTO dto) {
		service.save(dto);
		return new ResponseEntity<>(new ApiResponseDTO<>(true, null), HttpStatus.CREATED);
	}

	@PutMapping(value= "actualizar")
	public ResponseEntity<Object> actualizar(@RequestBody ProductoDTO dto) {
		service.update(dto);
		return new ResponseEntity<>(new ApiResponseDTO<>(true, null),HttpStatus.CREATED);
		
	}

	@GetMapping(path = "listar")
	public ResponseEntity<Object> listar() {
		List<ProductoDTO> list = service.findAll(new ProductoDTO());
		if (!list.isEmpty()) {
			ApiResponseDTO<List<ProductoDTO>> response = new ApiResponseDTO<>(true, list);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.NOT_FOUND);
		}
	}

	//se coloca las {} en el path xq vamos a recibir un dato y "id sera donde se colocara"
	@GetMapping(path = "{id}/buscar")
	public ResponseEntity<Object> buscar(@PathVariable Long id) {
		ProductoDTO dto = new ProductoDTO();
		dto.setId(id);
		Optional<Product> domain = service.find(dto);
		if (!domain.isEmpty()) {
			dto = service.mapToDto(domain.get());
			return new ResponseEntity<>(new ApiResponseDTO<>(true, dto), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.NOT_FOUND);
		}
	}
	
	//se coloca las {} en el path xq vamos a recibir un dato y "id sera donde se colocara"
	@DeleteMapping(path = "{id}/eliminar")
	public ResponseEntity<Object> eliminar(@PathVariable Long id) {
		ProductoDTO dto = new ProductoDTO();
		dto.setId(id);
		Optional<Product> domain = service.find(dto);
		if (!domain.isEmpty()) {
			service.delete(dto);
			return new ResponseEntity<>(new ApiResponseDTO<>(true, dto), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.NOT_FOUND);
		}
	}
}
