package com.max.sudoku.exception;

import com.max.sudoku.model.Error;

import java.util.List;

public class SudokuValidationException extends RuntimeException {
     private List<Error> errors;

     public SudokuValidationException(List<Error> errors) {
          this.errors = errors;
     }

     List<Error> getErrors() {
          return errors;
     }
}
