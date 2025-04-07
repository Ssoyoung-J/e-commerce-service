package kr.hhplus.be.server.interfaces.api.point;

import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointCommand;
import kr.hhplus.be.server.domain.point.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class PointController {

    @Autowired
    PointService pointService;

    @PostMapping(value = "/{userId}/point")
    public ResponseEntity<PointResponse> charge(@PathVariable("userId") Long userId, @RequestBody PointRequest.Charge req) {
        PointCommand command = new PointCommand(userId, req.pointAmount);
        return ResponseEntity.ok(pointService.chargePoint(command));
    }

}
