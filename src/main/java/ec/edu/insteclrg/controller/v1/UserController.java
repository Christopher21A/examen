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

import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.dto.ApiResponseDTO;
import ec.edu.insteclrg.dto.UserDTO;
import ec.edu.insteclrg.service.crud.UserService;

@RestController
@RequestMapping("/api/v1.0/usuario")
public class UserController {

	@Autowired
	UserService service;
	
	
	@PostMapping("/registrar")
	public ResponseEntity<Object> registrar(@RequestBody UserDTO dto) {
		service.registrar(dto);
		return new ResponseEntity<>(new ApiResponseDTO<>(true, null), HttpStatus.CREATED);
	}

	//Metodo para logear
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody UserDTO dto) {
		//Recibimos datos y guradamos en la clase User
		Optional<User> domain = service.login(dto);
		domain.get().setPassword("");//no se presenta la contraseña
		dto = service.mapToDto(domain.get());
		return new ResponseEntity<>(new ApiResponseDTO<>(true, dto), HttpStatus.OK);
	}

	@PutMapping(path = "/User")
	public ResponseEntity<Object> actualizar(@RequestBody UserDTO dto) {
		service.update(dto);
		return new ResponseEntity<>(new ApiResponseDTO<>(true, null), HttpStatus.CREATED);

	}

	@GetMapping(path = "/Listar")
	public ResponseEntity<Object> listar() {
		List<UserDTO> list = service.findAll(new UserDTO());
		if (!list.isEmpty()) {
			ApiResponseDTO<List<UserDTO>> response = new ApiResponseDTO<>(true, list);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> buscar(@PathVariable Long id) {
		UserDTO dto = new UserDTO();
		dto.setId(id);
		Optional<User> domain = service.find(dto);
		if (!domain.isEmpty()) {
			dto = service.mapToDto(domain.get());
			return new ResponseEntity<>(new ApiResponseDTO<>(true, dto), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable Long id) {
		UserDTO dto = new UserDTO();
		dto.setId(id);
		this.service.delete(dto);
		return new ResponseEntity<>(new ApiResponseDTO<>(true, null), HttpStatus.OK);
	}

}
