package com.max.sudoku.validator;

import com.max.sudoku.model.Board;
import com.max.sudoku.model.Cell;
import com.max.sudoku.model.Error;

import java.util.List;

public interface BoardValidator {

     List<Error> validate(Board board, Cell cell);
}
