package service;

import da.BoardDAO;
import da.UserDAO;
import model.bean.BoardBean;
import model.exception.BadRequestException;
import model.exception.ConflictException;
import model.exception.ForbiddenException;
import model.exception.NotFoundException;
import model.request.BaseRequest;
import model.request.WriteBoardRequest;
import model.response.BaseResponse;

import java.util.Objects;

public class BoardService {
    public static class Create extends Service {
        @Override
        protected BaseResponse doService(BaseRequest req, String token) throws Exception {
            WriteBoardRequest request = (WriteBoardRequest) req;

            // validate request
            if (request.name() == null || request.username() == null)
                throw new BadRequestException("Null request value");

            // validate id
            if (BoardDAO.get(request.id()) != null) throw new ConflictException("Board already exists");

            BoardDAO.insert(new BoardBean(request.id(), request.name(), request.username(), request.items(),
                    request.createdAt(), request.isPublic()));
            return new BaseResponse();
        }
    }

    public static class Update extends Service {
        @Override
        protected BaseResponse doService(BaseRequest req, String token) throws Exception {
            WriteBoardRequest request = (WriteBoardRequest) req;

            // validate request
            if (request.name() == null || request.username() == null)
                throw new BadRequestException("Null request value");

            // validate id
            if (BoardDAO.get(request.id()) == null) throw new NotFoundException("Board doesn't exist");

            // make sure it's your board
            if (!Objects.equals(request.username(), UserDAO.getByToken(token).username()))
                throw new ForbiddenException("This board isn't yours");

            BoardDAO.update(new BoardBean(request.id(), request.name(), request.username(), request.items(),
                    request.createdAt(), request.isPublic()));
            return new BaseResponse();
        }
    }

    public static class ListAll extends Service {
        @Override
        protected BaseResponse doService(BaseRequest req, String token) throws Exception {
            return null;
        }
    }

    public static class ListMine extends Service {
        @Override
        protected BaseResponse doService(BaseRequest req, String token) throws Exception {
            return null;
        }
    }

    public static class Delete extends Service {
        @Override
        protected BaseResponse doService(BaseRequest req, String token) throws Exception {
            return null;
        }
    }
}