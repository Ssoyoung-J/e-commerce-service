package kr.hhplus.be.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jfr.Threshold;
import kr.hhplus.be.server.controller.api.UserInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Controller", description = "User Controller")
@RestController
@RequestMapping("/api/users")
public class UserController implements UserInterface {

}
