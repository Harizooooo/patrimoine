package patrimoine.hei.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patrimoine.hei.Model.Patrimoine;
import patrimoine.hei.Service.PatrimoineService;

import java.io.IOException;

@RestController
@RequestMapping("/patrimoines")
public class PatrimoineController {
    private final PatrimoineService patrimoineService;

    public PatrimoineController(PatrimoineService patrimoineService) {
        this.patrimoineService = patrimoineService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patrimoine> getPatrimoine(@PathVariable String id) {
        try {
            Patrimoine patrimoine = patrimoineService.getPatrimoine(id);
            if (patrimoine != null) {
                return new ResponseEntity<>(patrimoine, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> createOrUpdatePatrimoine(@PathVariable String id, @RequestBody Patrimoine patrimoine) {
        if (patrimoine == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            patrimoineService.saveOrUpdatePatrimoine(id, patrimoine);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
