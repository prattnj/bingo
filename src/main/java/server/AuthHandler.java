package server;

import com.google.gson.Gson;
import da.UserDAO;
import model.bean.UserBean;
import model.request.AuthRequest;
import model.response.AuthResponse;
import model.response.BaseResponse;
import spark.Request;
import spark.Response;

import java.util.UUID;

public class AuthHandler {

    public static Object handleRegister(Request req, Response res) {
        Gson gson = new Gson();

        try {
            AuthRequest request = gson.fromJson(req.body(), AuthRequest.class);

            // validate request
            if (request.username() == null || request.password() == null || request.email() == null) {
                res.status(400);
                return null;
            }

            // validate username, email
            if (UserDAO.getUser(request.username()) != null) {
                res.status(409);
                return gson.toJson(new BaseResponse("Username taken"));
            }
            if (UserDAO.getUserByEmail(request.email()) != null) {
                res.status(409);
                return gson.toJson(new BaseResponse("Email taken"));
            }

            String token = UUID.randomUUID().toString();
            UserDAO.insertUser(new UserBean(request.username(), request.password(), request.email(), token));
            res.status(200);
            return gson.toJson(new AuthResponse(request.username(), token));
        } catch (Exception e) {
            res.status(500);
            return null;
        }
    }

    public static Object handleLogin(Request req, Response res) {
        Gson gson = new Gson();

        try {
            AuthRequest request = gson.fromJson(req.body(), AuthRequest.class);

            // validate request
            if (request.username() == null || request.password() == null) {
                res.status(400);
                return null;
            }

            // validate username, password
            UserBean user = UserDAO.getUser(request.username());
            if (user == null) {
                res.status(401);
                return gson.toJson(new BaseResponse("Invalid username"));
            }
            if (!UserDAO.validatePassword(request.username(), request.password())) {
                res.status(401);
                return gson.toJson(new BaseResponse("Invalid password"));
            }

            String token = UUID.randomUUID().toString();
            UserDAO.updateToken(user.getUsername(), token);
            res.status(200);
            return gson.toJson(new AuthResponse(user.getUsername(), token));
        } catch (Exception e) {
            res.status(500);
            return gson.toJson(new BaseResponse("Server error"));
        }
    }
}
