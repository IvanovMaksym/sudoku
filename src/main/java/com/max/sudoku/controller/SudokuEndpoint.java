package com.max.sudoku.controller;

import com.max.sudoku.SudokuService;
import com.max.sudoku.model.Board;
import com.max.sudoku.model.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/sudoku")
public class SudokuEndpoint {

     private final SudokuService sudokuService;

     @Autowired
     public SudokuEndpoint(SudokuService sudokuService) {
          this.sudokuService = sudokuService;
     }

     @RequestMapping(value = "/board", method = RequestMethod.POST)
     public ResponseEntity<Object> generateNewBoard() {
          String resourceId = sudokuService.createNewBoard();
          return ResponseEntity
                  .status(HttpStatus.CREATED)
                  .header(HttpHeaders.LOCATION, resourceId)
                  .build();
     }

     @RequestMapping(value = "/board/{id}", method = RequestMethod.GET)
     public ResponseEntity<Object> getNewBoard(@PathVariable("id") String resourceId) {
          return ResponseEntity.ok(sudokuService.retrieveBoard(resourceId));
     }

     @RequestMapping(value = "/board/{id}/validate")
     public ResponseEntity<Object> validate(@PathVariable("id") String resourceId, @Valid @RequestBody Cell cell) {
          Board board = sudokuService.retrieveBoard(resourceId);
          Board updatedBoard = sudokuService.validateCell(board, cell);
          return ResponseEntity.ok(updatedBoard);
     }
}
