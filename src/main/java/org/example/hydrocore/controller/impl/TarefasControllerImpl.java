package org.example.hydrocore.controller.impl;

import org.example.hydrocore.controller.TarefasController;
import org.example.hydrocore.dto.ResumoTarefasEtaResponseDTO;
import org.example.hydrocore.dto.request.TarefasRequestDTO;
import org.example.hydrocore.dto.response.TarefasResponseDTO;
import org.example.hydrocore.service.TarefasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TarefasControllerImpl implements TarefasController {

    @Autowired
    private TarefasService tarefasService;

    @Override
    public ResponseEntity<List<TarefasResponseDTO>> mostrarTarefas(Boolean tarefasConcluidas) {
        return ResponseEntity.ok(tarefasService.listarTarefas(tarefasConcluidas));
    }

    @Override
    public ResponseEntity<TarefasResponseDTO> deletarTarefa(Integer idTarefa) {
        return ResponseEntity.ok(tarefasService.deletarTarefa(idTarefa));
    }

    @Override
    public ResponseEntity<List<TarefasResponseDTO>> buscarTarefaPorNome(String nome, Boolean tarefasConcluidas) {
        return ResponseEntity.ok(tarefasService.buscarTarefaPorNome(nome, tarefasConcluidas));
    }

    @Override
    public ResponseEntity<TarefasResponseDTO> criarTarefa(Integer idFuncionario, TarefasRequestDTO tarefasRequestDTO) {
        return ResponseEntity.ok(tarefasService.criarTarefa(idFuncionario, tarefasRequestDTO));
    }

    @Override
    public ResponseEntity<TarefasResponseDTO> atualizarStatusTarefa(Integer idTarefa, String status) {
        return ResponseEntity.ok(tarefasService.atualizarStatusTarefa(idTarefa, status));
    }

    @Override
    public ResponseEntity<TarefasResponseDTO> atribuirTarefa(Integer idTarefa, Integer idFuncionario) {
        return ResponseEntity.ok(tarefasService.atribuirTarefa(idTarefa, idFuncionario));
    }

    @Override
    public ResponseEntity<TarefasResponseDTO> atualizarTarefaParcial(Integer idTarefa, TarefasRequestDTO tarefasRequestDTO) {
        return ResponseEntity.ok(tarefasService.atualizarParcialmenteTarefa(idTarefa, tarefasRequestDTO));
    }

    @Override
    public ResponseEntity<TarefasResponseDTO> atualizarTarefaCompleta(Integer idTarefa, TarefasRequestDTO tarefasRequestDTO){
        return ResponseEntity.ok(tarefasService.atualizarTarefaCompleta(idTarefa, tarefasRequestDTO));
    }

    @Override
    public ResponseEntity<ResumoTarefasEtaResponseDTO> resumoTarefasPorEta(Integer idEta) {
        return ResponseEntity.ok(tarefasService.mostrarResumoTarefasEta(idEta));
    }

}
