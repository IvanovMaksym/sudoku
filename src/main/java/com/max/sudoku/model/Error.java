package com.max.sudoku.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Objects;

@JsonDeserialize(builder = Error.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

     private final ErrorType errorType;
     private final Cell cell;
     private final String details;

     private Error(ErrorType errorType, Cell cell, String details) {
          this.errorType = errorType;
          this.cell = cell;
          this.details = details;
     }

     public ErrorType getErrorType() {
          return errorType;
     }

     public Cell getCell() {
          return cell;
     }

     public String getDetails() {
          return details;
     }

     @Override
     public boolean equals(Object o) {
          if (this == o) {
               return true;
          }
          if (o == null || getClass() != o.getClass()) {
               return false;
          }
          Error error = (Error) o;
          return errorType == error.errorType &&
                  Objects.equals(cell, error.cell) &&
                  Objects.equals(details, error.details);
     }

     @Override
     public int hashCode() {

          return Objects.hash(errorType, cell, details);
     }

     public static class Builder {

          private ErrorType errorType;
          private Cell cell;
          private String details;

          public Builder withErrorType(ErrorType errorType) {
               this.errorType = errorType;
               return this;
          }

          public Builder withCell(Cell cell) {
               this.cell = cell;
               return this;
          }

          public Builder withDetails(String details) {
               this.details = details;
               return this;
          }

          public Error build() {
               return new Error(errorType, cell, details);
          }
     }
}
