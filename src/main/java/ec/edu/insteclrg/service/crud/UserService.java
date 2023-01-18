package ec.edu.insteclrg.service.crud;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.common.ApiException;
import ec.edu.insteclrg.domain.Product;
import ec.edu.insteclrg.dto.UserDTO;
import ec.edu.insteclrg.dto.ProductoDTO;
import ec.edu.insteclrg.persistence.UserRepository;
import ec.edu.insteclrg.service.GenericCrudServiceImpl;

@Service
public class UserService extends GenericCrudServiceImpl<User, UserDTO> {

	@Autowired
	private UserRepository repository;

	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Optional<User> find(UserDTO dto) {
		return repository.findById(dto.getId());
	}

	@Override
	public UserDTO mapToDto(User domain) {
		return modelMapper.map(domain, UserDTO.class);
	}

	@Override
	public User mapToDomain(UserDTO dto) {
		return modelMapper.map(dto, User.class);
	}

	public Optional<User> login(UserDTO dto) {
		Optional<User> optional = this.repository.findByEmail(dto.getEmail());
		if (!optional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra el usuario registrado");
		}

		if (passwordEncoder.matches(dto.getPassword(), optional.get().getPassword())) {
			return optional;
		} else {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"No se encuentra el usuario registrado");
		}
	}

	public void registrar(UserDTO dto) {
		Optional<User> optional = find(dto);
		if (!optional.isPresent()) {
			User domainObject = mapToDomain(dto);
			domainObject.setPassword(passwordEncoder.encode(domainObject.getPassword()));//se registra y encripta la contraseña
			repository.save(domainObject);
		} else {
			throw new ApiException(String.format("Registro %s ya existe en el sistema", dto));
		}
	}

}
