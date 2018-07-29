package com.max.sudoku.model;

import java.util.Set;

public class Board {

     private String id;
     private String dealLinks;
     private BoardState boardState;
     private Set<Cell> cells;

     public Board() {

     }

     public String getId() {
          return id;
     }

     public void setId(String id) {
          this.id = id;
     }

     public String getDealLinks() {
          return dealLinks;
     }

     public void setDealLinks(String dealLinks) {
          this.dealLinks = dealLinks;
     }

     public BoardState getBoardState() {
          return boardState;
     }

     public void setBoardState(BoardState boardState) {
          this.boardState = boardState;
     }

     public Set<Cell> getCells() {
          return cells;
     }

     public void setCells(Set<Cell> cells) {
          this.cells = cells;
     }
}
