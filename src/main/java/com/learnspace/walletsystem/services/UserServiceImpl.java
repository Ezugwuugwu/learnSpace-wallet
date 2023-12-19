package com.learnspace.walletsystem.services;

import com.learnspace.walletsystem.dtos.responses.CreateWalletResponse;
import com.learnspace.walletsystem.dtos.requests.RegisterRequest;
import com.learnspace.walletsystem.dtos.responses.RegisterResponse;
import com.learnspace.walletsystem.dtos.responses.WalletResponseDTO;
import com.learnspace.walletsystem.exception.NotFoundException;
import com.learnspace.walletsystem.models.Role;
import com.learnspace.walletsystem.models.User;
import com.learnspace.walletsystem.repositories.UserRepository;
import com.learnspace.walletsystem.security.entities.SecureUser;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final ModelMapper mapper;
    private final WalletService walletService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new NotFoundException("User with email already exists");
        }
        User user = mapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.getRoles().add(Role.USER);
        var savedUser = userRepository.save(user);
        var walletResponse = walletService.createWallet(savedUser.getEmail());
        savedUser.setWalletId(walletResponse.getId());
        userRepository.save(user);
        return buildRegisterResponse(savedUser, walletResponse);
    }

    @Override
    public WalletResponseDTO getWalletFor(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new NotFoundException("user not found"));
        return walletService.getWallet(user.getWalletId());
    }

    private RegisterResponse buildRegisterResponse(User savedUser, CreateWalletResponse walletResponse) {
        RegisterResponse response = new RegisterResponse();
        response.setEmail(savedUser.getEmail());
        response.setWalletId(walletResponse.getId());
        response.setBalance(walletResponse.getBalance().toString());
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(()->new NotFoundException(
                String.format("user with email %s not found", username)
        ));
        return new SecureUser(user);
    }
}
