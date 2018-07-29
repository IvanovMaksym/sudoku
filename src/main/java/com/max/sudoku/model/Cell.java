package com.max.sudoku.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Cell implements Comparable<Cell> {

     @Min(1)
     @Max(9)
     private Integer x;
     @Min(1)
     @Max(9)
     private Integer y;
     @Min(1)
     @Max(9)
     private Integer value;

     public Cell() {
     }

     public Cell(Integer x, Integer y, Integer value) {
          this.x = x;
          this.y = y;
          this.value = value;
     }

     public Integer getValue() {
          return value;
     }

     public void setValue(Integer value) {
          this.value = value;
     }

     public Integer getX() {
          return x;
     }

     public void setX(Integer x) {
          this.x = x;
     }

     public Integer getY() {
          return y;
     }

     public void setY(Integer y) {
          this.y = y;
     }

     @Override
     public int compareTo(Cell o) {
          if (this.x < o.x) {
               return -1;
          }
          if (this.x > o.x) {
               return 1;
          }
          if (this.x.equals(o.x)) {
               if (this.y < o.y) {
                    return -1;
               } else {
                    return 1;
               }
          }
          return 0;
     }
}
