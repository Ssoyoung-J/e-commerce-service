package kr.hhplus.be.server.presentation.point;

import kr.hhplus.be.server.application.point.PointFacade;
import kr.hhplus.be.server.application.point.PointResult;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping("/api/points")
public class PointController {

    private PointFacade pointFacade;

    @PostMapping(value = "/charge")
    public PointResponse.Transaction charge(@RequestBody PointRequest.Charge request) {
        PointResult.Transaction result = pointFacade.charge(request.userId(), request.pointAmount());
        return PointResponse.Transaction.from(result);
    }

}
