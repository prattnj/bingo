package service;

import model.exception.ServerErrorException;
import model.request.BaseRequest;
import model.response.BaseResponse;

import java.sql.SQLException;

public abstract class Service {

    public BaseResponse execute(BaseRequest request, String token) throws Exception {
        try {
            return doService(request, token);
        } catch (SQLException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    protected abstract BaseResponse doService(BaseRequest req, String token) throws Exception;
}
