package kr.hhplus.be.server.presentation.point;

import kr.hhplus.be.server.application.point.PointCriteria;
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
        PointCriteria.UserPoint chargeRequest = PointCriteria.UserPoint.fromRequest(request);
        PointResult.Transaction result = pointFacade.charge(chargeRequest);
        return PointResponse.Transaction.from(result);
    }

}
