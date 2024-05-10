package service;

import da.UserDAO;
import model.bean.UserBean;
import model.exception.BadRequestException;
import model.exception.ConflictException;
import model.exception.UnauthorizedException;
import model.request.AuthRequest;
import model.request.BaseRequest;
import model.response.AuthResponse;
import model.response.BaseResponse;

import java.util.UUID;

public class AuthService {
    public static class Register extends Service {
        @Override
        public BaseResponse doService(BaseRequest req, String token) throws Exception {
            AuthRequest request = (AuthRequest) req;

            // validate request
            if (request.username() == null || request.password() == null || request.email() == null)
                throw new BadRequestException("Null request value");

            // validate username, email
            if (UserDAO.get(request.username()) != null) throw new ConflictException("Username taken");
            if (UserDAO.getByEmail(request.email()) != null) throw new ConflictException("Email taken");

            String newToken = UUID.randomUUID().toString();
            UserDAO.insert(new UserBean(request.username(), request.password(), request.email(), newToken));
            return new AuthResponse(request.username(), newToken);
        }
    }

    public static class Login extends Service {
        @Override
        public BaseResponse doService(BaseRequest req, String token) throws Exception {
            AuthRequest request = (AuthRequest) req;

            // validate request
            if (request.username() == null || request.password() == null)
                throw new BadRequestException("Null request value");

            // validate username, password
            UserBean user = UserDAO.get(request.username());
            if (user == null) throw new UnauthorizedException("Invalid username");
            if (!UserDAO.validatePassword(request.username(), request.password()))
                throw new UnauthorizedException("Invalid password");

            String newToken = UUID.randomUUID().toString();
            UserDAO.updateToken(user.username(), newToken);
            return new AuthResponse(user.username(), newToken);
        }
    }
}
