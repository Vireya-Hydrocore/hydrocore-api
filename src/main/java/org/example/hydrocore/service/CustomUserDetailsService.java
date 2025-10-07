package org.example.hydrocore.service;

import org.example.hydrocore.repository.RepositoryFuncionario;
import org.example.hydrocore.repository.entity.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RepositoryFuncionario funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado"));

        String role;
        switch (funcionario.getIdCargo().getIdCargo()) {
            case 1 -> role = "ADMIN";
            case 2 -> role = "ESTOQUE";
            case 3, 4 -> role = "CALC";
            default -> role = "USER";
        }

        return User.builder()
                .username(funcionario.getEmail())
                .password(funcionario.getSenha())
                .roles(role)
                .build();
    }
}

