package server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import da.UserDAO;
import model.bean.UserBean;
import model.request.AuthRequest;
import model.response.AuthResponse;
import model.response.BaseResponse;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.UUID;

public class AuthHandler {

    private static final Gson gson = new Gson();
    private static final Object DEFAULT_RETURN = "{}";

    public static Object register(Request req, Response res) {
        try {
            AuthRequest request = gson.fromJson(req.body(), AuthRequest.class);

            // validate request
            if (request.username() == null || request.password() == null || request.email() == null) {
                res.status(400);
                return DEFAULT_RETURN;
            }

            // validate username, email
            if (UserDAO.get(request.username()) != null) {
                res.status(409);
                return gson.toJson(new BaseResponse("Username taken"));
            }
            if (UserDAO.getByEmail(request.email()) != null) {
                res.status(409);
                return gson.toJson(new BaseResponse("Email taken"));
            }

            String token = UUID.randomUUID().toString();
            UserDAO.insert(new UserBean(request.username(), request.password(), request.email(), token));

            res.status(200);
            return gson.toJson(new AuthResponse(request.username(), token));
        } catch (JsonSyntaxException e) {
            res.status(400);
            return DEFAULT_RETURN;
        } catch (Exception e) {
            res.status(500);
            return DEFAULT_RETURN;
        }
    }

    public static Object login(Request req, Response res) {
        try {
            AuthRequest request = gson.fromJson(req.body(), AuthRequest.class);

            // validate request
            if (request.username() == null || request.password() == null) {
                res.status(400);
                return DEFAULT_RETURN;
            }

            // validate username, password
            UserBean user = UserDAO.get(request.username());
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
        } catch (JsonSyntaxException e) {
            res.status(400);
            return DEFAULT_RETURN;
        } catch (Exception e) {
            res.status(500);
            return DEFAULT_RETURN;
        }
    }

    public static void verifyAuthentication(Request req) {
        try {
            String token = req.headers("Authentication");
            if (token == null || UserDAO.getByToken(token) == null)
                Spark.halt(401, gson.toJson(new BaseResponse("Invalid token")));
        } catch (Exception e) {
            Spark.halt(500);
        }
    }
}
