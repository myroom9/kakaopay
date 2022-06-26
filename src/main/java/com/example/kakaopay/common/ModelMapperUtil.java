package com.example.kakaopay.common;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 * modelMapper lib class
 */
public class ModelMapperUtil {
    private static ModelMapper modelMapper;

    public static <T> T map(Object source, Class<T> target) {
        return modelMapper().map(source, target);
    }

    private static ModelMapper modelMapper() {
        if (modelMapper == null) {
            synchronized (ModelMapper.class) {
                modelMapper = new ModelMapper();
                modelMapper
                        .getConfiguration()
                        .setMatchingStrategy(MatchingStrategies.STRICT)
                        .setSkipNullEnabled(true);
            }
        }

        return modelMapper;
    }
}
