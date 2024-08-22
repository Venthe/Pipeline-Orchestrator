/*
package eu.venthe.pipeline.orchestrator.repositorys.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repositorys")
@RequiredArgsConstructor
public class RepositoryController {
    private final RepositoryQueryService queryService;

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(queryService.listRepository());
    }
}
*/
