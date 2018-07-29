package com.max.sudoku.validator;

import com.max.sudoku.model.Board;
import com.max.sudoku.model.Cell;
import com.max.sudoku.model.Error;
import com.max.sudoku.model.ErrorType;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
public class SquareValidator implements BoardValidator {

     @Override
     public List<Error> validate(Board board, Cell incomingCell) {
          List<Error> errors = new LinkedList<>();
          Map<Integer, List<Cell>> squares = board.getCells().stream()
                  .collect(groupingBy(squareFunction));

          Integer squareNumberOfIncomingCell = squareFunction.apply(incomingCell);

          List<Cell> duplicated = squares.get(squareNumberOfIncomingCell).stream()
                  .filter(cell -> Objects.equals(cell.getValue(), incomingCell.getValue()))
                  .collect(Collectors.toList());

          duplicated.forEach(cell -> errors.add(new Error.Builder()
                  .withErrorType(ErrorType.DUPLICATE_IN_SQUARE)
                  .withCell(cell)
                  .build()));

          return errors;
     }

     // TODO refactor this function
     private Function<Cell, Integer> squareFunction = cell -> {
          Integer x = cell.getX();
          Integer y = cell.getY();

          if (x > 0 && x < 4) {
               if (y > 0 && y < 4) {
                    return 1;
               } else if (y > 3 && y < 7) {
                    return 2;
               } else if (y > 6 && y < 10) {
                    return 3;
               }
          } else if (x > 3 && x < 7) {
               if (y > 0 && y < 4) {
                    return 4;
               } else if (y > 3 && y < 7) {
                    return 5;
               } else if (y > 6 && y < 10) {
                    return 6;
               }
          } else if (x > 6 && x < 10) {
               if (y > 0 && y < 4) {
                    return 7;
               } else if (y > 3 && y < 7) {
                    return 8;
               } else if (y > 6 && y < 10) {
                    return 9;
               }
          }
          return null;
     };
}
