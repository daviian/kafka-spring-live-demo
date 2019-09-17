package cc.catalysts.kafka.livedemo.webservice.api;

import cc.catalysts.kafka.livedemo.model.Freezer;
import cc.catalysts.kafka.livedemo.webservice.service.FreezerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class FreezerController {

    @Autowired
    private FreezerService freezerService;

    @GetMapping("/freezers")
    List<Freezer> all() {
        return freezerService.getAll();
    }

    @PostMapping("/freezers")
    Freezer create(@RequestBody Freezer freezer) throws IOException {
        return freezerService.create(freezer);
    }

    @DeleteMapping("/freezers/{id}")
    UUID delete(@PathVariable UUID id) throws IOException {
        freezerService.delete(id);
        return id;
    }

}
