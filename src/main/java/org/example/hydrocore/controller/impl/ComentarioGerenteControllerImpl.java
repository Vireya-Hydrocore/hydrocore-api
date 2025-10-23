package org.example.hydrocore.controller.impl;

import org.example.hydrocore.controller.ComentarioGerenteController;
import org.example.hydrocore.dto.request.ComentarioGerenteRequestDTO;
import org.example.hydrocore.dto.response.ComentarioGerenteResponseDTO;
import org.example.hydrocore.service.ComentarioGerenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ComentarioGerenteControllerImpl implements ComentarioGerenteController {

    @Autowired
    private ComentarioGerenteService  comentarioGerenteService;

    @Override
    public ResponseEntity<List<ComentarioGerenteResponseDTO>> listarComentarios() {
        return ResponseEntity.ok(comentarioGerenteService.listarComentariosGerente());
    }

    @Override
    public ResponseEntity<ComentarioGerenteResponseDTO> buscarComentarioPorId(Integer id) {
        return ResponseEntity.ok(comentarioGerenteService.buscarComentarioGerentePorId(id));
    }

    @Override
    public ResponseEntity<ComentarioGerenteResponseDTO> criarComentario(ComentarioGerenteRequestDTO comentario) {
        return ResponseEntity.ok(comentarioGerenteService.salvarComentarioGerente(comentario));
    }

    @Override
    public ResponseEntity<ComentarioGerenteResponseDTO> atualizarComentario(Integer id, ComentarioGerenteRequestDTO requestDTO) {
        return ResponseEntity.ok(comentarioGerenteService.atualizarComentarioGerente(id, requestDTO));
    }

    @Override
    public ResponseEntity<ComentarioGerenteResponseDTO> deletarComentario(Integer id) {
        return ResponseEntity.ok(comentarioGerenteService.removerComentarioGerente(id));
    }
}
