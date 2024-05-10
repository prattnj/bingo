package model.response;

import model.bean.BoardBean;

public class ListBoardResponse extends BaseResponse {

    private final BoardBean[] boards;

    public ListBoardResponse(BoardBean[] boards) {
        this.boards = boards;
    }

    public BoardBean[] getBoards() {
        return boards;
    }
}
