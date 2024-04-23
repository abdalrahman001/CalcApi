import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CalculationController {

    @Autowired
    private CalculationRepository calculationRepository;

    // POST API to perform a calculation
    @PostMapping("/calc")
    public ResponseEntity<?> createCalculation(@RequestBody Calculation calculation) {
        try {
            int result;
            switch (calculation.getOperation()) {
                case "+":
                    result = calculation.getNumber1() + calculation.getNumber2();
                    break;
                case "-":
                    result = calculation.getNumber1() - calculation.getNumber2();
                    break;
                case "*":
                    result = calculation.getNumber1() * calculation.getNumber2();
                    break;
                case "/":
                    result = calculation.getNumber1() / calculation.getNumber2();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation");
            }
            calculation.setResult(result);
            calculationRepository.save(calculation);
            return ResponseEntity.ok().body(calculation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // GET API to retrieve all calculations
    @GetMapping("/calculations")
    public ResponseEntity<?> getAllCalculations() {
        try {
            List<Calculation> calculations = calculationRepository.findAll();
            return ResponseEntity.ok().body(calculations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
