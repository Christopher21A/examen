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

import ec.edu.insteclrg.domain.Service;
import ec.edu.insteclrg.dto.ApiResponseDTO;
import ec.edu.insteclrg.dto.ServicioDTO;
import ec.edu.insteclrg.service.crud.ServiceService;

@RestController
@RequestMapping("/api/v1.0/servicio/")//url del controlador de servicio
public class ServiceController {
	@Autowired
	ServiceService service;

	//Guardamos datos en la clase servicio
	@PostMapping (path = "guardar")
	public ResponseEntity<Object> guardar(@RequestBody ServicioDTO dto) {
		service.save(dto);
		return new ResponseEntity<>(new ApiResponseDTO<>(true, null), HttpStatus.CREATED);
	}

	//Actualizar datos en servicio
	@PutMapping(path= "actualizar")
	public ResponseEntity<Object> actualizar(@RequestBody ServicioDTO dto) {
		service.update(dto);
		return new ResponseEntity<>(new ApiResponseDTO<>(true, null),HttpStatus.CREATED);
		
	}

	@GetMapping(path = "listar")
	public ResponseEntity<Object> listar() {
		List<ServicioDTO> list = service.findAll(new ServicioDTO());
		if (!list.isEmpty()) {
			ApiResponseDTO<List<ServicioDTO>> response = new ApiResponseDTO<>(true, list);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "{id}/buscar")
	public ResponseEntity<Object> buscar(@PathVariable Long id) {
		ServicioDTO dto = new ServicioDTO();
		dto.setId(id);
		Optional<Service> domain = service.find(dto);
		if (!domain.isEmpty()) {
			dto = service.mapToDto(domain.get());
			return new ResponseEntity<>(new ApiResponseDTO<>(true, dto), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(path = "{id}/eliminar")
	public ResponseEntity<Object> eliminar(@PathVariable Long id) {
		ServicioDTO dto = new ServicioDTO();
		dto.setId(id);
		Optional<Service> domain = service.find(dto);
		if (!domain.isEmpty()) {
			service.delete(dto);
			return new ResponseEntity<>(new ApiResponseDTO<>(true, dto), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.NOT_FOUND);
		}
	}
}

