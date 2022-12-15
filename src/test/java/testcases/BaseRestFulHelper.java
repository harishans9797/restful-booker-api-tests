package testcases;

import base.BaseRestFulBooker;
import controllers.BookingController;
import domain.models.Auth;
import domain.models.User;
import utils.JacksonUtils;

import java.time.LocalDateTime;

public class BaseRestFulHelper extends BaseRestFulBooker {
    protected BookingController bookingController = new BookingController();
    protected JacksonUtils jacksonUtils = new JacksonUtils();

    protected String getAuthenticationToken() {
        init();
        jacksonUtils.init();
        String username = loadProp("username");
        String password = loadProp("password");
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        String userToJson = jacksonUtils.toJson(user);
        String token = bookingController.authenticate(userToJson.replace("userName", "username"));
        Auth auth = jacksonUtils.fromJson(token, Auth.class);
        return auth.token;
    }

    protected void retry(Long timeoutMs, Long intervalMs, Runnable block) {
        long timeoutSeconds = timeoutMs / 1000;
        LocalDateTime endTime = LocalDateTime.now().plusSeconds(timeoutSeconds);

        while (LocalDateTime.now().isBefore(endTime)) {
            try {
                block.run();
                return;
            } catch (Throwable t) {
                try {
                    Thread.sleep(intervalMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        block.run();

    }

}
