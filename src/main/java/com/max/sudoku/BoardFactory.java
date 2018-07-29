package com.max.sudoku;

import com.max.sudoku.model.Board;
import com.max.sudoku.model.BoardState;
import com.max.sudoku.model.Cell;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BoardFactory {

     Board generateNewValidBoard() {
          Board board = new Board();
          board.setId(UUID.randomUUID().toString());
          board.setBoardState(BoardState.VALID);
          board.setCells(generateCells());
          setValidCellsValues(board);

          return board;
     }

     private Set<Cell> generateCells() {
          return Stream.iterate(1, i -> i + 1)
                  .limit(9)
                  .map(this::generatePartial)
                  .flatMap(Collection::stream)
                  .collect(Collectors.toCollection(TreeSet::new));
     }

     private Set<Cell> generatePartial(int x) {
          return Stream.iterate(1, i -> i + 1)
                  .limit(9)
                  .map(i -> new Cell(x, i, null))
                  .collect(Collectors.toSet());
     }

     private void setValidCellsValues(Board board) {
          setValueForCoordinates(board, 1, 1, 4);
          setValueForCoordinates(board, 1, 3, 8);
          setValueForCoordinates(board, 1, 4, 7);
          setValueForCoordinates(board, 1, 6, 6);
          setValueForCoordinates(board, 1, 7, 9);
          setValueForCoordinates(board, 2, 2, 9);
          setValueForCoordinates(board, 3, 6, 2);
          setValueForCoordinates(board, 3, 8, 1);
          setValueForCoordinates(board, 4, 2, 2);
          setValueForCoordinates(board, 4, 4, 8);
          setValueForCoordinates(board, 4, 6, 5);
          setValueForCoordinates(board, 4, 7, 7);
          setValueForCoordinates(board, 4, 9, 3);
          setValueForCoordinates(board, 5, 5, 7);
          setValueForCoordinates(board, 5, 5, 3);
          setValueForCoordinates(board, 5, 9, 9);
          setValueForCoordinates(board, 6, 1, 3);
          setValueForCoordinates(board, 6, 2, 6);
          setValueForCoordinates(board, 7, 7, 5);
          setValueForCoordinates(board, 8, 1, 6);
          setValueForCoordinates(board, 8, 6, 7);
          setValueForCoordinates(board, 8, 8, 4);
          setValueForCoordinates(board, 8, 9, 1);
          setValueForCoordinates(board, 9, 1, 1);
          setValueForCoordinates(board, 9, 2, 7);
          setValueForCoordinates(board, 9, 4, 3);
          setValueForCoordinates(board, 9, 5, 8);
     }

     private void setValueForCoordinates(Board board, int x, int y, int value) {
          Cell targetCell = board.getCells().stream()
                  .filter(cell -> cell.getX().equals(x))
                  .filter(cell -> cell.getY().equals(y))
                  .findFirst()
                  .orElseThrow(RuntimeException::new);
          targetCell.setValue(value);
     }

//     x x x 3 9 x x 1 x
//     5 x 1 x x x x 4 x
//     9 x x 7 x x 5 x x
//     6 x 2 5 3 x x 7 x
//     X x x x 7 x x x 8
//     7 x x 8 x x 9 x 3
//     8 x 3 x 1 x x 9 x
//     x 9 x 2 x 6 x x 7
//     4 x x x x 3 x 6 1
}
