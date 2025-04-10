package kr.hhplus.be.server.interfaces.api.point;

import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointCommand;
import kr.hhplus.be.server.domain.point.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping("/api/users")
public class PointController {

    @Autowired
    PointService pointService;

    @PostMapping(value = "/{userId}/point")
    public ResponseEntity<PointResponse> charge(@PathVariable("userId") Long userId, @RequestBody PointCommand command) {
        Point point = pointService.chargePoint(command);
        PointResponse response = new PointResponse();
        response.setUserId(point.getUserId());
        response.setBalance(point.getBalance());
        return ResponseEntity.ok(response);
    }

}
