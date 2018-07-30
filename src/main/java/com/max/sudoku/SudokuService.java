package com.max.sudoku;

import com.max.sudoku.exception.SudokuValidationException;
import com.max.sudoku.model.Board;
import com.max.sudoku.model.BoardState;
import com.max.sudoku.model.Cell;
import com.max.sudoku.model.Error;
import com.max.sudoku.model.ErrorType;
import com.max.sudoku.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
public class SudokuService {

     private static Map<String, Board> boards = new ConcurrentHashMap<>();
     private static final String DEALS_LINK = "www.dealsLink.com";
     private static final String RESOURCE_EXPIRED_MESSAGE = "Resource expired";
     private final BoardFactory boardFactory;
     private final List<BoardValidator> validators;

     @Autowired
     public SudokuService(BoardFactory boardFactory, List<BoardValidator> validators) {
          this.boardFactory = boardFactory;
          this.validators = validators;
     }

     public String createNewBoard() {
          Board board = boardFactory.generateNewValidBoard();
          boards.put(board.getId(), board);

          return board.getId();
     }

     public Board retrieveBoard(String resourceId) {
          Board board = boards.get(resourceId);
          if (board == null) {
               throw new SudokuValidationException(
                       singletonList(new Error.Builder()
                               .withErrorType(ErrorType.RESOURCE_EXPIRED)
                               .withDetails(RESOURCE_EXPIRED_MESSAGE)
                               .build()
                       ));
          }
          return board;
     }

     public Board validateCell(Board board, Cell cell) {
          List<CompletableFuture<List<Error>>> futureResults = validators.stream()
                  .map(validator -> supplyAsync(() -> validator.validate(board, cell)))
                  .collect(Collectors.toList());
          List<Error> errors = futureResults.stream()
                  .map(CompletableFuture::join)
                  .flatMap(Collection::stream)
                  .collect(Collectors.toList());

          if (!errors.isEmpty()) {
               throw new SudokuValidationException(errors);
          }
          updateBoard(board, cell);

          return board;
     }

     private void updateBoard(Board board, Cell incomingCell) {
          board.getCells().stream()
                  .filter(cell -> Objects.equals(cell.getX(), incomingCell.getX()))
                  .filter(cell -> Objects.equals(cell.getY(), incomingCell.getY()))
                  .findFirst()
                  .ifPresent(x -> x.setValue(incomingCell.getValue()));

          List<Integer> nonNull = board.getCells().stream()
                  .map(Cell::getValue)
                  .filter(Objects::nonNull)
                  .collect(Collectors.toList());
          if (nonNull.size() == board.getCells().size()) {
               setCompleted(board);
          }
     }

     private void setCompleted(Board board) {
          board.setBoardState(BoardState.COMPLETED);
          board.setDealLinks(DEALS_LINK);
     }
}
