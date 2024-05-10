package server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import da.BoardDAO;
import da.UserDAO;
import model.bean.BoardBean;
import model.exception.BadRequestException;
import model.exception.ConflictException;
import model.request.BaseRequest;
import model.response.BaseResponse;
import service.AuthService;
import service.Service;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.lang.reflect.Type;

public class Handler {

    private static final Gson gson = new Gson();

    public static Object handle(Request req, Response res, Service service, Type requestType) throws Exception {
        BaseRequest request = null;
        if (requestType != null) request = gson.fromJson(req.body(), requestType);

        BaseResponse response = service.execute(request, req.headers("Authorization"));

        res.status(200);
        if (response == null) return "{}";
        return gson.toJson(response);
    }

    public static String newResponse(String message) {
        return gson.toJson(new BaseResponse(message));
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
