package org.example.hydrocore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {

    @GetMapping("/get")
    public String get() {
        return "Hello World";
    }

}
