package com.max.sudoku.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.max.sudoku.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

     private ObjectMapper objectMapper = new ObjectMapper();

     @ExceptionHandler(SudokuValidationException.class)
     public ModelAndView handleSudokuValidationException(SudokuValidationException e) {
          List<Error> errors = e.getErrors();
          ResponseEntity<List<Error>> entity = new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setView(new AbstractView() {
               @Override
               protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request,
                                                      HttpServletResponse response) throws Exception {
                    response.setContentType(MediaType.APPLICATION_JSON.toString());
                    response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                    response.setStatus(entity.getStatusCode().value());
                    response.getWriter().print(objectMapper.writeValueAsString(entity.getBody()));
               }
          });

          return modelAndView;
     }
}
