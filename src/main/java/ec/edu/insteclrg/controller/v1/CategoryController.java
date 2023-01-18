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

import ec.edu.insteclrg.domain.Category;
import ec.edu.insteclrg.dto.ApiResponseDTO;
import ec.edu.insteclrg.dto.CategoriaDTO;
import ec.edu.insteclrg.service.crud.CategoryService;

@RestController
@RequestMapping("/api/v1.0/categoria/")//url del controlador de categoria
public class CategoryController {

	@Autowired
	CategoryService service;

	@PostMapping(path = "guardar")
	public ResponseEntity<Object> guardar(@RequestBody CategoriaDTO dto) {
		service.save(dto);
		return new ResponseEntity<>(new ApiResponseDTO<>(true, null), HttpStatus.CREATED);
	}

	@PutMapping(path= "actualizar")
	public ResponseEntity<Object> actualizar(@RequestBody CategoriaDTO dto) {
		service.update(dto);
		return new ResponseEntity<>(new ApiResponseDTO<>(true, null),HttpStatus.CREATED);
		
	}

	@GetMapping(path = "listar")
	public ResponseEntity<Object> listar() {
		List<CategoriaDTO> list = service.findAll(new CategoriaDTO());
		if (!list.isEmpty()) {
			ApiResponseDTO<List<CategoriaDTO>> response = new ApiResponseDTO<>(true, list);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.NOT_FOUND);
		}
	}
	
	//se coloca las {} en el path xq vamos a recibir un dato y "id sera donde se colocara"
	@GetMapping(path = "{id}/buscar")
	public ResponseEntity<Object> buscar(@PathVariable Long id) {
		CategoriaDTO dto = new CategoriaDTO();
		dto.setId(id);
		Optional<Category> domain = service.find(dto);
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
		CategoriaDTO dto = new CategoriaDTO();
		dto.setId(id);
		Optional<Category> domain = service.find(dto);
		if (!domain.isEmpty()) {
			service.delete(dto);
			return new ResponseEntity<>(new ApiResponseDTO<>(true, dto), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.NOT_FOUND);
		}
	}


}

