package com.spring.recipe.services;

import com.spring.recipe.commands.UnitOfMeasureCommand;
import com.spring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository uomRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand typeToCommandConverter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand typeToCommandConverter) {
        this.uomRepository = uomRepository;
        this.typeToCommandConverter = typeToCommandConverter;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(uomRepository.findAll()
                .spliterator(), false)
                .map(typeToCommandConverter::convert)
                .collect(Collectors.toSet());
    }
}
