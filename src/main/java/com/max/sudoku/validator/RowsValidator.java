package com.max.sudoku.validator;

import com.max.sudoku.model.Board;
import com.max.sudoku.model.Cell;
import com.max.sudoku.model.Error;
import com.max.sudoku.model.ErrorType;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RowsValidator implements BoardValidator {

     @Override
     public List<Error> validate(Board board, Cell inputCell) {
          List<Error> errors = new LinkedList<>();
          List<Cell> duplicatedCellsInRow = board.getCells().stream()
                  .filter(x -> Objects.equals(x.getY(), inputCell.getY()))
                  .filter(cell -> Objects.equals(cell.getValue(), inputCell.getValue()))
                  .collect(Collectors.toList());

          duplicatedCellsInRow.forEach(cell -> errors.add(new Error.Builder()
                  .withErrorType(ErrorType.DUPLICATED_ROW)
                  .withCell(cell)
                  .build())
          );

          return errors;
     }
}
