package com.fitlife.app.controller.trainer;

import com.fitlife.app.security.user.CurrentUser;
import com.fitlife.app.security.user.FitLifeUserDetail;
import com.fitlife.app.service.user.IUserService;
import com.trainer.service.OpenAiService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/open-ai")
public class OpenAiController {
    private final OpenAiService openAiService;
    private IUserService userService;
    @Getter
    public static class AddTokenRequest{
        private String token;
    }
    @PostMapping("/token")
    public ResponseEntity<?> setToken(@CurrentUser FitLifeUserDetail userDetail, @RequestBody AddTokenRequest request) {
        try {
            userService.setOpenAiToken(userDetail.getId(),request.getToken());
            openAiService.updateToken(request.getToken());
            return ResponseEntity.ok().body("Update User Token Success");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Update User Token Failed");
        }
    }
}
