package com.example.heecoffee.Service.impl;

import com.example.heecoffee.Dto.Response.TypeResponse;
import com.example.heecoffee.Repository.TypeRepository;
import com.example.heecoffee.Service.TypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    public final TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public List<TypeResponse> getTypes() {
        return typeRepository.findAll()
                .stream()
                .map(t -> new TypeResponse(
                        t.getId(),
                        t.getTypes()
                ))
                .toList();
    }
}
