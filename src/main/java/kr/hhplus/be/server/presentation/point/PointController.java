package kr.hhplus.be.server.presentation.point;

import kr.hhplus.be.server.application.point.PointCriteria;
import kr.hhplus.be.server.application.point.PointFacade;
import kr.hhplus.be.server.application.point.PointResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/points")
public class PointController {

    private final PointFacade pointFacade;

    @GetMapping(value = "/{userId}")
    public PointResponse.UserPoint getUserBalance(@PathVariable("userId") Long userId) {
        PointCriteria.Balance criteria = PointCriteria.Balance.of(userId);
        PointResult.UserPoint balance = pointFacade.getUserBalance(criteria);
        return PointResponse.UserPoint.from(balance);
    }

    @PostMapping(value = "/charge")
    public PointResponse.Transaction charge(@RequestBody PointRequest.Charge request) {
        PointCriteria.UserPoint chargeRequest = PointCriteria.UserPoint.fromRequest(request);
        PointResult.Transaction result = pointFacade.charge(chargeRequest);
        return PointResponse.Transaction.from(result);
    }

    @GetMapping(value = "/history/{userId}")
    public List<PointResponse.History> getUserPointHistories(@PathVariable("userId") Long userId) {
        PointCriteria.Balance criteria = PointCriteria.Balance.of(userId);
        List<PointResponse.History> histories = pointFacade.getUserPointHistories(criteria).stream()
                .map(PointResponse.History::from)
                .toList();

        return histories;
    }

}
