package com.example.heecoffee.Controller;

import com.example.heecoffee.Dto.Response.ApiResponse;
import com.example.heecoffee.Dto.Response.TypeResponse;
import com.example.heecoffee.Service.TypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/type")
@CrossOrigin(origins = "http://localhost:3000")
public class TypeController {
    public final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getType() {
        List<TypeResponse> type = typeService.getTypes();
        return ResponseEntity.ok(new ApiResponse("success", type));
    }
}
